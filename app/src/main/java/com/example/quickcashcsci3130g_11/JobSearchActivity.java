package com.example.quickcashcsci3130g_11;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity allows users to search for job listings and provides the ability to perform advanced searches.
 */
public class JobSearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private JobAdapter mAdapter;
    private List<Job> mJobList;
    private DatabaseReference mDatabase;

    /**
     * Called when the activity is first created. Initializes UI elements, RecyclerView, and sets up listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView mRecyclerView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        initializeBackButton();

        // Initialize the RecyclerView and its adapter
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mJobList = new ArrayList<>();
        mAdapter = new JobAdapter(mJobList);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        Button advancedSearchButton = findViewById(R.id.advancedSearchButton);
        advancedSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(JobSearchActivity.this, AdvancedSearchActivity.class);
            startActivity(intent);
        });

        // Add a listener to the SearchView widget
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
    }

    /**
     * Callback for when the user submits a query in the SearchView.
     *
     * @param query The query entered by the user.
     * @return true if the query is handled, false otherwise.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        // Perform the search when the user submits the query
        searchJobs(query);
        return true;
    }

    /**
     * Callback for when the user changes the query text in the SearchView.
     *
     * @param newText The updated query text.
     * @return true if the query is handled, false otherwise.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        // Perform the search as the user types
        searchJobs(newText);
        return true;
    }

    /**
     * Searches for jobs that match the given query and updates the job list accordingly.
     *
     * @param query The search query provided by the user.
     */
    private void searchJobs(String query) {
        // Clear the current list of jobs
        mJobList.clear();

        // Query the Firebase database for jobs that match the search criteria
        Query jobQuery = mDatabase.orderByChild("searchableData").startAt(query).endAt(query + "\uf8ff");
        jobQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    mJobList.add(job);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * Initializes the back button in the activity and sets a click listener to navigate back
     * to the Employee Activity when the button is clicked.
     */
    private void initializeBackButton() {
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(JobSearchActivity.this, EmployeeActivity.class);
            startActivity(intent);
        });
    }
}