package com.example.quickcashcsci3130g_11;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for populating a RecyclerView with job listings and providing filtering functionality.
 * @noinspection ALL
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> implements Filterable {

    private List<Job> mJobList;
    private List<Job> mFilteredJobList;

    /**
     * Constructs a new JobAdapter with the provided job list.
     *
     * @param jobList The list of job listings to be displayed.
     */
    public JobAdapter(List<Job> jobList) {
        mJobList = jobList;
        mFilteredJobList = jobList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
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
        holder.titleTextView.setText(job.getTitle());
        holder.jobTypeTextView.setText(job.getJobType());
        holder.dateTextView.setText(job.getDate());
        holder.durationTextView.setText(job.getDuration() + " " + job.getDurationType());
        holder.urgencyTextView.setText(job.getUrgencyType());
        holder.salaryTextView.setText("$" + job.getSalary() + " " + job.getSalaryType());
        if (job.getLocationString() != null) {
            double latitude = convertStringToLocation(job.getLocationString()).getLatitude();
            double longitude = convertStringToLocation(job.getLocationString()).getLongitude();
            holder.locationTextView.setText("Latitude: " + latitude + ", Longitude: " + longitude);
        }

        String targetUserId = job.getEmployerId();

        // Fetch user by UID and set the display name
        fetchUserByUid(targetUserId, new UserCallback() {
            @Override
            public void onUserRetrieved(User user) {
                if (user != null) {
                    // User data found
                    String displayName = user.getDisplayName();
                    holder.employerIdTextView.setText(displayName);
                } else {
                    // Handle the case where user is null
                    holder.employerIdTextView.setText("Unknown User");
                }
            }

            @Override
            public void onFailure(String error) {
                // Handle the failure case
                holder.employerIdTextView.setText("Error fetching user");
            }
        });

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        String displayName = "";



        holder.descriptionTextView.setText(job.getDescription());
        holder.employerIdTextView.setText(job.getEmployerId());
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

    /**
     * Returns a filter that can be used to filter the job listings based on a search query.
     *
     * @return A filter for job listings.
     */
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
                        String locationString = "";
                        Location jobLocation = convertStringToLocation(job.getLocationString());

                        if (jobLocation != null) {
                            double latitude = jobLocation.getLatitude();
                            double longitude = jobLocation.getLongitude();
                            locationString = "Latitude: " + latitude + ", Longitude: " + longitude;
                        }

                        if (job.getTitle().toLowerCase().contains(query) ||
                                job.getJobType().toLowerCase().contains(query) ||
                                job.getDate().toLowerCase().contains(query) ||
                                job.getDuration().toLowerCase().contains(query) ||
                                job.getUrgencyType().toLowerCase().contains(query) ||
                                job.getSalary().toLowerCase().contains(query) ||
                                locationString.toLowerCase().contains(query) ||
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

    private Location convertStringToLocation(String locationString) {
        Location location = new Location("provider"); // You can provide any provider name

        // Sample locationString: "Latitude: 37.7749, Longitude: -122.4194"
        String[] parts = locationString.split(", ");
        if (parts.length == 2) {
            // Extract latitude and longitude values
            String latitudeString = parts[0].substring(parts[0].indexOf(":") + 2);
            String longitudeString = parts[1].substring(parts[1].indexOf(":") + 2);

            try {
                // Parse latitude and longitude as doubles
                double latitude = Double.parseDouble(latitudeString);
                double longitude = Double.parseDouble(longitudeString);

                // Set the values to the Location object
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                return location;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * ViewHolder class for holding the views of each job item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView jobTypeTextView;
        private TextView dateTextView;
        private TextView durationTextView;
        private TextView urgencyTextView;
        private TextView salaryTextView;
        private TextView locationTextView;
        private TextView descriptionTextView;
        private TextView employerIdTextView;

        /**
         * Creates a new ViewHolder and initializes its views.
         *
         * @param itemView The item view for the ViewHolder.
         */
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

    public void fetchUserByUid(String targetUserId, UserCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.child(targetUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User targetUser = dataSnapshot.getValue(User.class);

                    if (targetUser != null) {
                        // User data found
                        callback.onUserRetrieved(targetUser);
                    } else {
                        // Handle the case where user is null
                        callback.onFailure("User is null");
                    }
                } else {
                    // Handle the case where user data doesn't exist
                    callback.onFailure("User data not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                callback.onFailure(databaseError.getMessage());
            }
        });
    }


    public interface UserCallback {
        void onUserRetrieved(User user);
        void onFailure(String error);
    }
}