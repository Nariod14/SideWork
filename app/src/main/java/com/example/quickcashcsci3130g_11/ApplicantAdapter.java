package com.example.quickcashcsci3130g_11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/**
 * Adapter class for the RecyclerView displaying a list of job applicants.
 */
public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ViewHolder> {
    private final ApplicantInterface mApplicantInterface;
    private List<JobApplicant> mApplicantList;

    /**
     * Constructor for the ApplicantAdapter.
     *
     * @param applicantList      The list of job applicants to be displayed.
     * @param applicantInterface The interface for handling applicant-related interactions.
     */
    public ApplicantAdapter(List<JobApplicant> applicantList, ApplicantInterface applicantInterface) {
        mApplicantList = applicantList;
        this.mApplicantInterface = applicantInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applicant, parent, false);
        return new ViewHolder(view, mApplicantInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to each item in the RecyclerView
        JobApplicant applicant = mApplicantList.get(position);
        holder.emailTextView.setText(applicant.getEmail());
        holder.displayNameTextView.setText(applicant.getDisplayName());
    }

    @Override
    public int getItemCount() {
        return mApplicantList.size();
    }

    /**
     * Sets the list of job applicants for the adapter and notifies the adapter of the data set change.
     *
     * @param applicantsList The updated list of job applicants.
     */

    public void setApplicantsList(List<JobApplicant> applicantsList) {
        mApplicantList = applicantsList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class representing each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView emailTextView;
        public TextView displayNameTextView;
        public Button acceptButton;

        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView             The view representing the item in the RecyclerView.
         * @param mApplicantInterface The interface for handling applicant-related interactions.
         */
        public ViewHolder(@NonNull View itemView, ApplicantInterface mApplicantInterface) {
            super(itemView);

            emailTextView = itemView.findViewById(R.id.applicantEmailTextView);
            displayNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            acceptButton = itemView.findViewById(R.id.acceptApplicantButton);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicantAdapter.this.mApplicantInterface !=null){
                        ApplicantAdapter.this.mApplicantInterface.onAcceptApplicantClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
