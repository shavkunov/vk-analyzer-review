package ru.spbau.shavkunov;

import org.junit.Test;
import ru.spbau.shavkunov.network.Parameter;
import ru.spbau.shavkunov.users.Group;
import ru.spbau.shavkunov.users.Person;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static ru.spbau.shavkunov.network.Method.GROUP_GET_BY_ID;

/**
 * Created by Mikhail Shavkunov
 */
public class VkRequestTest {
    @Test
    public void identifyTest() throws Exception {
        String group = "alfabank";
        String person = "durov";

        assertEquals(Group.class, VkRequest.identify(group).getClass());
        assertEquals(Person.class, VkRequest.identify(person).getClass());
    }

    @Test
    public void getRequestUrlTest() throws Exception {
        URL url = VkRequest.getRequestUrl(GROUP_GET_BY_ID, new Parameter("key", "value"));

        String exceptedUrl = "https://api.vk.com/method/groups.getById?key=value";
        assertEquals(exceptedUrl, url.toString());

        URL url1 = VkRequest.getRequestUrl(GROUP_GET_BY_ID, new Parameter("key1", "value1"),
                                                            new Parameter("key2", "value2"));

        String exceptedUrl1 = "https://api.vk.com/method/groups.getById?key1=value1&key2=value2";
        assertEquals(exceptedUrl1, url1.toString());
    }
}