package com.example.quickcashcsci3130g_11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> implements Filterable {

    private List<Job> mJobList;
    private List<Job> mFilteredJobList;

    public JobAdapter(List<Job> jobList) {
        mJobList = jobList;
        mFilteredJobList = jobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = mFilteredJobList.get(position);
        holder.titleTextView.setText(job.getTitle());
        holder.jobTypeTextView.setText(job.getJobType());
        holder.dateTextView.setText(job.getDate());
        holder.durationTextView.setText(job.getDuration() + job.getDurationType());
        holder.urgencyTextView.setText(job.getUrgencyType());
        holder.salaryTextView.setText(job.getSalary() + job.getSalaryType());
        holder.locationTextView.setText(job.getLocation());
        holder.descriptionTextView.setText(job.getDescription());
        holder.employerIdTextView.setText(job.getEmployerId());
    }

    @Override
    public int getItemCount() {
        return mFilteredJobList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<Job> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    // If the query is empty, show all jobs
                    filteredList.addAll(mJobList);
                } else {
                    // Filter jobs based on the search query
                    for (Job job : mJobList) {
                        if (job.getTitle().toLowerCase().contains(query) ||
                                job.getJobType().toLowerCase().contains(query) ||
                                job.getDate().toLowerCase().contains(query) ||
                                job.getDuration().toLowerCase().contains(query) ||
                                job.getUrgencyType().toLowerCase().contains(query) ||
                                job.getSalary().toLowerCase().contains(query) ||
                                job.getLocation().toLowerCase().contains(query) ||
                                job.getEmployerId().toLowerCase().contains(query)) {
                            filteredList.add(job);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredJobList.clear();
                mFilteredJobList.addAll((List<Job>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView jobTypeTextView;
        public TextView dateTextView;
        public TextView durationTextView;
        public TextView urgencyTextView;
        public TextView salaryTextView;
        public TextView locationTextView;
        public TextView descriptionTextView;
        public TextView employerIdTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            jobTypeTextView = itemView.findViewById(R.id.job_type_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            durationTextView = itemView.findViewById(R.id.duration_text_view);
            urgencyTextView = itemView.findViewById(R.id.urgency_text_view);
            salaryTextView = itemView.findViewById(R.id.salary_text_view);
            locationTextView = itemView.findViewById(R.id.location_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            employerIdTextView = itemView.findViewById(R.id.employer_id_text_view);
        }
    }
}
