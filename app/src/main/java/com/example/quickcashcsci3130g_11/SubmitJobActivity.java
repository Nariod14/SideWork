package com.example.quickcashcsci3130g_11;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quickcashcsci3130g_11.databinding.ActivitySubmitJobBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class SubmitJobActivity extends BaseActivity {

    private EditText titleEditText;
    private EditText durationEditText;
    private EditText salaryEditText;
    private EditText locationEditText;
    private EditText descriptionEditText;
    private Spinner jobTypeSpinner;
    private Spinner durationTypeSpinner;
    private Spinner urgencyTypeSpinner;
    private Spinner salaryTypeSpinner;
    private Button pickDateButton;
    private Button locationButton;
    private Button submitButton;
    private TextView setDateTextView;
    private TextView mEmailTextView;
    private DatabaseReference databaseReference;
    private FusedLocationProviderClient fusedLocationClient;

    ActivitySubmitJobBinding submitJobBinding;

    /**
     * This method is called when the activity is first created. It initializes various views,
     * sets up event listeners for buttons, and prepares the spinners and user profile information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitJobBinding = ActivitySubmitJobBinding.inflate(getLayoutInflater());
        setContentView(submitJobBinding.getRoot());

        String activityTitle = getString(R.string.SUBMIT_JOB);
        setToolbarTitle(activityTitle);

        initializeViews();
        retrieveLocation();
        setSpinners();

        submitButton.setOnClickListener(this::onJobSubmitButtonClicked);

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveLocation();
            }
        });

        setEditTextFocusListeners();
    }

    /**
     * Initialize all the views used in this activity.
     */
    private void initializeViews() {
        mEmailTextView = findViewById(R.id.emailTextView);
        titleEditText = findViewById(R.id.titleEditText);
        setDateTextView = findViewById(R.id.setDateTextView);
        durationEditText = findViewById(R.id.durationEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        locationEditText = findViewById(R.id.locationEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        jobTypeSpinner = findViewById(R.id.jobTypeSpinner);
        durationTypeSpinner = findViewById(R.id.durationTypeSpinner);
        urgencyTypeSpinner = findViewById(R.id.urgencyTypeSpinner);
        salaryTypeSpinner = findViewById(R.id.salaryTypeSpinner);
        pickDateButton = findViewById(R.id.datePickerButton);
        locationButton = findViewById(R.id.getLocationButton);
        submitButton = findViewById(R.id.submitButton);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("jobs");
    }

    /**
     * Set up the spinners with their respective options.
     */
    private void setSpinners() {
        String[] jobTypeOptions = {"Pet Services", "Family Services", "Construction & Renovation", "Home Maintenance and Repair", "Outdoor & Landscaping", "Automotive Services", "Food and Culinary Services", "Personal Services", "Transportation Services", "Event & Entertainment Services", "Technology and IT Services"};
        String[] durationTypeOptions = {"Hours", "Days", "Weeks", "Months"};
        String[] urgencyTypeOptions = {"Low", "Medium", "High"};
        String[] salaryTypeOptions = {"per hour", "per day", "per job"};

        ArrayAdapter<String> jobTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jobTypeOptions);
        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeSpinner.setAdapter(jobTypeAdapter);

        ArrayAdapter<String> durationTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durationTypeOptions);
        durationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationTypeSpinner.setAdapter(durationTypeAdapter);

        ArrayAdapter<String> urgencyTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, urgencyTypeOptions);
        urgencyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        urgencyTypeSpinner.setAdapter(urgencyTypeAdapter);

        ArrayAdapter<String> salaryTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salaryTypeOptions);
        salaryTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salaryTypeSpinner.setAdapter(salaryTypeAdapter);
    }


    /**
     * Show a date picker dialog when the "Pick Date" button is clicked.
     * Sets the chosen date into the dateTextView.
     */
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateText = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
                setDateTextView.setText(dateText);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    /**
     * Retrieve the user's current location and display it in the location edit text field.
     */
    private void retrieveLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
                                    locationEditText.setText(locationText);
                                } else {
                                    Toast.makeText(SubmitJobActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SubmitJobActivity.this, "Location retrieval failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /**
     * Set focus change listeners for the edit text fields to hide the keyboard when they lose focus.
     */
    private void setEditTextFocusListeners() {
        EditText[] editTextFields = {titleEditText, durationEditText, salaryEditText, locationEditText, descriptionEditText};

        for (EditText editText : editTextFields) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard(v);
                    }
                }
            });
        }
    }

    /**
     * Handles the click event for the job submission button. It validates user inputs,
     * creates a new job, and submits it to the database.
     *
     * @param v The view that triggered the button click event.
     */
    private void onJobSubmitButtonClicked(View v) {
        String title = titleEditText.getText().toString().trim();
        String date = setDateTextView.getText().toString().trim();
        String duration = durationEditText.getText().toString().trim();
        String salary = salaryEditText.getText().toString().trim();
        String locationText = locationEditText.getText().toString().trim();
        Location location = parseLocation(locationText);
        String jobType = jobTypeSpinner.getSelectedItem().toString();
        String durationType = durationTypeSpinner.getSelectedItem().toString();
        String urgencyType = urgencyTypeSpinner.getSelectedItem().toString();
        String salaryType = salaryTypeSpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString().trim();

        boolean hasEmptyFields = false;

        if (TextUtils.isEmpty(title)) {
            titleEditText.setError("Title is required");
            hasEmptyFields = true;
        }

        if (TextUtils.isEmpty(date)) {
            setDateTextView.setError("Date is required");
            hasEmptyFields = true;
        }

        if (TextUtils.isEmpty(duration)) {
            durationEditText.setError("Duration is required");
            hasEmptyFields = true;
        }

        if (TextUtils.isEmpty(salary)) {
            salaryEditText.setError("Salary is required");
            hasEmptyFields = true;
        }

        if (TextUtils.isEmpty(locationText)) {
            locationEditText.setError("Location is required");
            hasEmptyFields = true;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionEditText.setError("Description is required");
            hasEmptyFields = true;
        }

        if (hasEmptyFields) {
            Toast.makeText(SubmitJobActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date) || TextUtils.isEmpty(duration) ||
                TextUtils.isEmpty(urgencyType) || TextUtils.isEmpty(salary) || TextUtils.isEmpty(locationText)) {
            Toast.makeText(SubmitJobActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String employerId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

            String jobId = databaseReference.push().getKey();

            location.setAltitude(0.0);

            Job job = new Job(jobId, false, title, jobType, date, duration, durationType, urgencyType, salary, salaryType, locationText, description, employerId);

            assert jobId != null;
            databaseReference.child(jobId).setValue(job);

            Snackbar.make(v, "Job submitted successfully", BaseTransientBottomBar.LENGTH_SHORT).show();

            Intent intent = new Intent(SubmitJobActivity.this, JobPostingsActivity.class);
            startActivity(intent);

            finish();
        }
    }
    private Location parseLocation(String locationText) {
        // Extract latitude and longitude from the string
        // Assuming the format is "Latitude: {latitude}, Longitude: {longitude}"

        String[] parts = locationText.split(", ");
        String latitudeString = parts[0].substring(parts[0].indexOf(":") + 2);
        String longitudeString = parts[1].substring(parts[1].indexOf(":") + 2);

        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);

        // Create a Location object
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    /**
     * Hide the software keyboard for the given view.
     *
     * @param view The view that currently has focus and should lose it.
     */
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
