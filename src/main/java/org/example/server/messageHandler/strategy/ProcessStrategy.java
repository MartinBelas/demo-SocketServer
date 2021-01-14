package org.example.server.messageHandler.strategy;

import org.example.server.messageHandler.ProcessMessageResult;

import java.io.PrintWriter;

public interface ProcessStrategy {

        ProcessMessageResult process(PrintWriter out);
}
