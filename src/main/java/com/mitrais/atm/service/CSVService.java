package com.mitrais.atm.service;

import com.mitrais.atm.exception.MultipleAccountNumberException;
import com.mitrais.atm.model.account.Account;
import com.mitrais.atm.model.database.CSVImpl;
import com.mitrais.atm.utility.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVService {

    private CSVImpl csv;

    private static CSVService singleInstance = null;

    private CSVService() {
        csv = new CSVImpl(FileUtil.getResourcePath() + "\\");
    }

    public static CSVService getInstance() {
        if (singleInstance == null) {
            singleInstance = new CSVService();
        }
        return singleInstance;
    }

    public List<Account> getDataFromCSV() {
        try {
            return csv.getDataFromCSV();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (MultipleAccountNumberException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void updateAccountBalance(int accountNumber, float newBalance) {
        csv.updateAccountBalance(accountNumber, newBalance);
    }

}
