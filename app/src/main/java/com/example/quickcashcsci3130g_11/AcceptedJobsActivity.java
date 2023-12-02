package com.example.quickcashcsci3130g_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.quickcashcsci3130g_11.databinding.ActivityAcceptedJobsBinding;
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

/**
 * Activity displaying a list of jobs that have been accepted by the current user.
 */
public class AcceptedJobsActivity extends BaseActivity {
    ActivityAcceptedJobsBinding activityAcceptedJobsBinding;
    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private List<Job> jobList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAcceptedJobsBinding = ActivityAcceptedJobsBinding.inflate(getLayoutInflater());
        setContentView(activityAcceptedJobsBinding.getRoot());


        String activityTitle = getString(R.string.ACCEPTED_JOBS);
        setToolbarTitle(activityTitle);


        recyclerView = findViewById(R.id.jobRecyclerView);
        jobList = new ArrayList<>();
        jobAdapter = new JobAdapter(jobList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(jobAdapter);

        // Fetch and display accepted jobs
        fetchAndDisplayAcceptedJobs();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, (view, position) -> {
            // Handle item click here
            Job selectedJob = jobList.get(position);

            // Start the JobDetailsActivity and pass the selected job object
            Intent intent1 = new Intent(AcceptedJobsActivity.this, JobDetailsActivity.class);
            intent1.putExtra("job", selectedJob);
            startActivity(intent1);
        }));
    }

    /**
     * Fetches and displays the list of jobs that have been accepted by the current user.
     */
    private void fetchAndDisplayAcceptedJobs() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");

            // Query jobs where the acceptedApplicantUid is equal to the currentUserId
            Query query = jobsRef.orderByChild("acceptedApplicantUid").equalTo(currentUserId);

            query.addValueEventListener(new ValueEventListener() {
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
    }

    /**
     * Displays a Toast and logs a database error message.
     *
     * @param errorMessage The error message to be displayed.
     */
    private void displayDatabaseError(String errorMessage) {
        Log.e("AcceptedJobsActivity", "Database Error: " + errorMessage);
        Toast.makeText(this, "Database Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}