package com.palazzisoft.gerbio.integrator.exception;

public class GerbioException extends Exception {

    public GerbioException(Exception e) {
        super(e);
    }

    public GerbioException(String message) {
        super(message);
    }
}
