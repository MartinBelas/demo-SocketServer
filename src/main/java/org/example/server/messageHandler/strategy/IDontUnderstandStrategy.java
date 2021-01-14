package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

import java.io.PrintWriter;

public class IDontUnderstandStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(IDontUnderstandStrategy.class);

    private final static String I_DONT_UNDERSTAND_MESSAGE = "SORRY, I DID NOT UNDERSTAND THAT";

    protected IDontUnderstandStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process(PrintWriter out) {

        logger.warn("Handle IDontUnderstandStrategy");

        out.println(I_DONT_UNDERSTAND_MESSAGE);
        out.flush();

        return new SuccessProcessResult(null, I_DONT_UNDERSTAND_MESSAGE);
    }
}
