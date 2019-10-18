package com.mitrais.atm;

import com.mitrais.atm.exceptions.InsufficientBalanceException;
import com.mitrais.atm.model.Account;

import java.util.Scanner;

public class Screens {
    private SetupData setupData;
    private Account activeUser;

    public Screens(SetupData setupData) {
        this.setupData = setupData;
    }

    public void showAuthScreen() {
        Scanner scanner1 = new Scanner(System.in);

        System.out.print("Enter Account Number: ");
        int userAccountNumber = scanner1.nextInt();

        Scanner scanner2 = new Scanner(System.in);
        System.out.print("Enter Pin: ");
        String userPin = scanner2.nextLine();

        Account userAccount = setupData.loginAccount(userAccountNumber, userPin);
        if (userAccount != null) {
            activeUser = userAccount;
            showTransactionScreen();
        }

        if (userAccount == null) {
            System.out.println("Invalid Account Number/PIN");
        }
    }

    public void showTransactionScreen() {
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.print("Please choose option[3]: ");

        Scanner transactionScanner = new Scanner(System.in);
        String transactionChoice = Utils.checkBlankInputString(transactionScanner);

        if (transactionChoice.equals("") || transactionChoice.equals("3")) {
            showAuthScreen();
        } else if (transactionChoice.equals("1")) {
            showWithdrawScreen();
        } else if (transactionChoice.equals("2")) {
            showTransferScreen();
        } else {
            showTransactionScreen();
        }
    }

    public void showWithdrawScreen() {
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");

        Scanner withdrawScanner = new Scanner(System.in);

        int withdrawChoice =  withdrawScanner.nextInt();

        try {
            switch (withdrawChoice) {
                case 1:
                    setupData.debitBalance(activeUser.getAccountNumber(), 10);
                    break;
                case 2:
                    setupData.debitBalance(activeUser.getAccountNumber(), 50);
                    break;
                case 3:
                    setupData.debitBalance(activeUser.getAccountNumber(), 100);
                    System.out.println(setupData.getAccountByAccountNumber(activeUser.getAccountNumber()).getBalance());
                    break;
                case 4:
                    break;
                case -1:
                case 5:
                    showTransactionScreen();
                    break;
                default:
                    break;
            }
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showTransferScreen(){

    }

    public void showSummaryScreen(){

    }

    public void showFundTransferSummaryScreen() {

    }

}
