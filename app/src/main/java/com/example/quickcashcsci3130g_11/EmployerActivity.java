package com.example.quickcashcsci3130g_11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class represents the main activity for employers. It allows employers to perform various
 * actions like switching to the employee role, adding a job, viewing job postings, and more.
 */
public class EmployerActivity extends AppCompatActivity {

    private TextView mEmailTextView;
    private Button mSwitchRoleButton;
    private Button mGoToPayment;
    private Button mAddJobButton;
    private Button mJobPostings;
    private Button mViewPreferredEmployees;
    private Button mAcceptedApplications;
    private Button reportButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    /**
     * Called when the activity is first created. Initializes views, sets up click listeners,
     * and shows user profile information.
     *
     * @param savedInstanceState The saved instance state, if any.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationAccess locationAccess;

        locationAccess = new LocationAccess(this);
        locationAccess.requestLocationPermission();
        setContentView(R.layout.activity_employer);
        Button logout=(Button)findViewById(R.id.logout);

        mSwitchRoleButton = findViewById(R.id.switch2EmployeeButton);
        mEmailTextView = findViewById(R.id.emailTextView);
        mGoToPayment = findViewById(R.id.paymentButton);
        String userID = new String();
        mAddJobButton = findViewById(R.id.employerAddJob);
        mJobPostings = findViewById(R.id.employerViewJobs);
        mViewPreferredEmployees =  findViewById(R.id.employerPreferredEmployees);
        mAcceptedApplications =  findViewById(R.id.employerAcceptedApplications);
        reportButton  = findViewById(R.id.employerReport);


        this.showProfileInfo();
        this.showEmployerMessage();
        this.switch2Employee();
        this.goToPayments();
        this.go2SubmitJob();
        this.go2JobPostings();
        this.viewPreferredEmployees();
        this.viewAcceptedApplication();
        this.employerReport();

        logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    });


    }

    /**
     * Show the user's profile information on the UI.
     */
    protected void showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
        }
    }

    /**
     * Display a welcome message for the employer using a Snackbar.
     */
    protected void showEmployerMessage() {
        ConstraintLayout constraintLayout = findViewById(R.id.rLayout);

        String userEmail = user != null ? user.getEmail() : "Unknown";
        String employerMessage = getString(R.string.EMPLOYER_MESSAGE, userEmail);

        Snackbar employerSnack = Snackbar.make(constraintLayout, employerMessage, BaseTransientBottomBar.LENGTH_SHORT);
        employerSnack.show();
    }

    /**
     * Switch to the employee role when the "Switch to Employee" button is clicked.
     */
    protected void switch2Employee() {
        mSwitchRoleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployerActivity.this, EmployeeActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
    * Switch to payments page when the "go to Payments" button is clicked.
    */
    protected void goToPayments() {
        mGoToPayment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployerActivity.this, PaymentsActivity.class);
                startActivity(intent);

    /**
     * Navigate to the job submission activity when the "Add Job" button is clicked.
     */
    protected void go2SubmitJob() {
        mAddJobButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployerActivity.this, SubmitJobActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
     * Navigate to the job postings activity when the "View Job Postings" button is clicked.
     */
    protected void go2JobPostings() {
        mJobPostings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployerActivity.this, JobPostingsActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
     * Handle viewing preferred employees when the corresponding button is clicked.
     */
    public void viewPreferredEmployees(){
        mViewPreferredEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });
    }

    /**
     * Handle viewing accepted job applications when the corresponding button is clicked.
     */
    public void viewAcceptedApplication() {
        mAcceptedApplications.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                // Do what you want here
            }
        });
    }


    /**
     * Handle generating employer reports when the "Generate Report" button is clicked.
     */
    public void employerReport() {
        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Do what you want here
            }
        });
    }
}
