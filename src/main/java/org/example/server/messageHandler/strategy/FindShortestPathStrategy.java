package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.*;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

import java.util.*;

/**
 * Finds the shortest path between two nodes using the Dijkstra’s algorithm
 */
public class FindShortestPathStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(FindShortestPathStrategy.class);

    protected FindShortestPathStrategy(String clientMessage) {
        super(clientMessage);
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

        int result = new GraphService().findShortestPathWeight(start, target);
        return new SuccessProcessResult(null, String.valueOf(result));
    }
}
