package com.adem.producer.writer.exception;

public class MethodNotAvailableException extends Exception{
    private static final long serialVersionUID = 8031440621759372749L;

    public MethodNotAvailableException() {
    }

    public MethodNotAvailableException(String message) {
        super(message);
    }
}
