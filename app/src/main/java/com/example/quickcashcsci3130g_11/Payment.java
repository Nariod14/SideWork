package com.example.quickcashcsci3130g_11;

public class Payment {
    private String jobId;
    private String jobTitle;
    private String jobType;
    private String employerId;
    private String employeeId;
    private String date;
    private Double amountPaid;

    // Default constructor required for Firebase
    public Payment() {
    }

    public Payment(String jobId, String jobTitle, String jobType, String employerId, String employeeId, String date, double amountPaid) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.employerId = employerId;
        this.employeeId = employeeId;
        this.date = date;
        this.amountPaid = amountPaid;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getJobId() {
        return jobId;
    }
    public String getJobTitle() {
        return jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getDatePaid() {
        return date;
    }

}
