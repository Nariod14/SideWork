package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashcsci3130g_11.databinding.ActivityJobPostingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobPostingsActivity extends BaseActivity {
    private RecyclerView jobPosting;
    private JobAdapter jobAdapter;
    private List<Job> jobList;
    ActivityJobPostingBinding jobPostingBinding;


    /**
     * This method is called when the activity is first created. It initializes the views,
     * sets up the RecyclerView, and fetches and displays jobs posted by the current user.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobPostingBinding = ActivityJobPostingBinding.inflate(getLayoutInflater());
        setContentView(jobPostingBinding.getRoot());

        AppPreferences appPreferences = new AppPreferences(this);
        String userRole = appPreferences.getUserRole();

        String activityTitle = "";

        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            activityTitle = getString(R.string.POSTED_JOBS);
            fetchAndDisplayUserJobs();
        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            activityTitle = getString(R.string.AVAILABLE_JOBS);
            fetchAndDisplayAllJobs();
        }
        setToolbarTitle(activityTitle);
        initializeViews();
        setupRecyclerView();
    }

    /**
     * Initialize the RecyclerView to display job postings.
     */
    private void initializeViews() {
        jobPosting = findViewById(R.id.jobRecyclerView);
    }

    /**
     * Set up the RecyclerView with an adapter and a layout manager.
     */
    private void setupRecyclerView() {
        jobList = new ArrayList<>();
        jobAdapter = new JobAdapter(jobList);
        jobPosting.setLayoutManager(new LinearLayoutManager(this));
        jobPosting.setAdapter(jobAdapter);
    }

    /**
     * Fetch and display jobs posted by the current user from the Firebase Realtime Database.
     */
    private void fetchAndDisplayUserJobs() {
        DatabaseReference mDatabase;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

            // Query the Firebase Realtime Database for jobs posted by the current user
            Query query = mDatabase.orderByChild("employerId").equalTo(currentUserId);

            query.addValueEventListener(new ValueEventListener() {
                /**
                 * Notify on Data change
                 * @param dataSnapshot The current data at the location
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    jobList.clear();

                    for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                        Job job = jobSnapshot.getValue(Job.class);
                        if (job != null) {
                            jobList.add(job);
                        }
                    }

                    jobAdapter.notifyDataSetChanged();
                }

                /**
                 * Display error message when cancelled
                 *
                 * @param databaseError A description of the error that occurred
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    displayDatabaseError(databaseError.getMessage());
                }
            });
        }
    }

    private void fetchAndDisplayAllJobs() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobList.clear();

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null) {
                        jobList.add(job);
                    }
                }

                jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                displayDatabaseError(databaseError.getMessage());
            }
        });
    }


    /**
     * Display a toast message with a database error message.
     *
     * @param message The database error message to display.
     */
    private void displayDatabaseError(String message) {
        Toast.makeText(this, "Database Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
