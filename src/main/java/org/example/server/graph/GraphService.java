package org.example.server.graph;

import java.util.*;

public class GraphService {

    private static final int MAX_VALUE = Integer.MAX_VALUE;

    private Map<String, Node> unprocessed = NodesHandler.getAll();
    private Collection<Edge> allEdges = EdgesHandler.getAll();

    private Set<Node> processed = new HashSet<>();
    private Map<Node, Node> predecessors = new HashMap<Node, Node>();
    private Map<Node, Integer> distances = new HashMap<>();

    public GraphService() {
    }

    public int findShortestPathWeight(Node start, Node target) {

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
