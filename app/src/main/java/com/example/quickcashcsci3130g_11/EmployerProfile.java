package com.example.quickcashcsci3130g_11;

import java.io.Serializable;

public class EmployerProfile implements Serializable {

    private String EmployerId;
    private String EmployerName;
    private String EmployerEmail;
    private String EmployerLocation;

    public EmployerProfile() {
    }

    public EmployerProfile(String EmployerId, String EmployerName, String EmployerEmail, String EmployerLocation) {
        this.EmployerId = EmployerId;
        this.EmployerName = EmployerName;
        this.EmployerEmail = EmployerEmail;
        this.EmployerLocation = EmployerLocation;
    }

    public String getEmployerLocation() {
        return EmployerLocation;
    }

    public String getEmployerId() {
        return EmployerId;
    }

    public String getEmployerEmail() {
        return EmployerEmail;
    }

    public String getEmployerName() {
        return EmployerName;
    }
}