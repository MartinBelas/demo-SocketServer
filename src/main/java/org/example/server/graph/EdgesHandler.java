package org.example.server.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.messageHandler.strategy.GraphOperationResult;
import org.example.server.messageHandler.strategy.RemoveEdgeStrategy;

import java.util.*;

public class EdgesHandler {

    private static final Logger logger = LogManager.getLogger(EdgesHandler.class);

    private static Collection<Edge> edges = new LinkedList<>(); //TODO which implementation class is best?

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

    public Collection<Edge> getAll() {

        Collection<Edge> deepCopy = new LinkedList<>();

        edges.forEach( (e) -> {
            deepCopy.add(new Edge(e.getX(), e.getY(), e.getWeight())); //TODO some Edges builder?
        });

        return deepCopy;
    }

    public static int getCount() {
        return edges.size();
    }
}
