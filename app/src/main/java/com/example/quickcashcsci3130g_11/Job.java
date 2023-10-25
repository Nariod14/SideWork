package com.example.quickcashcsci3130g_11;

public class Job {
    private String jobId;

    private String jobType;
    private String title;
    private String date;
    private String duration;
    private String urgency;
    private String salary;
    private String location;

    public Job(String jobId, String jobType, String title, String date, String duration, String urgency, String salary, String location) {
        this.jobId = jobId;
        this.jobType = jobType;
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.urgency = urgency;
        this.salary = salary;
        this.location = location;
    }

    public String getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
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
}
