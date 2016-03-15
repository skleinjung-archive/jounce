package com.thrashplay.jounce;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class JounceException extends RuntimeException {
    public JounceException() {
    }

    public JounceException(String detailMessage) {
        super(detailMessage);
    }

    public JounceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public JounceException(Throwable throwable) {
        super(throwable);
    }
}
