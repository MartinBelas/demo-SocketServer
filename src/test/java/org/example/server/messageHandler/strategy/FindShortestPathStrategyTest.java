package org.example.server.messageHandler.strategy;

import org.example.server.graph.Edge;
import org.example.server.graph.EdgesHandler;
import org.example.server.graph.Node;
import org.example.server.graph.NodesHandler;
import org.example.server.messageHandler.ProcessMessageResult;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FindShortestPathStrategyTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        generateNodes();
        generateEdges();
    }

    private static void generateNodes() {
        for (int i = 0; i < 11; i++) {
            NodesHandler.add(new Node("Node_" + i));
        }
    }

    private static void generateEdges() {
        
        EdgesHandler.add(new Edge(new Node("Node_" + 0), new Node("Node_" + 1), 85));
        EdgesHandler.add(new Edge(new Node("Node_" + 0), new Node("Node_" + 2), 217));
        EdgesHandler.add(new Edge(new Node("Node_" + 0), new Node("Node_" + 4), 173));
        EdgesHandler.add(new Edge(new Node("Node_" + 2), new Node("Node_" + 6), 186));
        EdgesHandler.add(new Edge(new Node("Node_" + 2), new Node("Node_" + 7), 103));
        EdgesHandler.add(new Edge(new Node("Node_" + 3), new Node("Node_" + 7), 183));
        EdgesHandler.add(new Edge(new Node("Node_" + 5), new Node("Node_" + 8), 250));
        EdgesHandler.add(new Edge(new Node("Node_" + 8), new Node("Node_" + 9), 84));
        EdgesHandler.add(new Edge(new Node("Node_" + 7), new Node("Node_" + 9), 167));
        EdgesHandler.add(new Edge(new Node("Node_" + 4), new Node("Node_" + 9), 502));
        EdgesHandler.add(new Edge(new Node("Node_" + 9), new Node("Node_" + 10), 40));
        EdgesHandler.add(new Edge(new Node("Node_" + 1), new Node("Node_" + 10), 600));
    }

    @Test
    public void testExcuteExisting() {

        String message = "SHORTEST PATH Node_0 Node_6";

        FindShortestPathStrategy strategy =
                new FindShortestPathStrategy(NodesHandler.getAll(), EdgesHandler.getAll(), message);

        ProcessMessageResult result = strategy.process();

        System.out.println(" ---> RESULT : " + result.getMessage());

        assertNotNull(result);
        assertTrue(Integer.valueOf(Integer.valueOf(result.getMessage())) > 0);
        assertFalse(Integer.valueOf(result.getMessage()).equals(Integer.MAX_VALUE));
    }

    @Test
    public void testExcuteNotExisting() {

        String message = "SHORTEST PATH Node_6 Node_0";

        FindShortestPathStrategy strategy =
                new FindShortestPathStrategy(NodesHandler.getAll(), EdgesHandler.getAll(), message);

        ProcessMessageResult result = strategy.process();

        System.out.println(" ---> RESULT : " + result.getMessage());

        assertNotNull(result);
        assertTrue(Integer.valueOf(result.getMessage()).equals(Integer.MAX_VALUE));
    }
}