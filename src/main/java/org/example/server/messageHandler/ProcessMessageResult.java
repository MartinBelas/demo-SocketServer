package org.example.server.messageHandler;

import org.example.server.api.QuitReason;

public interface ProcessMessageResult {

    boolean isOk();

    String getMessage();

    QuitReason getQuitReason();
}
