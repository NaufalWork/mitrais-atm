package com.mitrais.atm.exception;

public class MultipleAccountNumberException extends Exception {
    public MultipleAccountNumberException() {
        super("Multiple Account With Same AccountNumber Found");
    }
}
