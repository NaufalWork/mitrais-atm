package com.mitrais.atm;

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
}
