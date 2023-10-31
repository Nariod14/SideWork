package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitJobActivity extends AppCompatActivity {

    private EditText titleEditText;

    private EditText jobTypeEditText;
    private EditText dateEditText;
    private EditText durationEditText;
    private EditText urgencyEditText;
    private EditText salaryEditText;
    private EditText locationEditText;
    private Button submitButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_job);

        titleEditText = findViewById(R.id.titleEditText);
        jobTypeEditText = findViewById(R.id.jobTypeEditText);
        dateEditText = findViewById(R.id.dateEditText);
        durationEditText = findViewById(R.id.durationEditText);
        urgencyEditText = findViewById(R.id.urgencyEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        locationEditText = findViewById(R.id.locationEditText);
        submitButton = findViewById(R.id.submitButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("jobs");

        // Betsy: edit the job entries to have options

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();
                String jobType = jobTypeEditText.getText().toString().trim();
                String duration = durationEditText.getText().toString().trim();
                String urgency = urgencyEditText.getText().toString().trim();
                String salary = salaryEditText.getText().toString().trim();
                String location = locationEditText.getText().toString().trim();

                if (TextUtils.isEmpty(title)) {
                    titleEditText.setError("Title is required");
                    return;
                }

                if (TextUtils.isEmpty(date)) {
                    dateEditText.setError("Date is required");
                    return;
                }

                if (TextUtils.isEmpty(jobType)) {
                    dateEditText.setError("Job Type is required");
                    return;
                }

                if (TextUtils.isEmpty(duration)) {
                    durationEditText.setError("Duration is required");
                    return;
                }

                if (TextUtils.isEmpty(urgency)) {
                    urgencyEditText.setError("Urgency is required");
                    return;
                }

                if (TextUtils.isEmpty(salary)) {
                    salaryEditText.setError("Salary is required");
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    locationEditText.setError("Location is required");
                    return;
                }

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String employerId = firebaseAuth.getCurrentUser().getUid();

                String jobId = databaseReference.push().getKey();

                Job job = new Job(jobId, title, jobType, date, duration, urgency, salary, location, employerId);

                databaseReference.child(jobId).setValue(job);

                Snackbar.make(v, "Job submitted successfully", Snackbar.LENGTH_SHORT).show();


                finish();
            }
        });
    }
}