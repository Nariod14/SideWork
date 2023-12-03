package com.example.quickcashcsci3130g_11;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeProfileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private TextView mDisplayNameTextView;


    private TextView mReputationScoreTextView;
    private RatingsAdapter mRatingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText mCommentsEditText;
        RatingBar mRatingBar;
        String employeeId;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        employeeId = getIntent().getStringExtra("inputUID");
        String currentUserID = getIntent().getStringExtra("currentUserID");

        mReputationScoreTextView = findViewById(R.id.reputationScoreTextView);
        mDisplayNameTextView = findViewById(R.id.displayNameTextView);
        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        Button mSubmitRatingButton = findViewById(R.id.submitRatingButton);
        ImageButton backButton = findViewById(R.id.backButtonEmployeeProfile);

        // Retrieve and display the employee's information
        retrieveEmployeeInformation(employeeId);

        // Initialize RecyclerView for ratings
        RecyclerView mRatingsRecyclerView = findViewById(R.id.ratingsRecyclerView);
        mRatingsAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecyclerView.setAdapter(mRatingsAdapter);

        // Retrieve and display the employee's ratings
        retrieveEmployeeRatings(employeeId);

        retrieveReputationScore(employeeId);


        // Check if the current user is viewing their own profile


        assert currentUserID != null;
        if (currentUserID.equals(employeeId)) {
            // Hide the RatingBar, EditText, and Button
            findViewById(R.id.ratingBar).setVisibility(View.GONE);
            findViewById(R.id.commentsEditText).setVisibility(View.GONE);
            findViewById(R.id.submitRatingButton).setVisibility(View.GONE);
        }

        mSubmitRatingButton.setOnClickListener(v -> {
            float ratingValue = mRatingBar.getRating();
            String comments = mCommentsEditText.getText().toString();
            submitRating(employeeId, ratingValue, comments);
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void retrieveReputationScore(String employeeId) {
        mDatabase.child("employeeRatings").child(employeeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        float totalScore = 0;
                        int numReviews = 0;
                        for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                            Rating rating = ratingSnapshot.getValue(Rating.class);
                            totalScore += rating != null ? rating.getRatingValue() : 0;
                            numReviews++;
                        }
                        if (numReviews > 0) {
                            float averageScore = totalScore / numReviews;
                            mReputationScoreTextView.setText("Reputation Score: " + averageScore);
                        } else {
                            mReputationScoreTextView.setText("No reviews yet");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
    }
    private void retrieveEmployeeInformation(String employeeId) {
        mDatabase.child("users").child(employeeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User employee = dataSnapshot.getValue(User.class);
                        if (employee != null) {
                            // Update the UI with employee information
                            updateUIWithEmployeeInformation(employee);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
    }

    private void updateUIWithEmployeeInformation(User employee) {
        // Assuming you have a TextView in your layout with the ID displayNameTextView
        mDisplayNameTextView.setText(employee.getDisplayName());
    }

    private void retrieveEmployeeRatings(String employeeId) {
        mDatabase.child("employeeRatings").child(employeeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Rating> ratings = new ArrayList<>();
                        for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                            Rating rating = ratingSnapshot.getValue(Rating.class);
                            ratings.add(rating);
                        }

                        // Update the RecyclerView with employee ratings
                        mRatingsAdapter.setRatingsList(ratings);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
    }

    private void submitRating(String userId, float ratingValue, String comments) {
        // Get the current user's ID
        String currentUserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        // Check if the current user is trying to rate their own profile
        if (currentUserID.equals(userId)) {
            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "You cannot rate your own profile!", BaseTransientBottomBar.LENGTH_SHORT).show();
            return; // Exit the method to prevent rating self
        }

        // Check if the current user has already submitted a review
        mDatabase.child("employeeRatings").child(currentUserID).child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "You've already submitted a review!", BaseTransientBottomBar.LENGTH_SHORT).show();
                        } else {
                            // Store the new rating in the Firebase database
                            mDatabase.child("employeeRatings").child(currentUserID).child(userId).setValue(new Rating(currentUserID, ratingValue, comments));
                            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "Review submitted successfully!", BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
    }
}

