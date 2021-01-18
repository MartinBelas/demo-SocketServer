package org.example.server.messageHandler.strategy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProcessStrategyFactoryTest {


    //TODO use parameterized test for all messages and strategies

    @Test
    public void test_GreetingMessage() {

        String clientMessage = "HI, I AM John Lennon";

        ProcessStrategy strategy = ProcessStrategyFactory.getStrategy(clientMessage);

        assertEquals(GreetingStrategy.class, strategy.getClass());
    }

    @Test
    public void test_GoodByMessage() {

        String clientMessage = "BYE MATE!";

        ProcessStrategy strategy = ProcessStrategyFactory.getStrategy(clientMessage);

        assertEquals(GoodByStrategy.class, strategy.getClass());
    }

    @Test
    public void test_FindShortestPathMessage() {

        String clientMessage = "SHORTEST PATH x y";

        ProcessStrategy strategy = ProcessStrategyFactory.getStrategy(clientMessage);

        assertEquals(FindShortestPathStrategy.class, strategy.getClass());
    }

}