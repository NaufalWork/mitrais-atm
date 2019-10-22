package com.mitrais.atm.model.database;

import com.mitrais.atm.exception.MultipleAccountNumberException;
import com.mitrais.atm.model.Transfer;
import com.mitrais.atm.model.account.Account;

import java.io.IOException;
import java.util.List;

public interface CSV {
    List<Account> getDataFromCSV() throws IOException, MultipleAccountNumberException;
    void updateAccountBalance(int accountNumber, float newBalance);
    void insertTransferHistory(Transfer transfer);
    void insertWithdrawalHistory(Account account, float withdrawAmount);
}
