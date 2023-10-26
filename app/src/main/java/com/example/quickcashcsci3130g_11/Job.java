package com.example.quickcashcsci3130g_11;

public class Job {
    private String jobId;
    private String title;
    private String jobType;
    private String date;
    private String duration;
    private String urgency;
    private String salary;
    private String location;
    private String employerId;

    public Job() {

    }

    public Job(String jobId, String title, String jobType, String date, String duration, String urgency, String salary, String location, String employerId) {
        this.jobId = jobId;
        this.title = title;
        this.jobType = jobType;
        this.date = date;
        this.duration = duration;
        this.urgency = urgency;
        this.salary = salary;
        this.location = location;
        this.employerId = employerId;
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

    public String getUrgency() {
        return urgency;
    }

    public String getSalary() {
        return salary;
    }

    public String getLocation() {
        return location;
    }

    public String getEmployerId() {
        return employerId;
    }
}
