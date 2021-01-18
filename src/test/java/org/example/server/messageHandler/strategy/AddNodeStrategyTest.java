package org.example.server.messageHandler.strategy;

import org.example.server.api.AcceptableClientMessage;
import org.example.server.messageHandler.ProcessMessageResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddNodeStrategyTest {

    @Test
    public void test_thatSucceed_whenAddingNewUniqueNode() throws Exception {

        String messageFromClient_1 = "ADD NODE aaa-bbb-ccc";
        String messageFromClient_2 = "ADD NODE xxx-yyy-zzz";

        ProcessMessageResult result;

        result = new AddNodeStrategy(messageFromClient_1).process();
        assertTrue(result.isOk());
        assertEquals(AcceptableClientMessage.ADD_NODE.getSuccessResponse(), result.getMessage());

        result = new AddNodeStrategy(messageFromClient_2).process();
        assertTrue(result.isOk());
        assertEquals(AcceptableClientMessage.ADD_NODE.getSuccessResponse(), result.getMessage());
    }

    @Test
    public void test_thatFailed_whenAddingNewDuplicitousNode() throws Exception {

        String messageFromClient_1 = "ADD NODE uuu-vvv";

        ProcessMessageResult result;

        result = new AddNodeStrategy(messageFromClient_1).process();
        assertTrue(result.isOk());
        assertEquals(AcceptableClientMessage.ADD_NODE.getSuccessResponse(), result.getMessage());

        result = new AddNodeStrategy(messageFromClient_1).process();
        assertFalse(result.isOk());
        assertEquals(AcceptableClientMessage.ADD_NODE.getFailureResponse(), result.getMessage());
    }
}