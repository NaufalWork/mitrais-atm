package com.mitrais.atm;

import com.mitrais.atm.exceptions.InsufficientBalanceException;
import com.mitrais.atm.model.Account;

import java.util.ArrayList;
import java.util.List;

public class SetupData {
    private List<Account> accountList = new ArrayList<Account>();

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public Account loginAccount(int accountNumber, String pin) {
        for (Account acc : accountList) {
            if (acc.getAccountNumber() == accountNumber && acc.getPin().equals(pin)) {
                // return correct id
                return acc;
            }
        }
        return null;
    }

    public int getAccountPosition(int accountNumber) {
        for (int i = 0; i < accountList.size(); i++) {
            Account acc = accountList.get(i);
            if (acc.getAccountNumber() == accountNumber) {
                // return correct id
                return i;
            }
        }
        return -1;
    }

    public void debitBalance(int accountNumber, float amount) throws InsufficientBalanceException {
        int accountPosition = getAccountPosition(accountNumber);
        Account acc = accountList.get(accountPosition);
        if (acc.getBalance() - amount < 0) {
            throw new InsufficientBalanceException();
        }
        acc.setBalance(acc.getBalance() - amount);
    }

    public Account getAccountByAccountNumber(int accountNumber){
        for (Account acc : accountList) {
            if (acc.getAccountNumber() == accountNumber) {
                // return correct id
                return acc;
            }
        }
        return null;
    }
}
