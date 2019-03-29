package com.fabriciosuarte.planets.api.controller;

class ApplicationError{
    private int error;
    private String message;

    ApplicationError(int code, String message) {
        this.error = code;
        this.message = message;
    }
}
