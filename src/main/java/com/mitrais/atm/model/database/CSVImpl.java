package com.mitrais.atm.model.database;

import com.mitrais.atm.exception.MultipleAccountNumberException;
import com.mitrais.atm.model.Transfer;
import com.mitrais.atm.model.account.Account;
import com.mitrais.atm.service.AccountService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVImpl implements CSV {
    private static final String ACCOUNT_FILE_SUFFIX = "account.csv";
    private static final String ACCOUNT_FILE_SUFFIX_TEMP = "account_temp.csv";

    private String dbFilePath;

    public CSVImpl(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }

    private String csvParserForAccount(Account account) {
        return account.getName() + "," + account.getPin() + "," + account.getBalance() + "," + account.getAccountNumber();
    }

    private Account accountParser(String csvLine) {
        String[] arrAccountDetail = csvLine.split(",");
        String accountName = arrAccountDetail[0];
        String accountPin = arrAccountDetail[1];
        String accountBalance = arrAccountDetail[2];
        String accountNumber = arrAccountDetail[3];

        return new Account(
                accountName, accountPin,
                Float.parseFloat(accountBalance), Integer.parseInt(accountNumber));
    }

    /**
     * register all data from csv to data service
     */
    @Override
    public List<Account> getDataFromCSV() throws IOException, MultipleAccountNumberException {
        List<Account> accountList = new ArrayList<>();
        BufferedReader accountBuffer = new BufferedReader(new FileReader(dbFilePath + ACCOUNT_FILE_SUFFIX));
        List<String> accountNumberTemp = new ArrayList<>();
        String line;
        while ((line = accountBuffer.readLine()) != null) {// read each line of csv file

            Account account = accountParser(line);

            /* check if there is duplicate account number */
            boolean isDuplicate = accountNumberTemp.contains(String.valueOf(account.getAccountNumber()));
            if (isDuplicate) {
                throw new MultipleAccountNumberException(); // throw exception if there is any duplication
            }

            accountNumberTemp.add(String.valueOf(account.getAccountNumber())); //add account number if there is no duplication

            accountList.add(account);
        }
        accountBuffer.close();
        return accountList;
    }

    /**
     * this is kind of hack
     * create new csv with updated data
     * delete old csv rename new csv
     */
    @Override
    public void updateAccountBalance(int accountNumber, float newBalance) {
        FileWriter csvWriter;
        try {
            csvWriter = new FileWriter(dbFilePath + ACCOUNT_FILE_SUFFIX_TEMP);

            BufferedReader readCsv = new BufferedReader(new FileReader(dbFilePath + ACCOUNT_FILE_SUFFIX));
            String csvLine;
            while ((csvLine = readCsv.readLine()) != null) {
                Account account = accountParser(csvLine);
                if (account.getAccountNumber() == accountNumber) { //manipulate data
                    account.setBalance(newBalance);
                }

                String newCsvLine = csvParserForAccount(account);
                csvWriter.append(newCsvLine); //add to new line
                csvWriter.write(System.getProperty("line.separator"));
            }

            csvWriter.flush();
            csvWriter.close();
            readCsv.close();

            File oldCsv = new File(dbFilePath + ACCOUNT_FILE_SUFFIX);
            oldCsv.delete();//delete old file

            File newFile = new File(dbFilePath + ACCOUNT_FILE_SUFFIX_TEMP);
            newFile.renameTo(new File(dbFilePath + ACCOUNT_FILE_SUFFIX)); //rename new file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertTransferHistory(Transfer transfer) {

    }

    @Override
    public void insertWithdrawalHistory(Account account, float withdrawAmount) {

    }
}
