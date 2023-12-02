package com.example.quickcashcsci3130g_11;

/**
 * Interface for handling applicant-related interactions in a RecyclerView adapter.
 */
public interface ApplicantInterface {

    /**
     * Callback method triggered when an applicant is accepted.
     *
     * @param position The position of the accepted applicant in the list.
     */
    void onAcceptApplicantClick (int position);
}
