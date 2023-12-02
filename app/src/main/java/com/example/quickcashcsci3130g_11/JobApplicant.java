package com.example.quickcashcsci3130g_11;

public class JobApplicant {
    private String email;
    private String displayName;
    private String uid;

    public JobApplicant(){

    }

    public JobApplicant(String uid, String email, String displayName) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}
