package org.example.server.messageHandler.strategy;

abstract public class AbstractProcessStrategy implements ProcessStrategy {

    protected final String clientMessage;

    protected AbstractProcessStrategy(String clientMessage) {
        this.clientMessage = clientMessage;
    }
}
