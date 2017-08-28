package ru.spbau.shavkunov;

import org.junit.Test;
import ru.spbau.shavkunov.primitives.Post;
import ru.spbau.shavkunov.users.Person;
import ru.spbau.shavkunov.users.User;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostTest {
    @Test
    public void getPostLinkTest() throws Exception {
        User owner = mock(Person.class);
        when(owner.getLink()).thenReturn("userLink");
        int userID = 123;
        int postID = 321;
        when(owner.getID()).thenReturn(userID);

        Map json = mock(Map.class);
        when(json.get("id")).thenReturn(postID);

        Post post = mock(Post.class);

        Method method = Post.class.getDeclaredMethod("getPostLink", User.class, Map.class);
        method.setAccessible(true);

        String actual = (String) method.invoke(post, owner, json);
        String excepted = "userLink?w=wall" + userID + "_" + postID;
        assertEquals(actual, excepted);
    }

    @Test
    public void countPhotoAttachmentsTest() throws Exception {
        Post post = mock(Post.class);
        Map attach = mock(Map.class);
        when(attach.get("type")).thenReturn("photo");
        Map json = mock(Map.class);
        List attachList = new ArrayList<>();
        attachList.add(attach);
        when(json.get("attachments")).thenReturn(attachList);

        Map jsonNoAttach = mock(Map.class);
        when(jsonNoAttach.get("attachments")).thenReturn(null);

        Method method = Post.class.getDeclaredMethod("countPhotoAttachments", Map.class);
        method.setAccessible(true);
        assertEquals((long) 0, method.invoke(post, jsonNoAttach));

        assertEquals((long) 1, method.invoke(post, json));
    }
}
