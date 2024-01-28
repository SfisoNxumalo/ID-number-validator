package com.sfisomawila.luhnalogorithm;

import java.util.Arrays;

public class IDNumberValidator {

    public static boolean isValidIDNumber(String IDNumber) {
        // Remove any spaces or dashes from the ID number
        IDNumber = IDNumber.replaceAll("[-\\s]", "");

        // Check if the ID number has exactly 13 digits
        if (IDNumber.length() != 13) {
            return false;
        }

        // Check if all characters are digits
        if (!IDNumber.matches("\\d+")) {
            return false;
        }

        // Extract the date of birth from the ID number
        int year = Integer.parseInt(IDNumber.substring(0, 2));
        int month = Integer.parseInt(IDNumber.substring(2, 4));
        int day = Integer.parseInt(IDNumber.substring(4, 6));

        // Check if the date of birth is valid
        if (!isValidDate(year, month, day)) {
            return false;
        }

        // Perform the Luhn algorithm to validate the ID number
        int[] digits = new int[13];
        long firstStep = 0L;
        StringBuilder secondStep = new StringBuilder();
        long secondSLong;


        for (int i = 0; i < 13; i++) {
            digits[i] = Character.getNumericValue(IDNumber.charAt(i));
        }

        for(int i = 0; i < digits.length;i++)
        {
            if(i % 2 == 0 && i != 12)
            {
                firstStep += digits[i];
            }
            else if(i % 2 != 0)
            {
                secondStep.append(digits[i]);
            }
        }


        secondSLong = (Long.parseLong(String.valueOf(secondStep)) * 2);

        char[] stepThree = String.valueOf(secondSLong).toCharArray();

        long[] concatStepthr = new long[stepThree.length];

        for(int i = 0; i < concatStepthr.length; i++)
        {
            concatStepthr[i] = Long.parseLong(String.valueOf(stepThree[i]));
        }

        long stepFour = firstStep + Arrays.stream(concatStepthr).sum();

        int Checksum = 10 -  Integer.parseInt(String.valueOf(String.valueOf(stepFour).charAt(String.valueOf(stepFour).length()-1))) ;


        return Checksum == digits[digits.length-1];
    }

    private static boolean isValidDate(int year, int month, int day) {
        // Check if the year is valid (between 00 and 99)
        if (year < 0 || year > 99) {
            return false;
        }

        // Check if the month is valid (between 01 and 12)
        if (month < 1 || month > 12) {
            return false;
        }

        // Check if the day is valid
        if (day < 1 || day > 31) {
            return false;
        }

        // Check if the day is valid for the given month and year
        boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
        int[] daysInMonth = {31, isLeapYear ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int maxDay = daysInMonth[month - 1];

        return day <= maxDay;
    }
}
