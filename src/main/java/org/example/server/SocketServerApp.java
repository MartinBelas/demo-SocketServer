package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketServerApp {

    private static final Logger logger = LogManager.getLogger(SocketServerApp.class);
    final static int DEFAULT_PORT = 50000;

    private final int port;

    private final List<Session> sessions = new LinkedList<>();
    private Session activeSession;

    public SocketServerApp(int port) {
        this.port = port;
    }

    public static void main(String[] args) {

        int port;
        if (args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        SocketServerApp server = new SocketServerApp(port);
        server.start();
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            logger.info("Server is listening on port: " + port);

            while (true) {

                logger.info("Listening for a connection...");

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();
                Session session = new Session(socket);
                sessions.add(session);
                logger.debug("Sessions count: " + sessions.size());
                
                if (this.activeSession == null) {

                    this.activeSession = sessions.get(0);
                    logger.info("New activeSession: " + this.activeSession.getId());
                    SessionResult sessionResult = new SessionHandler(activeSession, socket).handle();
                    logger.info(String.format("Session RESULT - isOk: %s, reason: %s, msg: %s", sessionResult.isOk(),
                            sessionResult.getQuitReason().toString(), sessionResult.getMessage()));

                    sessions.remove(0);
                    this.activeSession = null;
                }
            }
        } catch (IOException e) {
            logger.error("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
