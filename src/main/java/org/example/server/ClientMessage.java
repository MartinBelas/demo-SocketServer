package org.example.server;

import java.util.Arrays;

public enum ClientMessage {
    GOOD_BY("BYE MATE!");

    private final String message;

    ClientMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static boolean containsMessage(String m) {
        return Arrays.stream(ClientMessage.values()).anyMatch((x) -> x.getMessage().equals(m));
    }
}
