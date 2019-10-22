package com.mitrais.atm;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.account.Account;
import com.mitrais.atm.model.Transfer;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.utility.ErrorMessage;
import com.mitrais.atm.utility.DataUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

class Screens {
    private AccountService accountService;
    private Account activeAccount;

    Screens() {
        this.accountService = AccountService.getInstance();
    }

    void showAuthScreen() {
        Scanner scanner1 = new Scanner(System.in);

        System.out.println("\n----------\n");
        System.out.println("Welcome To National Bank\n");
        System.out.print("Enter Account Number: ");
        String userAccountNumber = DataUtil.checkBlankInputString(scanner1);

        if (userAccountNumber.equals("") || !DataUtil.has6digit(userAccountNumber)) {
            System.out.println(ErrorMessage.ACCOUNT_6_DIGIT);
            showAuthScreen();
        }

        if (!DataUtil.isNumeric(userAccountNumber)) {
            System.out.println(ErrorMessage.ACCOUNT_ONLY_NUMBER);
            showAuthScreen();
        }

        Scanner scanner2 = new Scanner(System.in);
        System.out.print("Enter Pin: ");
        String userPin = scanner2.nextLine();

        if (!DataUtil.isNumeric(userPin)) {
            System.out.println(ErrorMessage.PIN_ONLY_NUMBER);
            showAuthScreen();
        }

        if (userPin.equals("") || !DataUtil.has6digit(userPin)) {
            System.out.println(ErrorMessage.PIN_6_DIGIT);
            showAuthScreen();
        }

        Account userAccount = accountService.authenticateUser(Integer.parseInt(userAccountNumber), userPin);
        if (userAccount != null) {
            activeAccount = userAccount;
            showTransactionScreen();
        }

        if (userAccount == null) {
            System.out.println(ErrorMessage.INVALID_ACCOUNT_NUMBER_PIN);
            showAuthScreen();
        }
    }

    private void showTransactionScreen() {
        System.out.println("\n----------\n");
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.print("Please choose option[3]: ");

        Scanner transactionScanner = new Scanner(System.in);
        String transactionChoice = DataUtil.checkBlankInputString(transactionScanner);

        switch (transactionChoice) {
            case "":
            case "3":
                showAuthScreen();
                break;
            case "1":
                showWithdrawScreen();
                break;
            case "2":
                showTransferScreen();
                break;
            default:
                showTransactionScreen();
                break;
        }
    }

    private void withdrawBalance(float amount){
        if (accountService.withdrawBalance(
                activeAccount.getAccountNumber(), amount)) {
            showSummaryScreen(amount, accountService.getAccount(activeAccount.getAccountNumber()).getBalance());
        } else {
            showTransactionScreen();
        }
    }

    private void showWithdrawScreen() {
        System.out.println("\n----------\n");
        System.out.println("Withdrawal\n");
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");

        Scanner withdrawScanner = new Scanner(System.in);

        String withdrawChoice = DataUtil.checkBlankInputString(withdrawScanner);

        switch (withdrawChoice) {
            case "1":
                withdrawBalance(10);
                break;
            case "2":
                withdrawBalance(50);
                break;
            case "3":
                withdrawBalance(100);
                break;
            case "4":
                showOtherWithdrawAmount();
                break;
            case "":
            case "5":
                showTransactionScreen();
                break;
        }
    }

    private void showOtherWithdrawAmount() {
        System.out.println("\n----------\n");
        System.out.print("Other Withdraw\n" +
                "Enter amount to withdraw :");
        Scanner otherWithdrawAmount = new Scanner(System.in);
        String sWithdrawAmount = DataUtil.checkBlankInputString(otherWithdrawAmount);

        //check if the input is numeric
        if (!DataUtil.isNumeric(sWithdrawAmount)) {
            System.out.println(ErrorMessage.INVALID_AMOUNT);
            showOtherWithdrawAmount();
        }

        if (DataUtil.isMaxWithdrawAmount(Float.parseFloat(sWithdrawAmount))) {
            System.out.println(ErrorMessage.MAXIMUM_WITHDRAW);
            showOtherWithdrawAmount();
        }

        float withdrawAmount = sWithdrawAmount.equals("") ? 0 : Float.parseFloat(sWithdrawAmount);

        //checks if the amount is greater than maximum amount or not multiple of 10
        if (DataUtil.isMaxWithdrawAmount(withdrawAmount) || !DataUtil.isMultipleOf10(withdrawAmount)) {
            System.out.println(ErrorMessage.INVALID_AMOUNT);
            showOtherWithdrawAmount();
        }

        boolean withdrawProcess = accountService.withdrawBalance(activeAccount.getAccountNumber(), withdrawAmount);
        if (withdrawProcess) {
            showSummaryScreen(withdrawAmount, accountService.getAccount(activeAccount.getAccountNumber()).getBalance());
        }
    }

