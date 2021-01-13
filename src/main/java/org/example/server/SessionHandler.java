package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This thread is responsible for communication with client.
 */
public class SessionHandler {

    private final static Logger logger = LogManager.getLogger(SessionHandler.class);
    private final static int COMMUNICATION_TIMEOUT = 30; // in seconds

    // server messages
    private final static String FIRST_MESSAGE = "HI, I AM %s";
    private final static String I_DONT_UNDERSTAND_MESSAGE = "SORRY, I DID NOT UNDERSTAND THAT";
    private final static String GOOD_BY_MESSAGE = "BYE %s, WE SPOKE FOR %d MS";

    // client messages
    private final static String CLIENT_PREFIX_OF_GREETING = "HI, I AM ";
    private final static String CLIENT_GOOD_BY_MESSAGE = ClientMessage.GOOD_BY.getMessage();

    private final Session session;
    private final Socket socket;
    private String clientName;

    SessionHandler(Session session, Socket socket) {
        this.session = session;
        this.socket = socket;
    }

    private static class ReceiveLineTask implements Callable<String> {

        private final BufferedReader in;

        public ReceiveLineTask(BufferedReader in) {
            this.in = in;
        }

        @Override
        public String call() throws IOException {
            return in.readLine();
        }
    }

    public SessionResult handle() {

        try {

            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            // Write out first message to the client
            out.println(String.format(FIRST_MESSAGE, this.session.getId()));
            out.flush();

            // Listen to the client until the client sends 'good by' message or stops
            // communication
            String line = in.readLine();

            if (line.startsWith(CLIENT_PREFIX_OF_GREETING)) {
                clientName = line.substring(CLIENT_PREFIX_OF_GREETING.length());
                logger.debug("client name: " + clientName);
                out.println(String.format("HI %s", clientName));
                out.flush();
            }

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            while (line != null) {

                logger.debug(String.format("... waiting for message for session %s ...", this.session.getId()));
                Future<String> messageFuture = executorService.submit(new ReceiveLineTask(in));

                try {
                    line = messageFuture.get(COMMUNICATION_TIMEOUT, TimeUnit.SECONDS);
                    logger.info(" ---> Received LINE: " + line);
                } catch (TimeoutException e) {
                    return quitSession(in, out, QuitReason.TIMEOUT);
                }

                if (line.equals(CLIENT_GOOD_BY_MESSAGE)) {
                    return quitSession(in, out, QuitReason.GOOD_BYE_FROM_CLIENT);
                }

                if (!ClientMessage.containsMessage(line)) {
                    out.println(I_DONT_UNDERSTAND_MESSAGE);
                    out.flush();
                }

                // TODO continue with other commands/messages
            }

            return quitSession(in, out, QuitReason.ALL_FINISHED_OK);

        } catch (Exception e) {
            return new SessionResult(false, this.session.getId(), QuitReason.EXCEPTION, e.getMessage());
        }
    }

    private SessionResult quitSession(BufferedReader in, PrintWriter out, QuitReason reason) {

        String goodByMessage = null;
        if (reason.equals(QuitReason.GOOD_BYE_FROM_CLIENT) || reason.equals(QuitReason.TIMEOUT)) {

            long timeElapsed = Duration.between(this.session.getSessionCreated(), Instant.now()).toMillis();

            goodByMessage = String.format(GOOD_BY_MESSAGE, clientName, timeElapsed);
            logger.debug("Quit session with message: " + goodByMessage);
            out.println(goodByMessage);
            out.flush();
        }

        try {
            in.close();
            this.session.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();

        logger.info(String.format("Session %s closed", this.session.getId()));
        logger.info(" ------------------------------------------ ");
        return new SessionResult(true, this.session.getId(), reason, goodByMessage);
    }
}