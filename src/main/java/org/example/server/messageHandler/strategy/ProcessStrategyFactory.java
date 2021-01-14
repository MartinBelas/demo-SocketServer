package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;

public class ProcessStrategyFactory {

    private static final Logger logger = LogManager.getLogger(ProcessStrategyFactory.class);

    private enum ProcessStrategyType {
        I_DONT_UNDERSTAND,
        GREETING,
        GOOD_BY,
        ADD_NODE,
        REMOVE_NODE,
        ADD_EDGE,
        REMOVE_EDGE,
        NONE;
    }

    public static ProcessStrategy getStrategy(String clientMessage) {

        logger.debug("Selecting process strategy...");
        ProcessStrategyType strategyType = getStrategyType(clientMessage);

        logger.debug("Selected process strategy: " + strategyType.toString());

        switch (strategyType) {

            case I_DONT_UNDERSTAND:
                return new IDontUnderstandStrategy(clientMessage);

            case GREETING:
                return new GreetingStrategy(clientMessage);

            case GOOD_BY:
                return new GoodByStrategy(clientMessage);

            case ADD_NODE:
                return new AddNodeStrategy(clientMessage);

            case REMOVE_NODE:
                return new RemoveNodeStrategy(clientMessage);

            case ADD_EDGE:
                return new AddEdgeStrategy(clientMessage);

            case REMOVE_EDGE:
                return new RemoveEdgeStrategy(clientMessage);

            default:
                return new NoneStrategy(clientMessage);
        }
    }

    private static ProcessStrategyType getStrategyType(String clientMessage) { //TODO unit test

        if (clientMessage == null) {
            return ProcessStrategyType.NONE;
        }

        // I_DONT_UNDERSTAND
        if (!AcceptableClientMessage.containsMessage(clientMessage)) {
            return ProcessStrategyType.I_DONT_UNDERSTAND;
        }

        // GREETING
        if (clientMessage.startsWith(AcceptableClientMessage.GREETING.getMessage())) {
            return ProcessStrategyType.GREETING;
        }

        // GOOD_BY
        if (clientMessage.equals(AcceptableClientMessage.GOOD_BY.getMessage())) {
            return ProcessStrategyType.GOOD_BY;
        }

        // ADD_NODE
        if (clientMessage.substring(0, AcceptableClientMessage.ADD_NODE.getMessage().length())
                .equals(AcceptableClientMessage.ADD_NODE.getMessage())) {
            return ProcessStrategyType.ADD_NODE;
        }

        // REMOVE_NODE
        if (clientMessage.substring(0, AcceptableClientMessage.REMOVE_NODE.getMessage().length())
                .equals(AcceptableClientMessage.REMOVE_NODE.getMessage())) {
            return ProcessStrategyType.REMOVE_NODE;
        }

        // ADD_EDGE
        if (clientMessage.substring(0, AcceptableClientMessage.ADD_EDGE.getMessage().length())
                .equals(AcceptableClientMessage.ADD_EDGE.getMessage())) {
            return ProcessStrategyType.ADD_EDGE;
        }

        // REMOVE_EDGE
        if (clientMessage.substring(0, AcceptableClientMessage.REMOVE_EDGE.getMessage().length())
                .equals(AcceptableClientMessage.REMOVE_EDGE.getMessage())) {
            return ProcessStrategyType.REMOVE_EDGE;
        }

        return ProcessStrategyType.NONE;
    }
}
