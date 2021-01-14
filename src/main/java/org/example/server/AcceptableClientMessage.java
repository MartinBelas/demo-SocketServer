package org.example.server;

import java.util.Arrays;

public enum ClientMessage {
    GOOD_BY("BYE MATE!"),
    ADD_NODE("ADD NODE");

    private final String message;

    ClientMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
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
        return Arrays.stream(ClientMessage.values()).anyMatch(
                (x) -> x.getMessage().equals(messageFromClient)
        );
    }
}
