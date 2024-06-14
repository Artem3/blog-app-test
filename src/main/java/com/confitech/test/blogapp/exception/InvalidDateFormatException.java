package com.confitech.test.blogapp.exception;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException(String message) {
        super(message);
    }
}