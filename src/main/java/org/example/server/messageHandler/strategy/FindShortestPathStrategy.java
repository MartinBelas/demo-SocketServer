package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.Edge;
import org.example.server.graph.EdgesHandler;
import org.example.server.graph.Node;
import org.example.server.graph.NodesHandler;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

import java.util.*;

/**
 * Finds the shortest path between two nodes using the Dijkstra’s algorithm
 */
public class FindShortestPathStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(FindShortestPathStrategy.class);

    private static final int MAX_VALUE = Integer.MAX_VALUE;

    private Map<String, Node> unprocessed = NodesHandler.getAll();
    private Collection<Edge> allEdges = EdgesHandler.getAll();

    Set<Node> processed = new HashSet<>();
    Map<Node, Node> predecessors = new HashMap<Node, Node>();
    Map<Node, Integer> distances = new HashMap<>();

    protected FindShortestPathStrategy(String clientMessage) {
        super(clientMessage);
    }

    protected FindShortestPathStrategy(Map<String, Node> nodes, Collection<Edge> edges, String clientMessage) {
        super(clientMessage);
        this.unprocessed = nodes;
        this.allEdges = edges;
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle FindShortestPathStrategy");

        String[] words = clientMessage.substring(AcceptableClientMessage.FIND_SHORTEST_PATH.getMessage().length()).trim()
                .split("\\s+");

        Node start = new Node(words[0]);
        Node target = new Node(words[1]);

        logger.debug("start name: " + start.getName());
        logger.debug("target name: " + target.getName());

        int result = findShortestPathWeight(start, target);
        return new SuccessProcessResult(null, String.valueOf(result));
    }

    private int findShortestPathWeight(Node start, Node target) {

        int result = MAX_VALUE;

        distances.put(start, 0);
        unprocessed.put(start.getName(), start);

        while (!unprocessed.isEmpty()) {

            final Node current = getNearest(unprocessed);
            unprocessed.remove(current.getName());
            processed.add(current);
            findMinimalDistances(current);
        }

        if (distances.containsKey(target)) {
            result = distances.get(target);
        }

        return result;
    }

    private void findMinimalDistances(Node node) {

        List<Node> neighbors = getUnprocessedNeighbors(node);

        for (Node neighbor : neighbors) {
            if (getDistanceFromStart(neighbor) > getDistanceFromStart(node) + getDistanceBetween(node, neighbor)) {
                distances.put(neighbor, getDistanceFromStart(node) + getDistanceBetween(node, neighbor));
                predecessors.put(neighbor, node);
                unprocessed.put(neighbor.getName(), neighbor);
            }
        }
    }

    private Node getNearest(Map<String, Node> nodes) {

        Node nearest = null;

        for (Node n : nodes.values()) {
            if (nearest == null) {
                nearest = n;
            } else {
                if (getDistanceFromStart(n) < getDistanceFromStart(nearest)) {
                    nearest = n;
                }
            }
        }

        return nearest;
    }

    private List<Node> getUnprocessedNeighbors(Node node) {

        List<Node> neighbors = new ArrayList<>();

        for (Edge edge : allEdges) {
            if (edge.getX().getName().equals(node.getName())
                    && !processed.contains(edge.getY())) {
                neighbors.add(edge.getY());
            }
        }

        return neighbors;
    }

    private int getDistanceFromStart(Node target) {

        Integer d = distances.get(target);

        if (d == null) {
            return MAX_VALUE;
        } else {
            return d;
        }
    }

    private int getDistanceBetween(Node node, Node target) {

        Collection<Edge> edges = new ArrayList<>();

        for (Edge edge : allEdges) {
            if (edge.getX().equals(node)
                    && edge.getY().equals(target)) {
                edges.add(edge);
            }
        }

        Edge shortest = edges.stream().min(Comparator.comparing(Edge::getWeight)).orElse(null);

        return (shortest == null ? MAX_VALUE : shortest.getWeight());
    }
}
