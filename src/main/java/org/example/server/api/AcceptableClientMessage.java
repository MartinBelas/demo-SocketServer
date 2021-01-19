package org.example.server.api;

import java.util.Arrays;

public enum AcceptableClientMessage {
    GREETING("HI, I AM", "HI %s", null),
    GOOD_BY("BYE MATE!", "BYE %s, WE SPOKE FOR %d MS", null),
    ADD_NODE("ADD NODE", "NODE ADDED", "ERROR: NODE ALREADY EXISTS"),
    REMOVE_NODE("REMOVE NODE", "NODE REMOVED", "ERROR: NODE NOT FOUND"),
    ADD_EDGE("ADD EDGE", "EDGE ADDED", "ERROR: NODE NOT FOUND"),
    REMOVE_EDGE("REMOVE EDGE", "EDGE REMOVED", "ERROR: NODE NOT FOUND"),
    FIND_SHORTEST_PATH("SHORTEST PATH", "%d", "ERROR: NODE NOT FOUND"),
    GET_CLOSER_THAN("CLOSER THAN", "%s", "ERROR: NODE NOT FOUND");

    private final String message;
    private final String successResponse;
    private final String failureResponse;

    AcceptableClientMessage(String message, String successResponse, String failureResponse) {
        this.message = message;
        this.successResponse = successResponse;
        this.failureResponse = failureResponse;
    }

    public String getMessage() {
        return this.message;
    }

    public String getSuccessResponse() {
        return this.successResponse;
    }

    public String getFailureResponse() {
        return this.failureResponse;
    }

    /**
     * Compares message received from client with the set of acceptable messages.
     * We doesn't compare the whole message, but only the beginning which is constant,
     * because the message can also contain some variables.
     *
     * @param messageFromClient - message received from client
     * @return true if the message is acceptable, otherwise return false
     */
    public static boolean containsMessage(String messageFromClient) {
        return Arrays.stream(AcceptableClientMessage.values()).anyMatch(
                (x) -> {
                    if (messageFromClient.length() < x.getMessage().length()) {
                        return false;
                    }
                    return x.getMessage().equals(messageFromClient.substring(0, x.getMessage().length()));
                }
        );
    }
}
