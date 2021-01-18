package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.QuitReason;
import org.example.server.messageHandler.FailureProcessResult;
import org.example.server.messageHandler.ProcessMessageResult;

public class NoneStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(NoneStrategy.class);

    protected NoneStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.warn("Handle NoneStrategy");
        return new FailureProcessResult(QuitReason.ERROR, "No process strategy");
    }
}
