package com.example.quickcashcsci3130g_11;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AllAvailableJobActivity extends AppCompatActivity {
    private JobListAdapter mAdapter;
    private List<Job> mJobList;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView mRecyclerView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs);

        initializeBackButton();

        // Initialize the RecyclerView and its adapter
        mRecyclerView = findViewById(R.id.allJobRecycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mJobList = new ArrayList<>();
        mAdapter = new JobListAdapter(mJobList);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        mDatabase.addValueEventListener(new ValueEventListener() {
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

        private void initializeBackButton() {
            ImageButton backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                Intent intent = new Intent(AllAvailableJobActivity.this, EmployeeActivity.class);
                startActivity(intent);
            });
        }
}
