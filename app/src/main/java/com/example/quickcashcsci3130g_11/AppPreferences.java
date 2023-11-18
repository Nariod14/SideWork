package com.example.quickcashcsci3130g_11;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_USER_ROLE = "userRole";

    private final SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setUserRole(String role) {
        preferences.edit().putString(KEY_USER_ROLE, role).apply();
    }

    public String getUserRole() {
        return preferences.getString(KEY_USER_ROLE, "");
    }
}

