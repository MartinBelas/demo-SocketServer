package org.example.server;

import java.util.Arrays;

public enum QuitReason {
    TIMEOUT,
    GOOD_BYE_FROM_CLIENT,
    EXCEPTION,
    ALL_FINISHED_OK;
}