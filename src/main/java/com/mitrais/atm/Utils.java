package com.mitrais.atm;

import java.util.Random;
import java.util.Scanner;

public class Utils {
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

    public static String get6RandomNumber(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
