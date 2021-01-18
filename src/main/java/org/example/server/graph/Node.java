package org.example.server.graph;

import java.util.Objects;

public class Node {

    private final String name;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return getName().equals(node.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
