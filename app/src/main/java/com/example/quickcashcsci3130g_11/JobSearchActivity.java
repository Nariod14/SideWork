package com.example.quickcashcsci3130g_11;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
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

public class JobSearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private JobAdapter mAdapter;
    private List<Job> mJobList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        // Initialize the RecyclerView and its adapter
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mJobList = new ArrayList<>();
        mAdapter = new JobAdapter(mJobList);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        // Add a listener to the SearchView widget
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Perform the search when the user submits the query
        searchJobs(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Perform the search as the user types
        searchJobs(newText);
        return true;
    }

    private void searchJobs(String query) {
        // Clear the current list of jobs
        mJobList.clear();

        // Query the Firebase database for jobs that match the search criteria
        Query jobQuery = mDatabase.orderByChild("title").startAt(query).endAt(query + "\uf8ff");
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
}