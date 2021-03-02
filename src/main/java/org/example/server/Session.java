package org.example.server;

import java.net.Socket;
import java.util.UUID;

public class Session {

    private final UUID id;
    private final Socket socket;

    public Session (Socket socket) {
        this.id = UUID.randomUUID();
        this.socket = socket;
    }

    public UUID getId() {
        return this.id;
    }

    public Socket getSocket() {
        return this.socket;
    }
}
