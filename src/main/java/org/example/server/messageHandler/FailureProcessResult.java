package org.example.server.messageHandler;

import org.example.server.api.QuitReason;

public class FailureProcessResult implements ProcessMessageResult {

    private final String message;
    private final QuitReason reason;

    public FailureProcessResult(QuitReason reason, String message) {
        this.reason = reason;
        this.message = message;
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public QuitReason getQuitReason() {
        return this.reason;
    }
}
