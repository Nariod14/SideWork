package com.example.quickcashcsci3130g_11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Adapter class for populating a RecyclerView with job listings and providing filtering functionality.
 * @noinspection ALL
 */
public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder>{

    private RefreshList refreshList;
    private LayoutInflater mInflater;
    private List<Job> mJobList;
    private List<Job> mFilteredJobList;
    private DatabaseReference mDatabase;

    /**
     * Constructs a new JobAdapter with the provided job list.
     *
     * @param jobList The list of job listings to be displayed.
     */
    public JobListAdapter(List<Job> jobList) {
        mJobList = jobList;
        mFilteredJobList = jobList;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Creates and returns a new ViewHolder instance for each item in the RecyclerView.
     *
     * @param parent   The parent view group.
     * @param viewType The type of view.
     * @return A new ViewHolder for an item view.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater =LayoutInflater.from(parent.getContext());
        return new ViewHolder(mInflater.inflate(R.layout.activity_job_item_add, parent, false));
    }

    /**
     * Binds data to the views of a ViewHolder for a specific position in the RecyclerView.
     *
     * @param holder   The ViewHolder to bind data to.
     * @param position The position of the item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = mFilteredJobList.get(position);
        holder.jobNameTextView.setText(job.getTitle());
        holder.salaryTextView.setText("$" + job.getSalary() + " " + job.getSalaryType());
        holder.locationTextView.setText((CharSequence) job.getLocation());
        boolean isFavourite = job.getIsFavourite();
        if (isFavourite) {
            holder.isFavourite.setImageResource(R.drawable.ic_bookmark_active); // Set your favorite icon resource
        } else {
            holder.isFavourite.setImageResource(R.drawable.ic_bookmark_inactive); // Set your not favorite icon resource
        }

        holder.isFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite){
                    mDatabase.child("favourite").setValue(job.getEmployerId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mDatabase.child("jobs").child(job.getEmployerId()).child("isFavourite").setValue(false)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(mInflater.getContext(), "Favourite Removed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
                }else {
                    DatabaseReference favouriteReference = FirebaseDatabase.getInstance().getReference().child("Favourite");
                    Map<String, Object> favourite = new HashMap<>();
                    favourite.put("favouriteKey", job.getEmployerId());

                    favouriteReference.setValue(favourite).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mDatabase.child("jobs").child(job.getEmployerId()).child("isFavourite").setValue(true)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            if (refreshList != null){
                                                refreshList.RefreshData();
                                            }
                                            Toast.makeText(mInflater.getContext(), "Favourite Added", Toast.LENGTH_SHORT).show();
                                   }
                           });
                        }
                    });
                }
            }
        });

    }

    public void OnRefreshListListener(RefreshList refreshList){
        this.refreshList = refreshList;
    }
    public interface RefreshList{
        void RefreshData();
    }

    private boolean convertStringToBoolean(String stringValue) {
        return stringValue != null && stringValue.equalsIgnoreCase("true");
    }
    /**
     * Returns the total number of items in the RecyclerView.
     *
     * @return The number of items in the RecyclerView.
     */
    @Override
    public int getItemCount() {
        return mFilteredJobList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView jobNameTextView;
        private TextView salaryTextView;
        private TextView locationTextView;

        private ImageView isFavourite;

        /**
         * Creates a new ViewHolder and initializes its views.
         *
         * @param itemView The item view for the ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobNameTextView = itemView.findViewById(R.id.jobNameTextView);
            salaryTextView = itemView.findViewById(R.id.Salary_details);
            locationTextView = itemView.findViewById(R.id.jobLocation);
            isFavourite = itemView.findViewById(R.id.isFavourite);

        }
    }
}