package com.mitrais.atm.model.account;

import com.mitrais.atm.exception.InsufficientBalanceException;

public interface AccountDAO {
    Account authenticateUser(int accountNumber, String pin);
    Account getAccountDetail(int accountNumber);
    void creditBalance(int accountNumber, float creditAmount);
    void debitBalance(int accountNumber, float debitAmount) throws InsufficientBalanceException;
    void registerAccount(Account account);
}
