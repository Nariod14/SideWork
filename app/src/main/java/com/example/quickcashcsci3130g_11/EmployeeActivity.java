package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployeeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        Button AvailableJobs = (Button) findViewById(R.id.employerPreferredEmployees);
        Button AppliedJobs = (Button) findViewById(R.id.employerAddJob);
        Button AcceptedJobs = (Button) findViewById(R.id.employerViewJobs);
        Button Report = (Button) findViewById(R.id.employerReport);

        AvailableJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });

        AppliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here

            }
        });

        AcceptedJobs.setOnClickListener(new View.OnClickListener() {
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