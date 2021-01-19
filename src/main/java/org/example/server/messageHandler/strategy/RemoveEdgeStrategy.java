package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.Edge;
import org.example.server.graph.EdgesHandler;
import org.example.server.graph.GraphOperationResult;
import org.example.server.graph.Node;
import org.example.server.messageHandler.FailureProcessResult;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

public class RemoveEdgeStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(RemoveEdgeStrategy.class);

    protected RemoveEdgeStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle RemoveEdgeStrategy");


        String[] words = clientMessage.substring(AcceptableClientMessage.ADD_EDGE.getMessage().length()).trim()
                .split("\\s+");

        String nodeX = words[0];
        String nodeY = words[1];
        Edge edge = new Edge(new Node(nodeX), new Node(nodeY), -1);

        GraphOperationResult result = EdgesHandler.remove(edge);

        String resultMessage;
        if (!result.isOk()) {
            resultMessage = AcceptableClientMessage.REMOVE_EDGE.getFailureResponse();
        } else {
            resultMessage = AcceptableClientMessage.REMOVE_EDGE.getSuccessResponse();
        }

        if (!result.isOk()) {
            return new FailureProcessResult(null, resultMessage);
        } else {
            return new SuccessProcessResult(null, resultMessage);
        }
    }
}
