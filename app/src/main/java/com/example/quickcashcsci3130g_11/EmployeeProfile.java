package com.example.quickcashcsci3130g_11;

import java.io.Serializable;

public class EmployeeProfile implements Serializable {

        private String EmployeeId;
        private String EmployeeName;
        private String EmployeeEmail;
        private String EmployeeLocation;

    public EmployeeProfile() {
    }

        public EmployeeProfile(String EmployeeId, String EmployeeName, String EmployeeEmail, String EmployeeLocation) {
            this.EmployeeId = EmployeeId;
            this.EmployeeName = EmployeeName;
            this.EmployeeEmail = EmployeeEmail;
            this.EmployeeLocation = EmployeeLocation;
        }

        public String getEmployeeLocation() {
            return EmployeeLocation;
        }
        public String getEmployeeId() {return EmployeeId;}
        public String getEmployeeEmail() {return EmployeeEmail;}
        public String getEmployeeName() {return EmployeeName;}

    }

