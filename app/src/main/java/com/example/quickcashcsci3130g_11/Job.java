package com.example.quickcashcsci3130g_11;

public class Job {
    private String jobId;
    private String title;
    private String jobType;
    private String date;
    private String duration;
    private String durationType;
    private String urgency;
    private String urgencyType;
    private String salary;
    private String salaryType;
    private String location;
    private String description;
    private String employerId;

    private String searchableData;

    public Job() {

    }

    public Job(String jobId, String title, String jobType, String date, String duration, String durationType, String urgencyType, String salary, String salaryType, String location, String description, String employerId) {
        this.jobId = jobId;
        this.title = title;
        this.jobType = jobType;
        this.date = date;
        this.duration = duration;
        this.durationType = durationType;
        this.urgencyType = urgencyType;
        this.salary = salary;
        this.salaryType = salaryType;
        this.location = location;
        this.description = description;
        this.employerId = employerId;
        this.searchableData = title + " " + jobType + " " + date + " " + duration + " " + durationType + " " + urgency + " " + salary + " " + salaryType + " " + location;


    }
    public String getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getJobType() {
        return jobType;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getDurationType() {
        return durationType;
    }

    public String getUrgencyType() {
        return urgencyType;
    }

    public String getSalary() {
        return salary;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getSearchableData() {
        return searchableData;
    }
}
