package com.project.shelf._core.util;

import java.text.DecimalFormat;

public class Formatter {
    public static String number(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    public static int parseNumber(String numberStr) {
        try {
            return Integer.parseInt(numberStr.replace(",", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + numberStr, e);
        }
    }

    // 만약 소수점이 포함된 숫자도 처리해야 한다면
    public static double parseDouble(String numberStr) {
        try {
            return Double.parseDouble(numberStr.replace(",", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + numberStr, e);
        }
    }
}
