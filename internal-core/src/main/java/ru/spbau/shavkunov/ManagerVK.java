package ru.spbau.shavkunov;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spbau.shavkunov.exceptions.*;
import ru.spbau.shavkunov.primitives.Statistics;
import ru.spbau.shavkunov.users.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.spbau.shavkunov.Constants.VK_PREFIX;
import static ru.spbau.shavkunov.Constants.VK_PREFIX_NO_PROTOCOL;

/**
 * Manager collects appropriate data and give statistics about user wall.
 */
public class ManagerVK {
    private static final @NotNull Logger logger = LoggerFactory.getLogger(ManagerVK.class);

    /**
     * userID of the user or community of vk.com
     */
    private @NotNull String userID;
    private @NotNull int amount;

    /**
     * @param link -- possible link of given user or community
     * @param requestAmount -- amount of posts to analyze.
     * It should satisfy a condition: amount at least 10, but not no more then 80.
     * @throws InvalidAmountException throws if amount doesn't satisfy the condition.
     */
    public ManagerVK(@NotNull String link, @NotNull String requestAmount) throws InvalidAmountException, InvalidPageLinkException, EmptyLinkException {
        logger.debug("Created new manager____________________________________");
        if (link.equals("")) {
            logger.debug("The link is empty");
            throw new EmptyLinkException();
        }

        if (!validateVkLink(link)) {
            logger.debug("Link is invalid");
            throw new InvalidPageLinkException();
        }

        Integer amount;
        try {
            amount = Integer.valueOf(requestAmount);
        } catch (Exception e) {
            logger.debug("Amount is not a number: {}", requestAmount);
            throw new InvalidAmountException();
        }

        if (amount < 10 || amount > 80) {
            logger.debug("Amount don't fit the condition. Amount: {}", amount);
            throw new InvalidAmountException();
        }

        this.amount = amount;
        logger.debug("Validation succeeded");
    }

    public @NotNull Statistics getStatistics() throws BadJsonResponseException, IOException, HiddenWallException, UserBannedOrDeletedException {
        User user = VkRequest.identify(userID);

        Map response = VkRequest.getWall(user, amount);
        if (response.containsKey("response")) {
            Map objects = (Map) response.get("response");
            // first one always total count
            return new Statistics(user, (List<Map>) objects.get("items"), amount);
        }

        if (response.containsKey("error")) {
            Map error = (Map) response.get("error");

            if ((int) error.get("error_code") == 15) {
                throw new HiddenWallException();
            }

            if ((int) error.get("error_code") == 18) {
                throw new UserBannedOrDeletedException();
            }
        }

        throw new BadJsonResponseException();
    }

    /**
     * Check link to vk belong.
     * @param link provided link
     * @return true if it's link with VK_PREFIX of VK_PREFIX without protocol(see constants).
     */
    private boolean isVkLink(@NotNull String link) {
        logger.debug("Is link {} a vk link?", link);
        if (link.startsWith(VK_PREFIX)) {
            logger.debug("Link started with {} and it's correct", VK_PREFIX);
            userID = link.replaceFirst("^" + VK_PREFIX, "");
            return true;
        }

        if (link.startsWith(VK_PREFIX_NO_PROTOCOL)) {
            logger.debug("Link started with {} and it's correct", VK_PREFIX_NO_PROTOCOL);
            userID = link.replaceFirst("^" + VK_PREFIX_NO_PROTOCOL, "");
            return true;
        }

        logger.debug("Link isn't a vk link");
        return false;
    }

    /**
     * Validation of provided link
     * @param link provided link
     * @return true, if it's link to vk user/community with vk prefix(see constants)
     * or it's just name of the user(i.e. VK_PREFIX + link)
     */
    private boolean validateVkLink(@NotNull String link) {
        logger.debug("Validating link: {}", link);
        if (!isVkLink(link)) {
            userID = link;
        }

        logger.debug("Trying to check existence of vk user: {}", VK_PREFIX + userID);
        try {
            URL request = new URL(VK_PREFIX + userID);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();

            if (connection.getResponseCode() == 404) {
                logger.debug("This user doesn't exist");
                return false;
            }
        } catch (Exception e) {
            logger.debug(Arrays.toString(e.getStackTrace()));
            return false;
        }

        logger.debug("Link confirmed");
        return true;
    }
}