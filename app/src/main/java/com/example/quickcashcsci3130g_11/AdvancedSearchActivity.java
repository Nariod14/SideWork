package com.example.quickcashcsci3130g_11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class AdvancedSearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FusedLocationProviderClient mFusedLocationClient;
        EditText mLocationEditText;
        EditText mSalaryEditText;
        EditText mUrgencyEditText;
        EditText mDurationEditText;
        EditText mDateEditText;
        EditText mJobTypeEditText;
        EditText mTitleEditText;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        // Initialize the UI elements
        mTitleEditText = findViewById(R.id.titleEditText);
        mJobTypeEditText = findViewById(R.id.jobTypeEditText);
        mDateEditText = findViewById(R.id.dateEditText);
        mDurationEditText = findViewById(R.id.durationEditText);
        mUrgencyEditText = findViewById(R.id.urgencyEditText);
        mSalaryEditText = findViewById(R.id.salaryEditText);
        mLocationEditText = findViewById(R.id.locationEditText);
        Button mSearchButton = findViewById(R.id.searchButton);
        Button mLocationButton = findViewById(R.id.locationButton);

        // Initialize the Firebase database reference

        // Initialize the FusedLocationProviderClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Add an OnClickListener to the search button
        mSearchButton.setOnClickListener(v -> {
            String title = mTitleEditText.getText().toString().trim();
            String jobType = mJobTypeEditText.getText().toString().trim();
            String date = mDateEditText.getText().toString().trim();
            String duration = mDurationEditText.getText().toString().trim();
            String urgency = mUrgencyEditText.getText().toString().trim();
            String salary = mSalaryEditText.getText().toString().trim();
            String location = mLocationEditText.getText().toString().trim();

            // Perform the search based on the user's input
            Intent intent = new Intent(AdvancedSearchActivity.this, SearchResultsActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("jobType", jobType);
            intent.putExtra("date", date);
            intent.putExtra("duration", duration);
            intent.putExtra("urgency", urgency);
            intent.putExtra("salary", salary);
            intent.putExtra("location", location);
            startActivity(intent);
        });

        // Initialize UI elements and set click listener for the back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Navigate back to the JobSearchActivity
            Intent intent = new Intent(AdvancedSearchActivity.this, JobSearchActivity.class);
            startActivity(intent);
        });

        // Add an OnClickListener to the location button
        mLocationButton.setOnClickListener(v -> {
            // Get the user's location
            if (ContextCompat.checkSelfPermission(AdvancedSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        String retrievedLocation = location.getLatitude() + ", " + location.getLongitude();
                        mLocationEditText.setText(retrievedLocation);
                    }
                });
            } else {
                ActivityCompat.requestPermissions(AdvancedSearchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        });
    }
}

