package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.api.QuitReason;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

import java.io.PrintWriter;

public class GoodByStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(GoodByStrategy.class);

    protected GoodByStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process(PrintWriter out) {

        logger.info("Handle GoodByStrategy");
        return new SuccessProcessResult(QuitReason.GOOD_BYE_FROM_CLIENT, null);
    }
}
