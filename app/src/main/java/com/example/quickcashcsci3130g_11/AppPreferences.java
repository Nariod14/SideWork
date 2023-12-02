package com.example.quickcashcsci3130g_11;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing application preferences using SharedPreferences.
 */
public class AppPreferences {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_USER_ROLE = "userRole";

    private final SharedPreferences preferences;

    /**
     * Constructor for the AppPreferences class.
     *
     * @param context The context used to access SharedPreferences.
     */
    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Sets the user role in SharedPreferences.
     *
     * @param role The user role to be set.
     */
    public void setUserRole(String role) {
        preferences.edit().putString(KEY_USER_ROLE, role).apply();
    }

    /**
     * Retrieves the user role from SharedPreferences.
     *
     * @return The user role stored in SharedPreferences. Returns an empty string if not found.
     */
    public String getUserRole() {
        return preferences.getString(KEY_USER_ROLE, "");
    }
}

