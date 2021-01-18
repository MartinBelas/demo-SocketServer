package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.graph.Edge;
import org.example.server.graph.EdgesHandler;
import org.example.server.graph.Node;
import org.example.server.graph.NodesHandler;
import org.example.server.messageHandler.FailureProcessResult;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

public class AddEdgeStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(AddEdgeStrategy.class);

    protected AddEdgeStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle AddEdgeStrategy");

        String[] words = clientMessage.substring(AcceptableClientMessage.ADD_EDGE.getMessage().length()).trim()
                .split("\\s+");

        String nodeX = words[0];
        String nodeY = words[1];
        String weignt = words[2];

        String resultMessage;
        GraphOperationResult result;

        if (!NodesHandler.nodeExists(nodeX) || !NodesHandler.nodeExists(nodeY)) {
            resultMessage = AcceptableClientMessage.ADD_EDGE.getFailureResponse();
            result = new GraphOperationResult(false);
        } else {
            Edge edge = new Edge(new Node(nodeX), new Node(nodeY), Integer.parseInt(weignt));
            result = EdgesHandler.add(edge);
        }

        if (result.isOk()) {
            resultMessage = AcceptableClientMessage.ADD_EDGE.getSuccessResponse();
            return new SuccessProcessResult(null, resultMessage);
        } else {
            resultMessage = AcceptableClientMessage.ADD_EDGE.getFailureResponse();
            return new FailureProcessResult(null, resultMessage);
        }
    }
}
