package org.example.server;

import java.util.Arrays;

public enum AcceptableClientMessage {
    GREETING("HI, I AM", "HI %s"),
    GOOD_BY("BYE MATE!", "BYE %s, WE SPOKE FOR %d MS");

    private final String message;
    private final String response;

    AcceptableClientMessage(String message, String response) {
        this.message = message;
        this.response = response;
    }

    public String getMessage() {
        return this.message;
    }

    public String getResponse() {
        return this.response;
    };

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
