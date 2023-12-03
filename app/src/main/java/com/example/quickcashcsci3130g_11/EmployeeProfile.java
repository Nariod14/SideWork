package com.example.quickcashcsci3130g_11;

import java.io.Serializable;

public class EmployeeProfile implements Serializable {

        private String EmployeeId;
        private String EmployeeName;
        private String EmployeeEmail;
        private String EmployeeLocation;
        private String EmployeeKey;
    private boolean EmployeeisFavourite;

    public EmployeeProfile() {
    }

        public EmployeeProfile(String EmployeeId,String EmployeeKey,boolean EmployeeisFavourite, String EmployeeName, String EmployeeEmail, String EmployeeLocation) {
            this.EmployeeId = EmployeeId;
            this.EmployeeKey = EmployeeKey;
            this.EmployeeisFavourite = EmployeeisFavourite;
            this.EmployeeName = EmployeeName;
            this.EmployeeEmail = EmployeeEmail;
            this.EmployeeLocation = EmployeeLocation;
        }

        public String getEmployeeLocation() {
            return EmployeeLocation;
        }
        public String getEmployeeId() {return EmployeeId;}

    public Boolean getEmployeeisFavourite(){
        return EmployeeisFavourite;
    }
        public String getEmployeeKey(){
        return EmployeeKey;
        }
        public String getEmployeeEmail() {return EmployeeEmail;}
        public String getEmployeeName() {return EmployeeName;}

    }

