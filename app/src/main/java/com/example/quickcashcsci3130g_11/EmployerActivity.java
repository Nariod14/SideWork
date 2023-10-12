package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        Button AddJobPosting = (Button) findViewById(R.id.employerAddJob);
        Button ViewJobPostings = (Button) findViewById(R.id.employerViewJobs);
        Button ViewPreferredEmployees = (Button) findViewById(R.id.employerPreferredEmployees);
        Button AcceptedApplications = (Button) findViewById(R.id.employerAcceptedApplications);
        Button Report = (Button) findViewById(R.id.employerReport);

        AddJobPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });

        ViewJobPostings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });

        ViewPreferredEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });

        AcceptedApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });
    }
}
