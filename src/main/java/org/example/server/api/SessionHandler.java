package org.example.server.api;

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
import org.example.server.messageHandler.ProcessMessageResult;
import org.example.server.messageHandler.strategy.GreetingStrategy;
import org.example.server.messageHandler.strategy.ProcessStrategyFactory;

/**
 * This thread is responsible for communication with client.
 */
public class SessionHandler {

    private final static Logger logger = LogManager.getLogger(SessionHandler.class);
    private final static int COMMUNICATION_TIMEOUT = 30; // in seconds

    // server messages
    private final static String FIRST_MESSAGE = "HI, I AM %s";

    private final Session session;
    private final Socket socket;
    private String clientName;

    public SessionHandler(Session session, Socket socket) {
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

            // Suppose that greeting is the first received message after server has sent the FIRST_MESSAGE
            if (line.startsWith(AcceptableClientMessage.GREETING.getMessage())) {
                clientName = new GreetingStrategy(line).process().getMessage();
                logger.debug("client name: " + clientName);
                out.println(String.format(AcceptableClientMessage.GREETING.getSuccessResponse(), clientName));
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
                    return quitSession(in, out, QuitReason.TIMEOUT, clientName);
                }

                // Process command according to its type
                ProcessMessageResult result = ProcessStrategyFactory.getStrategy(line).process();

                if (result.getQuitReason() != null) {
                    return quitSession(in, out, result.getQuitReason(), result.getMessage());
                }

                // Write out the result message
                if (result.isOk() && result.getMessage() != null) {
                    logger.debug(result.getMessage());
                    out.println(result.getMessage());
                    out.flush();
                } else if (!result.isOk()) {
                    logger.error(result.getMessage());
                }
            }
            return quitSession(in, out, QuitReason.ALL_FINISHED_OK, "");

        } catch (Exception e) {
            e.printStackTrace();
            return new SessionResult(false, this.session.getId(), QuitReason.ERROR, e.getMessage());
        }
    }

    private SessionResult quitSession(BufferedReader in, PrintWriter out, QuitReason reason, String message) {

        Boolean result;

        if (reason.equals(QuitReason.GOOD_BYE_FROM_CLIENT) || reason.equals(QuitReason.TIMEOUT)) {

            long timeElapsed = Duration.between(this.session.getSessionCreated(), Instant.now()).toMillis();
            message = String.format(message, clientName, timeElapsed);
            logger.debug("Quit session with message: " + message);
            out.println(message);
            out.flush();
            result = true;
        } else {
            logger.error(String.format("Quit session, reason: %s, message: %s ", reason, message));
            result = false;
        }

        out.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(String.format("Session %s closed", this.session.getId()));
        logger.info(" ------------------------------------------ ");
        return new SessionResult(result, this.session.getId(), reason, message);
    }
}