package ru.spbau.shavkunov;

import org.junit.Test;
import ru.spbau.shavkunov.primitives.PostQuantity;
import ru.spbau.shavkunov.primitives.Statistics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mikhail Shavkunov
 */
public class StatisticsTest {
    @Test
    public void getDoubleWithPrecision() throws Exception {
        double value = 1.111111111111;
        double actual = Statistics.getDoubleWithPrecision(value, 5);
        assertEquals(1.11111, actual, 0);

        double value1 = 5/3.0;
        double actual1 = Statistics.getDoubleWithPrecision(value1, 2);
        assertEquals(1.67, actual1, 0);
    }

    @Test
    public void countAverageQuantityTest() throws Exception {
        Statistics stats = mock(Statistics.class);
        Method method = Statistics.class.getDeclaredMethod("countAverageQuantity", PostQuantity.class, List.class);
        method.setAccessible(true);

        PostQuantity views = PostQuantity.VIEWS;
        PostQuantity reposts = PostQuantity.REPOSTS;
        PostQuantity likes = PostQuantity.LIKES;

        List list = new ArrayList();
        Map entity1 = mock(Map.class);
        Map entity2 = mock(Map.class);
        list.add(entity1);
        list.add(entity2);

        Map count1 = mock(Map.class);
        when(count1.get("count")).thenReturn(5);
        when(entity1.containsKey(likes.toString())).thenReturn(true);
        Map count2 = mock(Map.class);
        when(count2.get("count")).thenReturn(3);
        when(entity2.containsKey(likes.toString())).thenReturn(true);

        when(entity1.get(likes.toString())).thenReturn(count1);
        when(entity2.get(likes.toString())).thenReturn(count2);

        double actual = (double) method.invoke(stats, likes, list);
        assertEquals(4.0, actual, 0);
    }
}