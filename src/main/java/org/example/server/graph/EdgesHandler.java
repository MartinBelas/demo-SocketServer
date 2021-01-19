package org.example.server.graph;

import java.util.*;

public class EdgesHandler {

        private static final Collection<Edge> edges = new LinkedList<>();

    public static GraphOperationResult add(Edge edge) {
        edges.add(edge);
        return new GraphOperationResult(true);
    }

    public static GraphOperationResult remove(Edge edge) {

        if (!edgeCanExist(edge)) {
            return new GraphOperationResult(false);
        }

        edges.stream().filter( e -> !(Objects.equals(e.getX(), edge.getX())) && !(e.getY().equals(edge.getY()))); //TODO unit test

        return new GraphOperationResult(true);
    }

    public static boolean edgeCanExist(Edge edge) {

        if (!NodesHandler.nodeExists(edge.getX().getName()) && !NodesHandler.nodeExists(edge.getY().getName())) {
            return false;
        }

        if (!NodesHandler.nodeExists(edge.getX().getName())) {
            return true;
        }

        return false;
    }

    public static Collection<Edge> getAll() {

        Collection<Edge> deepCopy = new LinkedList<>();

        edges.forEach( (e) -> {
            deepCopy.add(new Edge(e.getX(), e.getY(), e.getWeight())); //TODO some Edges builder?
        });

        return deepCopy;
    }
}
