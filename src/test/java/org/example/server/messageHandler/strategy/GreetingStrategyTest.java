package org.example.server.messageHandler.strategy;

import org.example.server.api.AcceptableClientMessage;
import org.example.server.messageHandler.ProcessMessageResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreetingStrategyTest {

    @Test
    public void test_thatSucceed_whenGreeting() {

        String name = "Karel Gott";

        String messageFromClient = String.format("HI, I AM %s", "Karel Gott");
        String expected = String.format("HI %s", "Karel Gott");

        ProcessMessageResult result = new GreetingStrategy(messageFromClient).process();
        String resultMessage = String.format(result.getMessage(), name);

        assertTrue(result.isOk());
        assertEquals(expected, resultMessage);
    }
}