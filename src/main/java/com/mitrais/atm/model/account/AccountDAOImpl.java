package com.mitrais.atm.model.account;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.service.CSVService;

import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private static AccountDAOImpl singleInstance = null;
    private List<Account> accountList;
    private CSVService csvService; //using csv as database

    private AccountDAOImpl(){
        csvService = CSVService.getInstance();
        accountList = csvService.getDataFromCSV();
    }

    public static AccountDAOImpl getInstance() {
        if (singleInstance == null) singleInstance = new AccountDAOImpl();
        return singleInstance;
    }

    @Override
    public Account authenticateUser(int accountNumber, String pin) {
        for (Account acc : accountList) {
            if (acc.getAccountNumber() == accountNumber && acc.getPin().equals(pin)) {
                // return correct id
                return acc;
            }
        }
        return null;
    }

    @Override
    public Account getAccountDetail(int accountNumber) {
        for (Account acc : accountList) {
            if (acc.getAccountNumber() == accountNumber) {
                // return correct id
                return acc;
            }
        }
        return null;
    }

    @Override
    public void creditBalance(int accountNumber, float creditAmount) {
        Account acc = getAccountDetail(accountNumber);
        float newBalance = acc.getBalance() + creditAmount;
        acc.setBalance(newBalance);
        csvService.updateAccountBalance(accountNumber, newBalance);
    }

    @Override
    public void debitBalance(int accountNumber, float debitAmount) throws InsufficientBalanceException {
        Account acc = getAccountDetail(accountNumber);
        float finalBalance = acc.getBalance() - debitAmount;
        if (finalBalance < 0) {
            throw new InsufficientBalanceException();
        }
        acc.setBalance(finalBalance);
        csvService.updateAccountBalance(accountNumber, finalBalance);
    }

    @Override
    public void registerAccount(Account account) {
        accountList.add(account);
    }
}
