package com.mitrais.atm.utility;

import java.util.Random;
import java.util.Scanner;

public class DataUtil {
    public static String checkBlankInputString(Scanner sc) {
        String value = sc.nextLine().trim();
        return value.length() == 0 ? "" : value;
    }

    public static int checkBlankInputInt(Scanner sc) {
        String value = sc.nextLine().trim();
        return value.length() == 0 ? -1 : sc.nextInt();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String get6RandomNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static boolean has6digit(String input) {
        return input.length() == 6;
    }

    public static boolean isMaxWithdrawAmount(float amount) {
        return amount > 1000;
    }

    public static boolean isMultipleOf10(float amount) {
        if (amount < 10) {
            return false;
        }
        return amount % 10 == 0;
    }

    public static boolean isMinimumTransferAmount(float amout){
        return amout >= 1;
    }
}
