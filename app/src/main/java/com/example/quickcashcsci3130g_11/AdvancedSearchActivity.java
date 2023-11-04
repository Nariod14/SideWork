package com.example.quickcashcsci3130g_11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class AdvancedSearchActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mJobTypeEditText;
    private EditText mDateEditText;
    private EditText mDurationEditText;
    private EditText mUrgencyEditText;
    private EditText mSalaryEditText;
    private EditText mLocationEditText;
    private Button mSearchButton;
    private Button mLocationButton;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        mSearchButton = findViewById(R.id.searchButton);
        mLocationButton = findViewById(R.id.locationButton);

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

        // Add an OnClickListener to the location button
        mLocationButton.setOnClickListener(v -> {
            // Get the user's location
            if (ActivityCompat.checkSelfPermission(AdvancedSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        mLocationEditText.setText(location.getLatitude() + ", " + location.getLongitude());
                    }
                });
            } else {
                ActivityCompat.requestPermissions(AdvancedSearchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        });
    }
}

