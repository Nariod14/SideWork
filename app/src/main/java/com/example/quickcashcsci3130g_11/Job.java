package com.example.quickcashcsci3130g_11;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Job implements Serializable {
    private String jobId;
    private String title;
    private String jobType;
    private String date;
    private String duration;
    private String durationType;
    private String urgencyType;
    private String salary;
    private String salaryType;
    private String location;
    private String description;
    private String employerId;

    private String searchableData;

    private List<String> applicants;

    /**
     * Default constructor for the Job class.
     */
    public Job()  {

    }

    /**
     * Constructs a new Job object with the provided details.
     *
     * @param jobId         The unique identifier for the job.
     * @param title         The title of the job.
     * @param jobType       The type of job.
     * @param date          The date when the job is available.
     * @param duration      The duration of the job.
     * @param durationType  The type of duration (e.g., hours, days).
     * @param urgencyType   The urgency level of the job.
     * @param salary        The salary offered for the job.
     * @param salaryType    The type of salary (e.g., per hour, per day).
     * @param location      The location where the job is available.
     * @param description   The job description.
     * @param employerId    The unique identifier of the job's employer.
     */
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
        this.applicants = new ArrayList<>();
        this.searchableData = title.toLowerCase() + " " + jobType.toLowerCase() + " " + date.toLowerCase() + " " + duration.toLowerCase() + " " + durationType.toLowerCase() + " " + urgencyType.toLowerCase() + " " + salary.toLowerCase() + " " + salaryType.toLowerCase() + " " + location.toLowerCase();


    }

    /**
     * Get the title of the job.
     *
     * @return The job title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the type of the job.
     *
     * @return The job type.
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Get the date when the job is available.
     *
     * @return The job date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the duration of the job.
     *
     * @return The job duration.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Get the type of duration (e.g., hours, days).
     *
     * @return The job duration type.
     */
    public String getDurationType() {
        return durationType;
    }

    /**
     * Get the urgency level of the job.
     *
     * @return The job urgency type.
     */
    public String getUrgencyType() {
        return urgencyType;
    }

    /**
     * Get the salary offered for the job.
     *
     * @return The job salary.
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Get the type of salary (e.g., per hour, per day).
     *
     * @return The job salary type.
     */
    public String getSalaryType() {
        return salaryType;
    }

    /**
     * Get the location where the job is available.
     *
     * @return The job location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the job description.
     *
     * @return The job description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the unique identifier of the job's employer.
     *
     * @return The employer's unique identifier.
     */
    public String getEmployerId() {
        return employerId;
    }

    /**
     * Get the searchable data for this job, used for filtering and searching.
     *
     * @return The searchable data string.
     */
    public String getSearchableData() {
        return searchableData;
    }

    public void addApplicant(String applicantUid) {
        if (applicants == null) {
            applicants = new ArrayList<>();
        }
        applicants.add(applicantUid);
    }

    // Getter and setter for 'applicants' field
    public List<String> getApplicants() {
        return applicants;
    }

// --Commented out by Inspection START (2023-11-05, 9:55 a.m.):
//    public void setApplicants(List<String> applicants) {
//        this.applicants = applicants;
//    }
// --Commented out by Inspection STOP (2023-11-05, 9:55 a.m.)

    public String getJobId() {
        return jobId;
    }
}
