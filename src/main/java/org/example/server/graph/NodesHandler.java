package org.example.server.graph;

import org.example.server.messageHandler.strategy.GraphOperationResult;

import java.util.HashMap;
import java.util.Map;

public class NodesHandler {

    private static Map<String, Node> nodes = new HashMap<>();

    public static GraphOperationResult addNode(Node node) {

        if (nodes.containsKey(node.getName())) {
            return new GraphOperationResult(false);
        }
        nodes.put(node.getName(), node);
        return new GraphOperationResult(true);
    }

    public static GraphOperationResult removeNode(Node node) {

        if (!nodes.containsKey(node.getName())) {
            return new GraphOperationResult(false);
        }
        nodes.remove(node.getName()); //TODO unit test
        return new GraphOperationResult(true);
    }

    public static boolean nodeExists(String nodeName) {
        return nodes.containsKey(nodeName);
    }

    public static Map<String, Node> getAllNodes() {

        Map<String, Node> deepCopy = new HashMap<>();

        nodes.forEach( (nodeName, node) -> {
            deepCopy.put(nodeName, new Node(nodeName)); //TODO some node builder?
        });

        return deepCopy;
    }
}
