package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.GraphService;
import org.example.server.graph.Node;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Finds the shortest path between two nodes using the Dijkstra’s algorithm
 */
public class GetCloserThanStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(GetCloserThanStrategy.class);

    protected GetCloserThanStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle GetCloserThanStrategy");

        String[] words = clientMessage.substring(AcceptableClientMessage.GET_CLOSER_THAN.getMessage().length()).trim()
                .split("\\s+");

        int weight = Integer.valueOf(words[0]);
        Node node = new Node(words[1]);

        List<Node> resultNodes = new GraphService().getCloserThan(node, weight);

        String result = resultNodes.stream()
                .map(n -> n.getName())
                .sorted()
                .skip(1)
                .collect(Collectors.joining( "," ));

        return new SuccessProcessResult(null, result);
    }
}
