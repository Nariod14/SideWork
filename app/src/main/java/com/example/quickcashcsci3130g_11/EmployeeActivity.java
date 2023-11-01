package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmployeeActivity extends AppCompatActivity{

    private TextView mEmailTextView;
    private TextView EmployeeTextView;
    private Button switch2EmployerButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private LocationAccess locationAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationAccess = new LocationAccess(this);
        locationAccess.requestLocationPermission();
        setContentView(R.layout.activity_employee);
        Button AvailableJobs = (Button) findViewById(R.id.employerPreferredEmployees);
        Button AppliedJobs = (Button) findViewById(R.id.employerAddJob);
        Button AcceptedJobs = (Button) findViewById(R.id.employerViewJobs);
        Button Report = (Button) findViewById(R.id.employerReport);

        Button logout=(Button)findViewById(R.id.logout);

        switch2EmployerButton = findViewById(R.id.switch2EmployerButton);
        mEmailTextView = findViewById(R.id.emailTextView);

        String email = this.showProfileInfo();
        this.showEmployeeMessage(email);
        this.switch2Employer();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


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



    protected String showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
            return email;
        }
//        if no profile of user, handle that scenario
        return null;
    }

    protected void showEmployeeMessage(String email) {
        ConstraintLayout constraintLayout = findViewById(R.id.eLayout);
        String employeeMessage = getString(R.string.EMPLOYEE_MESSAGE, email);

        Snackbar employeeSnack = Snackbar.make(constraintLayout, employeeMessage, Snackbar.LENGTH_SHORT);
        employeeSnack.show();
    }
    protected void switch2Employer() {
        switch2EmployerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployeeActivity.this, EmployerActivity.class);
                startActivity(intent);

            }
        });

    }


}