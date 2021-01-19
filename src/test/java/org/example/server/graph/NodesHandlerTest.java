package org.example.server.graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodesHandlerTest {

    @Before
    public void setUp() {
        NodesHandler.clear();
    }

    @Test
    public void testAddNode() throws InterruptedException {

        String nodeName = "node-001";
        Node node = new Node(nodeName);
        GraphOperationResult result;

        result = NodesHandler.add(node);
        assertEquals(true, result.isOk());
        assertEquals(1, NodesHandler.getAll().size());

        result = NodesHandler.add(node);
        assertEquals(false, result.isOk());
        assertEquals(1, NodesHandler.getAll().size());
    }
}
