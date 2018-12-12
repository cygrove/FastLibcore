package com.cygrove.libcore.updateversion.exception;

public class CallbackException extends RuntimeException {

    public CallbackException(String message) {
        super(message);
    }

    public CallbackException(Throwable cause) {
        super(cause);
    }

    public CallbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
