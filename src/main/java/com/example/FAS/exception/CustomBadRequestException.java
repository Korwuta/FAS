package com.example.FAS.exception;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String s) {
        super(s);
    }
}
