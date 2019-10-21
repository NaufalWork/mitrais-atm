package com.mitrais.atm.utility;

public class ArrayUtil {
    public static int indexOf(String[] arr, String item) {
        for (int i = 0; i < arr.length; i++) {
            String currentStr = arr[i];
            if (currentStr.equals(item)) return i;
        }
        return -1;
    }
}
