package com.mitrais.atm.exception;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Insufficient Balance");
    }
}
