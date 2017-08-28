package ru.spbau.shavkunov;

import org.junit.Test;
import ru.spbau.shavkunov.exceptions.EmptyLinkException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.exceptions.InvalidPageLinkException;
import ru.spbau.shavkunov.primitives.Post;
import ru.spbau.shavkunov.primitives.Statistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManagerVKTest {
    private void rightResponseTest(String user) throws Exception {
        ManagerVK manager;

        manager = new ManagerVK(user, "10");
        manager.getStatistics();

        manager = new ManagerVK("vk.com/" + user, "10");
        manager.getStatistics();

        manager = new ManagerVK("https://vk.com/" + user, "10");
        manager.getStatistics();
    }

    @Test
    public void rightGroupResponseTest() throws Exception {
        rightResponseTest("alfabank");
    }

    @Test
    public void rightPersonResponseTest() throws Exception {
        rightResponseTest("durov");
    }

    private void testPostsValidation(String user) throws Exception {
        ManagerVK vk = new ManagerVK(user, "20");
        Statistics stats = vk.getStatistics();

        List<Post> posts = new ArrayList();
        posts.add(stats.getBestLikesPost());
        posts.add(stats.getBestRepostsPost());
        posts.add(stats.getBestViewsPost());

        posts.add(stats.getWorseLikesPost());
        posts.add(stats.getWorseRepostsPost());
        posts.add(stats.getWorseViewsPost());

        posts.stream().forEach(post -> post.getDescription());
        posts.stream().forEach(post -> post.getText());
        posts.stream().forEach(post -> post.getImages());
        posts.stream().forEach(post -> post.getViews());
        posts.stream().forEach(post -> post.getReposts());
        posts.stream().forEach(post -> post.getLikes());

        boolean isLinksCorrect = true;
        for (Post post : posts) {
            URL url = new URL(post.getPostLink());
            url.openConnection();
        }
    }

    @Test
    public void groupPostsValidation() throws Exception {
        testPostsValidation("alfabank");
    }

    @Test
    public void personPostsValidation() throws Exception {
        testPostsValidation("durov");
    }

    @Test(expected = EmptyLinkException.class)
    public void testEmptyLink() throws Exception {
        ManagerVK vk = new ManagerVK("", "50");
    }

    @Test(expected = InvalidAmountException.class)
    public void testBadAmountLink() throws Exception {
        ManagerVK vk = new ManagerVK("durov", "blabal");
    }

    @Test(expected = InvalidAmountException.class)
    public void testNotEnoughAmountLink() throws Exception {
        ManagerVK vk = new ManagerVK("durov", "5");
    }

    @Test(expected = InvalidAmountException.class)
    public void testTooMuchAmountLink() throws Exception {
        ManagerVK vk = new ManagerVK("durov", "90");
    }

    @Test(expected = InvalidPageLinkException.class)
    public void invalidPageLink() throws Exception {
        ManagerVK vk = new ManagerVK("alfabank$$$$$", "50");
    }
}