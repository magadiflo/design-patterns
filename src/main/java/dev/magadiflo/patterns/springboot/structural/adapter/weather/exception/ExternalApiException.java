package dev.magadiflo.patterns.springboot.structural.adapter.weather.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }
}
