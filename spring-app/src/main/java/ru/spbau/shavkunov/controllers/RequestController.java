package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.spbau.shavkunov.ManagerVK;
import ru.spbau.shavkunov.controllers.response.Request;
import ru.spbau.shavkunov.controllers.response.Response;
import ru.spbau.shavkunov.controllers.response.exceptions.WrongResponseInitException;
import ru.spbau.shavkunov.exceptions.*;
import ru.spbau.shavkunov.primitives.Statistics;
import ru.spbau.shavkunov.services.DataRepository;

import static ru.spbau.shavkunov.controllers.response.ResponseDescription.*;

/**
 * Class which handles client requests.
 */
@RestController
public class RequestController {

    @Autowired
    private @NotNull DataRepository repository;

    /**
     * Handling main request.
     * @param request a client main request for collecting statistics.
     * @return Response class converted to json. (@see Response).
     */
    @RequestMapping(value = "/getStats", method = RequestMethod.POST,
                    produces = "application/json", consumes = "application/json")
    public @NotNull Response getStatistics(@RequestBody Request request) throws WrongResponseInitException {
        String pageLink = request.getLink();
        String postsAmount = request.getPosts();

        try {
            ManagerVK vk = new ManagerVK(pageLink, postsAmount);
            Response response = new Response(vk.getStatistics());

            return response;
        } catch (InvalidPageLinkException exception) {
            exception.printStackTrace();
            return new Response(INVALID_LINK);
        } catch (InvalidAmountException exception) {
            exception.printStackTrace();
            return new Response(INVALID_AMOUNT);
        } catch (EmptyLinkException exception) {
            exception.printStackTrace();
            return new Response(EMPTY_LINK);
        } catch (HiddenWallException exception) {
            exception.printStackTrace();
            return new Response(HIDDEN_WALL);
        } catch (UserBannedOrDeletedException exception) {
            exception.printStackTrace();
            return new Response(BANNED_USER);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Response(INTERNAL_ERROR);
        }
    }

    /**
     * Handles save request of statistics
     * @param stats same as from json which can be accessed from method getStatistics above.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/saveStats")
    public void saveStats(@RequestBody @NotNull Statistics stats) {
        repository.save(stats);
    }
}