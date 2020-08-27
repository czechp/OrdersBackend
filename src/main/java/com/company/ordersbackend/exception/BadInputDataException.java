package com.company.ordersbackend.exception;

public class BadInputDataException extends RuntimeException {
    public BadInputDataException(String message) {
        super(message);
    }
}
