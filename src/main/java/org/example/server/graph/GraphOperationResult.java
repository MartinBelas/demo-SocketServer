package org.example.server.graph;

public class GraphOperationResult {

    private final boolean isOk;

    public GraphOperationResult(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isOk() {
        return isOk;
    }
}
