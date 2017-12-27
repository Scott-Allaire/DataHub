package org.coder229.datahub.service;

public class GeneralException extends RuntimeException {
    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
