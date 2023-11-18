package com.example.quickcashcsci3130g_11;

public class User {
    public String email;
    public String displayName;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

