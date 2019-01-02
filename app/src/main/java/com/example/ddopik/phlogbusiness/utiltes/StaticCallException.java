package com.example.ddopik.phlogbusiness.utiltes;

/**
 * Created by ddopik..@_@
 */
public class StaticCallException extends Exception {
    public StaticCallException() {
    }

    public StaticCallException(String detailMessage) {
        super(detailMessage);
    }

    public StaticCallException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public StaticCallException(Throwable throwable) {
        super(throwable);
    }
}
