package org.example.server.graph;

import org.example.server.messageHandler.strategy.GraphOperationResult;

import java.util.HashMap;
import java.util.Map;

public class NodesHandler {

    private static final Map<String, Node> nodes = new HashMap<>();

    public static GraphOperationResult add(Node node) {

        if (nodes.containsKey(node.getName())) {
            return new GraphOperationResult(false);
        }
        nodes.put(node.getName(), node);
        return new GraphOperationResult(true);
    }

    public static GraphOperationResult remove(Node node) {

        if (!nodes.containsKey(node.getName())) {
            return new GraphOperationResult(false);
        }
        nodes.remove(node.getName());
        return new GraphOperationResult(true);
    }

    public static boolean nodeExists(String nodeName) {
        return nodes.containsKey(nodeName);
    }

    public static Map<String, Node> getAll() {

        Map<String, Node> deepCopy = new HashMap<>();

        nodes.forEach( (nodeName, node) -> {
            deepCopy.put(nodeName, new Node(nodeName));
        });

        return deepCopy;
    }
}
