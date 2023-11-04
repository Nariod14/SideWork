package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private JobAdapter mAdapter;
    private List<Job> mJobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Initialize the UI elements
        mRecyclerView = findViewById(R.id.recyclerView);
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
        searchJobs(title, jobType, date, duration, urgency, salary, location);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Handle item click here
                Job selectedJob = mJobList.get(position);

                // Start the JobDetailsActivity and pass the selected job object
                Intent intent = new Intent(SearchResultsActivity.this, JobDetailsActivity.class);
                intent.putExtra("job", selectedJob);
                startActivity(intent);
            }
        }));

        // Add a listener to the back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void searchJobs(String title, String jobType, String date, String duration, String urgency, String salary, String location) {
        // Clear the current list of jobs
        mJobList.clear();

        // Query the Firebase database for jobs that match the search criteria
        Query jobQuery = FirebaseDatabase.getInstance().getReference("jobs")
                .orderByChild("searchableData")
                .startAt(title.toLowerCase())
                .endAt(title.toLowerCase() + "\uf8ff");
        jobQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if ((title.isEmpty() || job.getTitle().toLowerCase().contains(title.toLowerCase()))
                            && (jobType.isEmpty() || job.getJobType().toLowerCase().contains(jobType.toLowerCase()))
                            && (date.isEmpty() || job.getDate().toLowerCase().contains(date.toLowerCase()))
                            && (duration.isEmpty() || job.getDuration().toLowerCase().contains(duration.toLowerCase()))
                            && (urgency.isEmpty() || job.getUrgencyType().toLowerCase().contains(urgency.toLowerCase()))
                            && (salary.isEmpty() || job.getSalary().toLowerCase().contains(salary.toLowerCase()))
                            && (location.isEmpty() || job.getLocation().toLowerCase().contains(location.toLowerCase()))) {
                        mJobList.add(job);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if (mJobList.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "No jobs found", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

