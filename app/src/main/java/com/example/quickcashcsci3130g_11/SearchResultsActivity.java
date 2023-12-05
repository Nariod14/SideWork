package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashcsci3130g_11.databinding.ActivitySearchResultsBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Activity class to display search results based on user input criteria.
 */
public class SearchResultsActivity extends BaseActivity {

    ActivitySearchResultsBinding searchResultsBinding;

    private JobAdapter mAdapter;
    private List<Job> mJobList;

    /**
     * Called when the activity is created. Initializes the user interface and retrieves search criteria
     * from the intent. Performs a job search based on the user's input criteria.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResultsBinding = ActivitySearchResultsBinding.inflate(getLayoutInflater());
        setContentView(searchResultsBinding.getRoot());

        String activityTitle = getString(R.string.SEARCH_RESULTS);
        setToolbarTitle(activityTitle);

        // Initialize the UI elements
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mJobList = new ArrayList<>();
        mAdapter = new JobAdapter(mJobList);
        mRecyclerView.setAdapter(mAdapter);

        // Get the search criteria from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String jobType = intent.getStringExtra("jobType");
        String date = intent.getStringExtra("date");
        String duration = intent.getStringExtra("duration");
        String urgency = intent.getStringExtra("urgency");
        String salary = intent.getStringExtra("salary");
        String location = intent.getStringExtra("location");

        // Perform the search based on the user's input
        searchJobs(Objects.requireNonNull(title), jobType, date, duration, urgency, salary, location);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, (view, position) -> {
            // Handle item click here
            Job selectedJob = mJobList.get(position);

            // Start the JobDetailsActivity and pass the selected job object
            Intent intent1 = new Intent(SearchResultsActivity.this, JobDetailsActivity.class);
            intent1.putExtra("job", selectedJob);
            startActivity(intent1);
        }));
    }


    /**
     * Perform a search for jobs based on user input criteria and populate the RecyclerView with results.
     *
     * @param title     The job title keyword for the search.
     * @param jobType   The job type for the search.
     * @param date      The job posting date for the search.
     * @param duration  The job duration for the search.
     * @param urgency   The job urgency type for the search.
     * @param salary    The job salary for the search.
     * @param location  The job location for the search.
     */
    private void searchJobs(String title, String jobType, String date, String duration, String urgency, String salary, String location) {
        // Clear the current list of jobs
        mJobList.clear();

        // Query the Firebase database for jobs that match the search criteria
        Query jobQuery = FirebaseDatabase.getInstance().getReference("jobs")
                .orderByChild("searchableData")
                .startAt(title.toLowerCase())
                .endAt(title.toLowerCase() + "\uf8ff");
        jobQuery.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (isTitleMatch(job) && isJobTypeMatch(job) && isDateMatch(job) && isDurationMatch(job)
                            && isUrgencyMatch(job) && isSalaryMatch(job) && isLocationMatch(job)) {
                        mJobList.add(job);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if (mJobList.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "No jobs found", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }

            /**
             * Check if the title of a job matches the search criteria.
             *
             * @param job The job to check.
             * @return True if the job title matches, false otherwise.
             */
            private boolean isTitleMatch(Job job) {
                return title.isEmpty() || job.getTitle().toLowerCase().contains(title.toLowerCase());
            }


            private boolean isJobTypeMatch(Job job) {
                return jobType.isEmpty() || job.getJobType().toLowerCase().contains(jobType.toLowerCase());
            }

            private boolean isDateMatch(Job job) {
                return date.isEmpty() || job.getDate().toLowerCase().contains(date.toLowerCase());
            }

            private boolean isDurationMatch(Job job) {
                return duration.isEmpty() || job.getDuration().toLowerCase().contains(duration.toLowerCase());
            }

            private boolean isUrgencyMatch(Job job) {
                return urgency.isEmpty() || job.getUrgencyType().toLowerCase().contains(urgency.toLowerCase());
            }

            private boolean isSalaryMatch(Job job) {
                return salary.isEmpty() || job.getSalary().toLowerCase().contains(salary.toLowerCase());
            }

            private boolean isLocationMatch(Job job) {
                if (location.isEmpty() || job.getLocationString() == null) {
                    return true; // If the search location is empty or job location is null, consider it a match
                }

                double latitude = convertStringToLocation(job.getLocationString()).getLatitude();
                double longitude = convertStringToLocation(job.getLocationString()).getLongitude();
                String locationString = "Latitude: " + latitude + ", Longitude: " + longitude;

                return locationString.toLowerCase().contains(location.toLowerCase());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
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
     * Initialize the contents of the activity's standard options menu. Inflates the menu
     * and adds items to the action bar if it is present.
     *
     * @param menu The options menu in which items are placed.
     * @return True to display the menu, false to hide it.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    /**
     * Handle action bar item clicks. This method is called when an item in the options menu is selected.
     *
     * @param item The selected menu item.
     * @return True if the item is handled successfully, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}