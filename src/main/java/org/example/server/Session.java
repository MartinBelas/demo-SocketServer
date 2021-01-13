package org.example.server;

import java.net.Socket;
import java.time.Instant;
import java.util.UUID;

public class Session {

    private final UUID id;
    private final Socket socket;

    private final Instant sessionCreated;

    public Session(Socket socket) {
        this.id = UUID.randomUUID();
        this.socket = socket;
        this.sessionCreated = Instant.now();
    }

    public UUID getId() {
        return this.id;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public Instant getSessionCreated() {
        return sessionCreated;
    }

}