    private void showTransferScreen() {
        System.out.println("\n----------\n");
        System.out.print("Please enter destination account and " +
                "press enter to continue or \n" +
                "press enter to go back to Transaction: ");
        Scanner transferScanner = new Scanner(System.in);
        String destinationAccount = DataUtil.checkBlankInputString(transferScanner);

        if (destinationAccount.equals("")) {
            showTransactionScreen();
        }

        if (!DataUtil.isNumeric(destinationAccount)) {
            System.out.println(ErrorMessage.INVALID_ACCOUNT);
            showTransferScreen();
        }

        Account acc = accountService.getAccount(Integer.parseInt(destinationAccount));

        //checks if the destination is correct
        if (acc == null || !DataUtil.isNumeric(destinationAccount)) {
            System.out.println(ErrorMessage.INVALID_ACCOUNT);
            showTransferScreen();
        }


        System.out.println("\n----------\n");
        System.out.print("Reference Number: (This is an autogenerated random 6 digits number)\n" +
                "press enter to continue or ");
        Scanner referenceNumberScanner = new Scanner(System.in);
        String referenceNumber = DataUtil.checkBlankInputString(referenceNumberScanner);


        //checks reference number
        if (referenceNumber.equals("")) {
            referenceNumber = DataUtil.get6RandomNumber();
        }

        showTransferAmountScreen(destinationAccount, referenceNumber);
    }

    private void showTransferAmountScreen(String destinationAccount, String referenceNumber) {
        System.out.println("\n----------\n");
        System.out.print("Please enter transfer amount and press enter to continue or \n" +
                "press enter to go back to Transaction: ");
        Scanner transferAmountScanner = new Scanner(System.in);
        String tranferAmount = DataUtil.checkBlankInputString(transferAmountScanner);

        //blank input
        if (tranferAmount.equals("")) {
            showTransactionScreen();
        }

        //checks if the transfer amount is valid
        if (!DataUtil.isNumeric(tranferAmount)) {
            System.out.println(ErrorMessage.INVALID_AMOUNT);
            showTransferAmountScreen(destinationAccount, referenceNumber);
        } else if (!DataUtil.isMinimumTransferAmount(Float.parseFloat(tranferAmount))) {
            System.out.println(ErrorMessage.MINIMUM_TRANSFER_AMOUNT);
            showTransferAmountScreen(destinationAccount, referenceNumber);
        } else if (DataUtil.isMaxWithdrawAmount(Float.parseFloat(tranferAmount))) {
            System.out.println(ErrorMessage.MAXIMUM_WITHDRAW);
            showTransferAmountScreen(destinationAccount, referenceNumber);
        } else {
            //valid amount do transfer
            float fTransferAmount = Float.parseFloat(tranferAmount);
            Transfer transfer = new Transfer(Integer.parseInt(destinationAccount), fTransferAmount, referenceNumber, activeAccount.getAccountNumber());
            showConfirmationTransferScreen(transfer);
        }
    }

    private void showSummaryScreen(float withdrawAmount, float balance) {
        System.out.println("\n----------\n");
        System.out.println("Summary");
        System.out.println("Date : " + new Timestamp(new Date().getTime()));
        System.out.println("Withdraw : " + withdrawAmount);
        System.out.println("Balance : " + balance);
        System.out.println();
        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.print("Choose option[2] : ");

        Scanner summaryScanner = new Scanner(System.in);
        String choice = DataUtil.checkBlankInputString(summaryScanner);

        if (choice.equals("1")) {
            showTransactionScreen();
        } else if (choice.equals("") || choice.equals("2")) {
            showAuthScreen();
        }
    }

    private void showConfirmationTransferScreen(Transfer transfer) {
        System.out.println("\n----------\n");
        System.out.print("Transfer Confirmation\n" +
                "Destination Account : " + transfer.getDestinationAccount() + "\n" +
                "Transfer Amount     : " + transfer.getTransferAmount() + "\n" +
                "Reference Number    : " + transfer.getReferenceNumber() + "\n\n" +
                "1. Confirm Trx\n" +
                "2. Cancel Trx\n\n" +
                "Choose option[2]: ");

        Scanner transferConfirmationScanner = new Scanner(System.in);
        String confirmationChoice = DataUtil.checkBlankInputString(transferConfirmationScanner);

        switch (confirmationChoice) {
            case "1":
                doTransfer(transfer);
                break;
            case "":
            case "2":
                showAuthScreen();
                break;
        }
    }

    private void doTransfer(Transfer transfer) {
        boolean transferProcess = accountService.transferBalance(
                transfer.getSourceAccout(), transfer.getDestinationAccount(), transfer.getTransferAmount());
        if (transferProcess) {
            // show transfer summary screen
            showFundTransferSummaryScreen(transfer);
        } else {
            // insufficient balance
            System.out.println("Insufficient balance $" + transfer.getTransferAmount());
            showTransferAmountScreen(String.valueOf(transfer.getDestinationAccount()), transfer.getReferenceNumber());
        }
    }

    private void showFundTransferSummaryScreen(Transfer transfer) {
        float accountBalance = accountService.getAccount(activeAccount.getAccountNumber()).getBalance();
        System.out.print("Fund Transfer Summary\n" +
                "Destination Account : " + transfer.getDestinationAccount() + "\n" +
                "Transfer Amount     : " + transfer.getTransferAmount() + "\n" +
                "Reference Number    : " + transfer.getReferenceNumber() + "\n" +
                "Balance             : $" + accountBalance + "\n" +
                "\n" +
                "1. Transaction\n" +
                "2. Exit\n" +
                "Choose Option[2]: ");
        Scanner optionCoiceScanner = new Scanner(System.in);
        String optionChoice = DataUtil.checkBlankInputString(optionCoiceScanner);

        if (optionChoice.equals("1")) {
            showTransactionScreen();
        }

        //exit the app
        if (optionChoice.equals("") || optionChoice.equals("2")) {
            showAuthScreen();
        }
    }
}
