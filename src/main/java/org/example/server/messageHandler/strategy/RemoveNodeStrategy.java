package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.Node;
import org.example.server.graph.NodesHandler;
import org.example.server.messageHandler.FailureProcessResult;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

public class RemoveNodeStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(RemoveNodeStrategy.class);

    protected RemoveNodeStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle RemoveNodeStrategy");

        String nodeId = (clientMessage.substring(AcceptableClientMessage.REMOVE_NODE.getMessage().length()).trim());
        Node node = new Node(nodeId);
        GraphOperationResult result = NodesHandler.remove(node);

        String resultMessage;
        if (!result.isOk()) {
            resultMessage = AcceptableClientMessage.REMOVE_NODE.getFailureResponse();
        } else {
            resultMessage = AcceptableClientMessage.REMOVE_NODE.getSuccessResponse();
        }

        if (!result.isOk()) {
            return new FailureProcessResult(null, resultMessage);
        } else {
            return new SuccessProcessResult(null, resultMessage);
        }
    }
}
