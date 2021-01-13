package org.example.server;

public enum QuitReason {
    TIMEOUT,
    GOOD_BYE_FROM_CLIENT,
    EXCEPTION,
    ALL_FINISHED_OK;
}