package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;

public class ProcessStrategyFactory {

    private static final Logger logger = LogManager.getLogger(ProcessStrategyFactory.class);

    protected enum ProcessStrategyType {
        I_DONT_UNDERSTAND,
        GREETING,
        GOOD_BY,
        ADD_NODE,
        REMOVE_NODE,
        ADD_EDGE,
        REMOVE_EDGE,
        FIND_SHORTEST_PATH,
        GET_CLOSER_THAN,
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

            case FIND_SHORTEST_PATH:
                return new FindShortestPathStrategy(clientMessage);

            case GET_CLOSER_THAN:
                return new GetCloserThanStrategy(clientMessage);

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

        // FIND_SHORTEST_PATH
        if (clientMessage.substring(0, AcceptableClientMessage.FIND_SHORTEST_PATH.getMessage().length())
                .equals(AcceptableClientMessage.FIND_SHORTEST_PATH.getMessage())) {
            return ProcessStrategyType.FIND_SHORTEST_PATH;
        }

        //GET_CLOSER_THAN
        if (clientMessage.substring(0, AcceptableClientMessage.GET_CLOSER_THAN.getMessage().length())
                .equals(AcceptableClientMessage.GET_CLOSER_THAN.getMessage())) {
            return ProcessStrategyType.GET_CLOSER_THAN;
        }

        return ProcessStrategyType.NONE;
    }
}
