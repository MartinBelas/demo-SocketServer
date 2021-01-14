package org.example.server.graph;

public class Edge {

    private final Node x;
    private final Node y;
    private final int weight;

    public Edge(Node x, Node y, int weight) {

        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public Node getX() {
        return x;
    }

    public Node getY() {
        return y;
    }

    public int getWeight() {
        return weight;
    }
}
