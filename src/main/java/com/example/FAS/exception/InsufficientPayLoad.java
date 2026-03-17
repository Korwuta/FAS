package com.example.FAS.exception;

public class InsufficientPayLoad extends RuntimeException {
    public InsufficientPayLoad(String message) {
        super(message);
    }
}
