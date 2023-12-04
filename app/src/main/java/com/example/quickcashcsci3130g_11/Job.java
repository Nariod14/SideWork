package com.example.quickcashcsci3130g_11;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Job implements Serializable {
    private String jobId;

    private String locationString;
    private String title;
    private String jobType;
    private String date;
    private String duration;
    private String durationType;
    private String urgencyType;
    private String salary;
    private boolean isFavourite;
    private String salaryType;
    private Location location;
    private String description;
    private String employerId;

    private String searchableData;

    private List<String> applicants;
    private String acceptedApplicantUid;

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
    public Job(String jobId,Boolean isFavourite, String title, String jobType, String date, String duration, String durationType, String urgencyType, String salary, String salaryType, String location, String description, String employerId) {
        this.jobId = jobId;
        this.title = title;
        this.jobType = jobType;
        this.date = date;
        this.isFavourite = isFavourite;
        this.duration = duration;
        this.durationType = durationType;
        this.urgencyType = urgencyType;
        this.salary = salary;
        this.salaryType = salaryType;
        this.locationString = location;
        this.description = description;
        this.employerId = employerId;
        this.applicants = new ArrayList<>();
        this.acceptedApplicantUid = "";
        this.searchableData = title.toLowerCase() + " " + jobType.toLowerCase() + " " + date.toLowerCase() + " " + duration.toLowerCase() + " " + durationType.toLowerCase() + " " + urgencyType.toLowerCase() + " " + salary.toLowerCase() + " " + salaryType.toLowerCase() + " " + location;


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
    public Location getLocation() {
        return location;
    }

    public boolean getIsFavourite(){
        return isFavourite;
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

    public String getJobId() {
        return jobId;
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

    public String getAcceptedApplicantUid() {
        return acceptedApplicantUid;
    }

    public void setAcceptedApplicantUid(String applicantUid) {
        acceptedApplicantUid = applicantUid;
    }
    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    public void setLocation(Location location) {
        this.locationString = convertLocationToString(location);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        // Exclude the Location object, and use the locationString instead
        map.put("locationString", locationString);
        // Add other fields as needed
        // ...
        return map;
    }

    private String convertLocationToString(Location location) {
        if (location != null) {
            return "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
        } else {
            return null;
        }



    }


}