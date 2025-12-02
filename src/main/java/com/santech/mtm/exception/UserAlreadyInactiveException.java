package com.santech.mtm.exception;

public class UserAlreadyInactiveException extends Exception{
    public UserAlreadyInactiveException(String message){
        super(message);
    }
}