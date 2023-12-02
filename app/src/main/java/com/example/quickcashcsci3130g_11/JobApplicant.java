package com.example.quickcashcsci3130g_11;

/**
 * Represents a job applicant with basic information such as email, display name, and user ID (uid).
 */
public class JobApplicant {
    private String email;
    private String displayName;
    private String uid;

    /**
     * Default constructor required for Firebase deserialization.
     */
    public JobApplicant(){

    }

    /**
     * Constructs a JobApplicant with the specified UID, email, and display name.
     *
     * @param uid          The unique identifier of the job applicant.
     * @param email        The email address of the job applicant.
     * @param displayName  The display name of the job applicant.
     */
    public JobApplicant(String uid, String email, String displayName) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
    }

    /**
     * Gets the unique identifier of the job applicant.
     *
     * @return The UID of the job applicant.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Gets the email address of the job applicant.
     *
     * @return The email address of the job applicant.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the display name of the job applicant.
     *
     * @return The display name of the job applicant.
     */
    public String getDisplayName() {
        return displayName;
    }
}
