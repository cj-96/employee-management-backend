package org.example.exception;

public class LeaveRequestException extends RuntimeException{

    public LeaveRequestException(String message){
        super(message);
    }
}
