package com.fabriciosuarte.planets.api.application;

public class ApplicationException extends RuntimeException {

    private int code;

    ApplicationException(int code, String message) {
        super(message);

        this.code = code;
    }

    public int getCode() { return this.code; }
}
