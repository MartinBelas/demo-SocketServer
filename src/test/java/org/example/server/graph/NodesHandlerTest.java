package org.example.server.graph;

import org.example.server.messageHandler.strategy.GraphOperationResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodesHandlerTest {

    NodesHandler nodesHandler;

    @Before
    public void setUp() {
        nodesHandler = new NodesHandler();
    }

    @Test
    public void testAddNode() throws InterruptedException {

        String nodeName = "node-001";
        Node node = new Node(nodeName);
        GraphOperationResult result;

        result = nodesHandler.add(node);
        assertEquals(true, result.isOk());
        assertEquals(1, nodesHandler.getAll().size());

        result = nodesHandler.add(node);
        assertEquals(false, result.isOk());
        assertEquals(1, nodesHandler.getAll().size());
    }
}
