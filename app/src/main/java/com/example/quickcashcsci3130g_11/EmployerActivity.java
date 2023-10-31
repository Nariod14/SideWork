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

public class EmployerActivity extends AppCompatActivity {

    private TextView mEmailTextView;
    private Button mSwitchRoleButton;
    private Button mAddJobButton;

    private FirebaseUser user;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        Button ViewJobPostings = (Button) findViewById(R.id.employerViewJobs);
        Button ViewPreferredEmployees = (Button) findViewById(R.id.employerPreferredEmployees);
        Button AcceptedApplications = (Button) findViewById(R.id.employerAcceptedApplications);
        Button Report = (Button) findViewById(R.id.employerReport);
        Button logout=(Button)findViewById(R.id.logout);

        mSwitchRoleButton = findViewById(R.id.switch2EmployeeButton);
        mEmailTextView = findViewById(R.id.emailTextView);
        mAddJobButton = findViewById(R.id.employerAddJob);

        String userID = new String();

        this.showProfileInfo();
        this.showEmployerMessage();;
        this.switch2Employee();
        this.go2SubmitJob();

        logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    });


        ViewJobPostings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    protected void showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
        }
    }

    protected void showEmployerMessage() {
        ConstraintLayout constraintLayout = findViewById(R.id.rLayout);

        String userEmail = user != null ? user.getEmail() : "Unknown";
        String employerMessage = getString(R.string.EMPLOYER_MESSAGE, userEmail);

        Snackbar employerSnack = Snackbar.make(constraintLayout, employerMessage, Snackbar.LENGTH_SHORT);
        employerSnack.show();
    }

    protected void switch2Employee() {
        mSwitchRoleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployerActivity.this, EmployeeActivity.class);
                startActivity(intent);

            }
        });
    }

    protected void go2SubmitJob() {
        mAddJobButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployerActivity.this, SubmitJobActivity.class);
                startActivity(intent);

            }
        });
    }

}
