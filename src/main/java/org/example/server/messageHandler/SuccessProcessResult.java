package org.example.server.messageHandler;

import org.example.server.api.QuitReason;

public class SuccessProcessResult implements ProcessMessageResult {

    private final String message;
    private final QuitReason reason;

    public SuccessProcessResult(QuitReason reason, String message) {
        this.reason = reason;
        this.message = message;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public QuitReason getQuitReason() {
        return this.reason;
    }
}
