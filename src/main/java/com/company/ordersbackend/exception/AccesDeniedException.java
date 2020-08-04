package com.company.ordersbackend.exception;

public class AccesDeniedException extends RuntimeException {
    public AccesDeniedException(String message) {
        super(message);
    }
}
