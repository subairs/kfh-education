package com.kfh.education.exception;
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}