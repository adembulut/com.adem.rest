package com.adem.producer.writer.exception;

public class MethodNotAvailableException extends RuntimeException{
    private static final long serialVersionUID = 8031440621759372749L;

    public MethodNotAvailableException() {
    }

    public MethodNotAvailableException(String message) {
        super(message);
    }

    public MethodNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
