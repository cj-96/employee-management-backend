package org.example.exception;

public class LeaveBalanceException extends RuntimeException{

    public LeaveBalanceException(String message){
        super(message);
    }
}
