package com.example.storefinderbackend.exception;

public class AdminAccessRequiredException extends RuntimeException{
    public AdminAccessRequiredException() {
        super("Only admin users are allowed to perform this action.");
    }
}
