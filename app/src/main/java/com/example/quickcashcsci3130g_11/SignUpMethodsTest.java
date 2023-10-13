package com.example.quickcashcsci3130g_11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpMethodsTest {
    public boolean isValidEmail(CharSequence target) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches("[A-Za-z0-9]+");
    }
}
