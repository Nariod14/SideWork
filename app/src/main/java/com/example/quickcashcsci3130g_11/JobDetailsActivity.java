package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashcsci3130g_11.databinding.ActivityJobDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays the details of a job and allows users to apply for the job.
 */
public class JobDetailsActivity extends BaseActivity implements ApplicantInterface{
    Button applyButton;
    Button preferredButton;
    ActivityJobDetailsBinding jobDetailsBinding;
    private Job job;
    private ApplicantAdapter adapter;
    private List<JobApplicant> applicantsList;

    /**
     * Called when the activity is first created. Initializes UI elements, displays job details, and sets click listeners for buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView descriptionTextView;
        TextView locationTextView;
        TextView salaryTextView;
        TextView urgencyTextView;
        TextView durationTextView;
        TextView dateTextView;
        TextView jobTypeTextView;
        TextView titleTextView;
        super.onCreate(savedInstanceState);
        jobDetailsBinding = ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(jobDetailsBinding.getRoot());

        setSubheaderVisibility(false);
        setHeaderVisibility(false);
        displayAcceptedApplicant(false);

        // Initialize UI elements
        titleTextView = findViewById(R.id.jobTitleTextView);
        jobTypeTextView = findViewById(R.id.jobTypeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        durationTextView = findViewById(R.id.durationTextView);
        urgencyTextView = findViewById(R.id.urgencyTextView);
        salaryTextView = findViewById(R.id.salaryTextView);
        locationTextView = findViewById(R.id.locationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        applyButton = findViewById(R.id.applyButton);

        // Get the job object from the intent

        job = getIntent().getSerializableExtra("job", Job.class);
        checkForAcceptedApplicant(job);


        // Display job details
        if (job != null) {
            titleTextView.setText(job.getTitle());
            jobTypeTextView.setText(job.getJobType());
            dateTextView.setText(job.getDate());
            String retrievedDuration = job.getDuration() + " " + job.getDurationType();
            durationTextView.setText(retrievedDuration);
            urgencyTextView.setText(job.getUrgencyType());
            String retrievedSalary = job.getSalary() + " " + job.getSalaryType();
            salaryTextView.setText(retrievedSalary);

            if (job.getLocation() != null) {
                double latitude = job.getLocation().getLatitude();
                double longitude = job.getLocation().getLongitude();
                String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
                locationTextView.setText(locationText);
            }

            descriptionTextView.setText(job.getDescription());
        }
        String userRole = appPreferences.getUserRole();
        customizeJobDetails(userRole, user.getUid());


    }

    /**
     * Customizes the display of job details based on the user's role.
     *
     * @param userRole The role of the user (employee or employer).
     * @param userUid  The UID of the user.
     */
    public void customizeJobDetails(String userRole, String userUid) {
        LinearLayout applicantsLayout = findViewById(R.id.applicantsLayout);
        LinearLayout acceptedApplicantsLayout = findViewById(R.id.acceptedApplicantLayout);
        preferredButton = findViewById(R.id.preferredJobButton);
        applyButton = findViewById(R.id.applyButton);
        Button rateEmployerButton = findViewById(R.id.rateEmployerButton);

        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            applicantsLayout.setVisibility(View.VISIBLE);
            preferredButton.setVisibility(View.GONE);
            applyButton.setVisibility(View.GONE);

            retrieveApplicants();

        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            if (job.getAcceptedApplicantUid() != null && job.getAcceptedApplicantUid().equals(userUid)) {
                // User is the accepted applicant, hide applyButton and preferredButton, and show rateEmployerButton
                applyButton.setVisibility(View.GONE);
                preferredButton.setVisibility(View.GONE);
                applicantsLayout.setVisibility(View.GONE);
                acceptedApplicantsLayout.setVisibility(View.VISIBLE);

                rateEmployerButton.setVisibility(View.VISIBLE);

            } else {
                // User is not the accepted applicant, show applyButton and preferredButton, hide rateEmployerButton
                applyButton.setVisibility(View.VISIBLE);
                preferredButton.setVisibility(View.VISIBLE);
                acceptedApplicantsLayout.setVisibility(View.GONE);
                applicantsLayout.setVisibility(View.GONE);

                rateEmployerButton.setVisibility(View.GONE);

                // Set click listener for applyButton
                applyButton.setOnClickListener(v -> applyForJob());
            }
        }
    }

    /**
     * Handles the "Apply" button click event. It allows the user to apply for the displayed job.
     */
    private void applyForJob() {
        String userUid;
        // Get the user's Firebase UID (replace with actual UID retrieval method)
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in, and you can get their UID
            userUid = user.getUid();
        } else {
            // User is not signed in; handle this case appropriately
            userUid = "TestUser";
        }
        // Check if the user has already applied for this job to avoid duplicate applications
        if (job.getApplicants() != null && job.getApplicants().contains(userUid)) {
            Toast.makeText(this, "You have already applied for this job.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the user's UID to the job's applicants list
        job.addApplicant(userUid);



        // Firebase database reference to update the job
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(job.getJobId());


        // Update the 'applicants' field in the job node with the user's UID
        jobRef.child("applicants").setValue(job.getApplicants(), (error, ref) -> {
            if (error == null) {
                Toast.makeText(this, "Applied for the job successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to apply for the job. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Retrieves the list of applicants for the displayed job from the Firebase database.
     */
    private void retrieveApplicants() {
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(job.getJobId()).child("applicants");

        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> applicantUids = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String applicantUid = snapshot.getValue(String.class);
                    applicantUids.add(applicantUid);
                }

                applicantsList = new ArrayList<>();

                for (String applicantUid : applicantUids) {
                    retrieveApplicantDetails(applicantUid, applicantsList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    /**
     * Retrieves details of a specific job applicant using their UID and populates the `applicantsList`.
     *
     * @param userUid         The UID of the job applicant.
     * @param applicantsList  The list to store the retrieved job applicants.
     */
    private void retrieveApplicantDetails(String userUid, List<JobApplicant> applicantsList ) {
        Log.d("userid",userUid);
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = usersRef.child(userUid);
        Log.d("query", String.valueOf(query));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
//                    for (DataSnapshot applicantSnapshot : dataSnapshot.getChildren()) {
                    JobApplicant jobApplicant;
                    Log.e("applicantSnapshot", String.valueOf(dataSnapshot));

                    jobApplicant = dataSnapshot.getValue(JobApplicant.class);
                    Log.e("jobApplicant", String.valueOf(jobApplicant));

                    String displayName;
                    String userEmail;
                    String uid;

                    if (jobApplicant != null) {
                        displayName = jobApplicant.getDisplayName();
                        userEmail = jobApplicant.getEmail();
                        uid = dataSnapshot.getKey();

                        Log.e("userEmail", userEmail);
                        Log.e("displayName", displayName);
                        Log.e("uid", uid);

                        JobApplicant applicant = new JobApplicant(uid, userEmail, displayName);
                        applicantsList.add(applicant);

                    }
                    ApplicantAdapter adapter = new ApplicantAdapter(applicantsList, JobDetailsActivity.this);
                    setupApplicantsRecyclerView(adapter);
                    Log.d("applicantsListSize", String.valueOf(applicantsList.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error,
            }
        });
    }

    /**
     * Sets up the RecyclerView to display the list of job applicants.
     *
     * @param adapter The adapter for the RecyclerView.
     */
    private void setupApplicantsRecyclerView(ApplicantAdapter adapter) {
        RecyclerView applicantRecyclerView = findViewById(R.id.applicantsRecyclerView);

        // Create a LinearLayoutManager for vertical scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        applicantRecyclerView.setLayoutManager(layoutManager);

        applicantRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Callback method triggered when an applicant is accepted. Updates the Firebase database with the selected applicant.
     *
     * @param position The position of the selected applicant in the list.
     */
    @Override
    public void onAcceptApplicantClick(int position) {
        JobApplicant selectedApplicant = applicantsList.get(position);
        String uid = selectedApplicant.getUid();
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(job.getJobId());

        job.setAcceptedApplicantUid(uid);

        jobRef.child("acceptedApplicantUid").setValue(job.getAcceptedApplicantUid());

        Toast.makeText(this, "Accepted applicant " + selectedApplicant.getDisplayName(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays or hides the layout for the accepted applicant based on the specified visibility.
     *
     * @param visible True to display the layout, false to hide it.
     */
    public void displayAcceptedApplicant(boolean visible){
        LinearLayout acceptedApplicantLayout = findViewById(R.id.acceptedApplicantLayout);
        acceptedApplicantLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Checks if there is an accepted applicant for the job and displays their details if present.
     *
     * @param job The job for which to check the accepted applicant.
     */
    private void checkForAcceptedApplicant(Job job) {
        // Assuming you have a DatabaseReference for the job
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(this.job.getJobId());

        jobRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Check if there's an accepted applicant UID in the job data
                    if (dataSnapshot.hasChild("acceptedApplicantUid")) {
                        String acceptedApplicantUid = (String) dataSnapshot.child("acceptedApplicantUid").getValue();

                        if (acceptedApplicantUid != null && !acceptedApplicantUid.isEmpty()) {
                            displayAcceptedApplicant(true);

                            displayAcceptedApplicantDetails(acceptedApplicantUid);
                            onPayButtonClick();
                        } else {
                            displayAcceptedApplicant(false);
                        }
                    } else {
                        displayAcceptedApplicant(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    /**
     * Displays details of the accepted applicant by querying the Firebase database.
     *
     * @param applicantUid The UID of the accepted applicant.
     */
    private void displayAcceptedApplicantDetails(String applicantUid) {
        // Assuming you have a DatabaseReference for user details
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(applicantUid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String displayName = dataSnapshot.child("displayName").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    // Update your TextViews with the retrieved display name and email
                    TextView displayNameTextView = findViewById(R.id.acceptedApplicantNameTextView);
                    TextView emailTextView = findViewById(R.id.acceptedApplicantEmailTextView);

                    displayNameTextView.setText(displayName);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    /**
     * Handles the button click event to initiate the payment process for the accepted applicant.
     */
    void onPayButtonClick(){
        Button payButton;
        payButton = findViewById(R.id.payApplicantButton);
        if (payButton != null){
            payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PaymentsActivity.class);

                    // Pass the current jobId, jobTitle, jobType, employer userID, employee userID
                    String jobId = job.getJobId();
                    String jobTitle = job.getTitle();
                    String jobType = job.getJobType();
                    String employerUserId = job.getEmployerId();
                    String employeeUserId = job.getAcceptedApplicantUid();

                    intent.putExtra("jobId", jobId);
                    intent.putExtra("jobTitle", jobTitle);
                    intent.putExtra("jobType", jobType);
                    intent.putExtra("employerUserId", employerUserId);
                    intent.putExtra("employeeUserId", employeeUserId);

                    startActivity(intent);
                }
            });
        }
    }
}