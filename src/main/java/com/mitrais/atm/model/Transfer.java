package com.mitrais.atm.model;

public class Transfer {
    private int destinationAccount;
    private float transferAmount;
    private String referenceNumber;
    private int sourceAccout;

    public Transfer(){

    }

    public Transfer(int destinationAccount, float transferAmount, String referenceNumber, int sourceAccout) {
        this.destinationAccount = destinationAccount;
        this.transferAmount = transferAmount;
        this.referenceNumber = referenceNumber;
        this.sourceAccout = sourceAccout;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public float getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(float transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getSourceAccout() {
        return sourceAccout;
    }

    public void setSourceAccout(int sourceAccout) {
        this.sourceAccout = sourceAccout;
    }
}
