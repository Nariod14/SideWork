package com.example.quickcashcsci3130g_11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ViewHolder> {
    private final ApplicantInterface mApplicantInterface;
    private List<JobApplicant> mApplicantList;

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

    public void setApplicantsList(List<JobApplicant> applicantsList) {
        mApplicantList = applicantsList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView emailTextView;
        public TextView displayNameTextView;
        public Button acceptButton;

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
