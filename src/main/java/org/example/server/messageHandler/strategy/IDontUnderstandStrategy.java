package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

public class IDontUnderstandStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(IDontUnderstandStrategy.class);

    private final static String I_DONT_UNDERSTAND_MESSAGE = "SORRY, I DID NOT UNDERSTAND THAT";

    protected IDontUnderstandStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.warn("Handle IDontUnderstandStrategy");
        return new SuccessProcessResult(null, I_DONT_UNDERSTAND_MESSAGE);
    }
}
