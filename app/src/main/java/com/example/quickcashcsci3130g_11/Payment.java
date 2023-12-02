package com.example.quickcashcsci3130g_11;

public class Payment {
    private String jobId;
    private String jobTitle;
    private String jobType;
    private String employerId;
    private String employeeId;
    private String datePaid;
    private Double amountPaid;

    /**
     * Default constructor required for Firebase deserialization.
     */
    public Payment() {
    }
    /**
     * Constructs a Payment with the specified details.
     *
     * @param jobId       The unique identifier of the associated job.
     * @param jobTitle    The title of the associated job.
     * @param jobType     The type of the associated job.
     * @param employerId  The unique identifier of the employer associated with the job.
     * @param employeeId  The unique identifier of the employee associated with the job.
     * @param date        The date when the payment was made.
     * @param amountPaid  The amount paid for the job.
     */
    public Payment(String jobId, String jobTitle, String jobType, String employerId, String employeeId, String date, double amountPaid) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.employerId = employerId;
        this.employeeId = employeeId;
        this.datePaid = date;
        this.amountPaid = amountPaid;
    }

    /**
     * Gets the unique identifier of the employer associated with the job.
     *
     * @return The employer ID.
     */
    public String getEmployerId() {
        return employerId;
    }

    /**
     * Gets the unique identifier of the employee associated with the job.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the unique identifier of the job.
     *
     * @return The job ID.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Gets the title of the associated job.
     *
     * @return The job title.
     */
    public String getJobTitle() {
        return jobId;
    }

    /**
     * Gets the type of the associated job.
     *
     * @return The job type.
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Gets the amount paid for the job.
     *
     * @return The amount paid.
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Gets the date when the payment was made.
     *
     * @return The payment date.
     */
    public String getDatePaid() {
        return datePaid;
    }

}
