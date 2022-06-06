package com.example.gym;

import com.example.gym.data.ComFunctions;

import org.junit.Test;
import static org.junit.Assert.*;

public class test {
    @Test
    public void data_correct1() throws Exception {
        boolean expected = true;
        String data1 = "10/10/2000";
        String data2 = "10/10/2001";
        boolean actual = ComFunctions.isCorrectHour(data1, data2);
        assertEquals(expected, actual);
    }
}
