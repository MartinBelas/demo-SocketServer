package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.GraphOperationResult;
import org.example.server.graph.Node;
import org.example.server.graph.NodesHandler;
import org.example.server.messageHandler.FailureProcessResult;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

public class AddNodeStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(AddNodeStrategy.class);

    protected AddNodeStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle AddNodeStrategy");

        String nodeId = (clientMessage.substring(AcceptableClientMessage.ADD_NODE.getMessage().length()).trim());
        Node node = new Node(nodeId);
        GraphOperationResult result = NodesHandler.add(node);

        String resultMessage;
        if (!result.isOk()) {
            resultMessage = AcceptableClientMessage.ADD_NODE.getFailureResponse();
        } else {
            resultMessage = AcceptableClientMessage.ADD_NODE.getSuccessResponse();
        }

        if (!result.isOk()) {
            return new FailureProcessResult(null, resultMessage);
        } else {
            return new SuccessProcessResult(null, resultMessage);
        }
    }
}
