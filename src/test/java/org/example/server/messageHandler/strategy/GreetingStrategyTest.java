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

        ProcessMessageResult result = new GreetingStrategy(messageFromClient).process();

        assertTrue(result.isOk());
        assertEquals(name, result.getMessage());
    }
}