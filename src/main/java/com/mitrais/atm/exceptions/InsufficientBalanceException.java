package com.mitrais.atm.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Insufficient Balance");
    }
}
