package org.example.server.api;

import java.util.UUID;

public class SessionResult {

    private final boolean isOk;
    private final UUID sessionId;
    private final QuitReason reason;
    private final String message;

    public SessionResult(boolean isOk, UUID id, QuitReason reason) {
        this(isOk, id, reason, null);
    }

    public SessionResult(boolean isOk, UUID id, QuitReason reason, String message) {
        this.isOk = isOk;
        this.sessionId = id;
        this.reason = reason;
        this.message = message;
    }

    public boolean isOk() {
        return isOk;
    }

    public QuitReason getQuitReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }
}
