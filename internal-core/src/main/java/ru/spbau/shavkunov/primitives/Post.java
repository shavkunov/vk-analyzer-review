package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spbau.shavkunov.users.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.spbau.shavkunov.VkRequest.getImageURL;
import static ru.spbau.shavkunov.primitives.PostQuantity.*;

/**
 * Class describes information of vk post. (see https://vk.com/dev/post).
 * These properties will be saved in database.
 */
@Entity
public class Post {
    private static final @NotNull Logger logger = LoggerFactory.getLogger(Post.class);

    @Id
    private @Nullable String id;

    // TODO : save other post's information

    /**
     * Posts text.
     */
    private @NotNull String text;

    /**
     * Posts description. It's mean that post has best/worst parameter.
     * Parameters are likes, reposts and views.
     */
    private @NotNull String description;

    /**
     * Url to post.
     */
    private @NotNull String postLink;

    // amount of each parameters.
    private int likes;
    private int reposts;
    private int views;

    /**
     * Images attached to post
     */
    @ElementCollection(targetClass=String.class)
    private @Nullable List<String> images;

    public Post(@NotNull User owner, @NotNull Map json, @NotNull PostCategory category) throws IOException {
        logger.debug("Posts map: {}", json.toString());
        text = (String) json.get("text");
        likes = (int) ((Map) json.get(LIKES.toString())).get("count");
        reposts = (int) ((Map) json.get(REPOSTS.toString())).get("count");

        if (json.containsKey(VIEWS.toString())) {
            views = (int) ((Map) json.get(VIEWS.toString())).get("count");
        } else {
            views = 0;
        }
        description = category.toString();
        postLink = getPostLink(owner, json);
        logger.debug("Post basic are initialized");

        if (countPhotoAttachments(json) == 0) {
            logger.debug("Post has no image attach");
            images = new ArrayList<>();
            images.add(owner.getPhoto().toString());
            return;
        }

        images = getAttachedImageURLs(owner, json);
    }

    private @NotNull String getPostLink(@NotNull User owner, @NotNull Map json) {
        int postID = (int) json.get("id");
        String url = owner.getLink() + "?w=wall" + owner.getID() + "_" + postID;

        logger.debug("Created link: {}", url);
        return url;
    }

    private @NotNull List<String> getAttachedImageURLs(@NotNull User owner, @NotNull Map json) throws IOException {
        logger.debug("Trying to get attached images");
        List<Map> attach = (List) json.get("attachments");
        List<String> photoURLs = attach.stream()
                .filter(map -> map.get("type").equals("photo"))
                .map(map -> (Map) map.get("photo"))
                .map(map -> (Integer) map.get("id"))
                .map(photoID -> getImageURL(owner, photoID))
                .collect(Collectors.toList());

        logger.debug("Collected next urls:");
        photoURLs.forEach(logger::debug);
        // TODO: make other class to manage with photos.
        return photoURLs;
    }

    private long countPhotoAttachments(@NotNull Map json) {
        logger.debug("Count photo attachments");
        List<Map> attach = (List) json.get("attachments");

        if (attach == null) {
            logger.debug("There is no attach");
            return 0;
        }

        long answer = attach.stream()
                     .filter(map -> map.get("type").equals("photo"))
                     .count();

        logger.debug("Count: {}", answer);
        return answer;
    }

    public @NotNull String getPostLink() {
        return postLink;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }

    public int getReposts() {
        return reposts;
    }

    public int getViews() {
        return views;
    }

    public @NotNull String getText() {
        return text;
    }

    public @NotNull List<String> getImages() {
        return images;
    }
}
