package com.example.projectlivingtogether.exception;

public class OutOfQuantityException extends RuntimeException{

    public OutOfQuantityException(String message){

        super(message);
    }
}
