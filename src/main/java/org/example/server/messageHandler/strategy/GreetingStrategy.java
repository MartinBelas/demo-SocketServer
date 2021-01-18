package org.example.server.messageHandler.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.server.api.AcceptableClientMessage;
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.SuccessProcessResult;

public class GreetingStrategy extends AbstractProcessStrategy implements ProcessStrategy {

    private static final Logger logger = LogManager.getLogger(GreetingStrategy.class);

    public GreetingStrategy(String clientMessage) {
        super(clientMessage);
    }

    @Override
    public ProcessMessageResult process() {

        logger.info("Handle GreetingStrategy");

        String clientName = clientMessage.substring(AcceptableClientMessage.GREETING.getMessage().length()).trim();

        return new SuccessProcessResult(null, clientName);
    }
}
