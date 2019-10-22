package com.mitrais.atm.service;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.account.Account;
import com.mitrais.atm.model.account.AccountDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private AccountDAOImpl accountDAO;

    private static AccountService singleInstance = null;

    private AccountService() {
        this.accountDAO = AccountDAOImpl.getInstance();
    }

    public static AccountService getInstance() {
        if (singleInstance == null) singleInstance = new AccountService();
        return singleInstance;
    }

    public void registerAccount(Account account) {
        accountDAO.registerAccount(account);
    }

    public Account authenticateUser(int accountNumber, String pin) {
        return accountDAO.authenticateUser(accountNumber, pin);
    }

    public Account getAccount(int accountNumber){
        return accountDAO.getAccountDetail(accountNumber);
    }

    /**
     * @param accountNumber
     * @param withdrawAmount
     * @return withdraw status, if true meaning it's successful otherwise failed
     */
    public boolean withdrawBalance(int accountNumber, float withdrawAmount) {
        try {
            accountDAO.debitBalance(accountNumber, withdrawAmount);
            return true;
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param sourceAccount
     * @param destinationAccount
     * @param transferAmount
     * @return transfer status, if true meaning it's successful otherwise failed
     */
    public boolean transferBalance(int sourceAccount, int destinationAccount,
                                   float transferAmount) {
        try {
            accountDAO.debitBalance(sourceAccount, transferAmount);
            accountDAO.creditBalance(destinationAccount, transferAmount);
            return true;
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }
}
