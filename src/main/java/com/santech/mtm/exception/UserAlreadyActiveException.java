package com.santech.mtm.exception;

public class UserAlreadyActiveException extends Exception{
    public UserAlreadyActiveException(String message){
        super(message);
    }
}