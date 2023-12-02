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

import com.example.quickcashcsci3130g_11.databinding.ActivityAdvancedSearchBinding;
import com.example.quickcashcsci3130g_11.databinding.ActivityJobSearchBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

/**
 * The `AdvancedSearchActivity` allows users to perform advanced job searches by specifying search criteria
 * such as job title, job type, date, duration, urgency, salary, and location.
 */
public class AdvancedSearchActivity extends BaseActivity {
    ActivityAdvancedSearchBinding advanceJobSearchBinding;

    /**
     * Called when the activity is first created. This method initializes the UI elements, sets up
     * click listeners for search and location buttons, and handles user interactions for advanced
     * job searches.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise, it is null.
     */
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
        advanceJobSearchBinding = ActivityAdvancedSearchBinding.inflate(getLayoutInflater());
        setContentView(advanceJobSearchBinding.getRoot());

        String activityTitle = getString(R.string.ADVANCED_SEARCH);
        setToolbarTitle(activityTitle);

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

