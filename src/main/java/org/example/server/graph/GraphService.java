package org.example.server.graph;

import java.util.*;
import java.util.stream.Collectors;

public class GraphService {

    private static final int MAX_VALUE = Integer.MAX_VALUE;

    private Map<String, Node> unprocessed = NodesHandler.getAll();
    private Collection<Edge> allEdges = EdgesHandler.getAll();

    private Set<Node> processed = new HashSet<>();
    private Map<Node, Node> predecessors = new HashMap<Node, Node>();
    private Map<Node, Integer> distances = new HashMap<>();
    private boolean graphProcessed = false;

    public GraphService() {
    }

    /**
     *  Calculates the shortest path between two nodes in the directed graph
     *
     * @param start node
     * @param target node
     * @return distance of the shortest path
     */
    public int findShortestPathWeight(Node start, Node target) {

        int result = MAX_VALUE;

        this.processGraph(start);

        if (this.distances.containsKey(target)) {
            result = this.distances.get(target);
        }

        return result;
    }

    /**
     * Provides unsorted list of nodes which are 'closer' to the given node
     * than the given weight.
     *
     * @param node
     * @param weight
     * @return list of nodes which are closer
     */
    public List<Node> getCloserThan(Node node, int weight) {

        this.processGraph(node);

        return this.distances.entrySet().stream()
                .filter(x -> x.getValue() < weight)
                .map(x -> x.getKey())
                .collect(Collectors.toList());
    }

    private void processGraph(Node start) {

        if (this.graphProcessed == false) {

            this.distances.put(start, 0);
            this.unprocessed.put(start.getName(), start);

            while (!this.unprocessed.isEmpty()) {

                final Node current = getNearest(this.unprocessed);
                this.unprocessed.remove(current.getName());
                this.processed.add(current);
                findMinimalDistances(current);
            }

            this.graphProcessed = true;
        }
    }

    private void findMinimalDistances(Node node) {

        List<Node> neighbors = getUnprocessedNeighbors(node);

        for (Node neighbor : neighbors) {
            if (getDistanceFromStart(neighbor) > getDistanceFromStart(node) + getDistanceBetween(node, neighbor)
                    && getDistanceFromStart(node) + getDistanceBetween(node, neighbor) > 0) {
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
