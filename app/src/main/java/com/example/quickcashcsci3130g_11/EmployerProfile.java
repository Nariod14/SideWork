package com.example.quickcashcsci3130g_11;

import java.io.Serializable;

public class EmployerProfile implements Serializable {

    private String EmployerId;
    private String EmployerName;
    private String EmployerEmail;
    private String EmployerLocation;
    private String EmployerKey;
    private boolean EmployerisFavourite;
    public EmployerProfile() {
    }

    public EmployerProfile(String EmployerId, String EmployerName, String EmployerEmail, String EmployerLocation,String EmployerKey, boolean EmployerisFavourite) {
        this.EmployerId = EmployerId;
        this.EmployerName = EmployerName;
        this.EmployerKey = EmployerKey;
        this.EmployerisFavourite = EmployerisFavourite;
        this.EmployerEmail = EmployerEmail;
        this.EmployerLocation = EmployerLocation;
    }

    public String getEmployerLocation() {
        return EmployerLocation;
    }

    public String getEmployerId() {
        return EmployerId;
    }

    public Boolean getEmployerisFavourite(){
        return EmployerisFavourite;
    }
    public String getEmployerKey(){
        return EmployerKey;
    }

    public String getEmployerEmail() {
        return EmployerEmail;
    }

    public String getEmployerName() {
        return EmployerName;
    }
}