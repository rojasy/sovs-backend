package com.uautso.sovs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInformationValidation {
    public static boolean isPhoneNumberValid(String phoneNumber) {
        String regexPattern = "(^(([2]{1}[5]{2})|([0]{1}))[1-9]{2}[0-9]{7}$)";
        return Pattern.compile(regexPattern)
                .matcher(phoneNumber)
                .matches();
    }

    public static boolean isEmailValid(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
