package com.example.quickcashcsci3130g_11;

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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeProfileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String employeeId;
    private TextView mDisplayNameTextView;
    private RatingBar mRatingBar;
    private EditText mCommentsEditText;

    private ImageButton backButton;

    private FirebaseAuth mAuth;

    private Button mSubmitRatingButton;

    private FirebaseUser selectedUser;

    private RecyclerView mRatingsRecyclerView;
    private RatingsAdapter mRatingsAdapter;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        selectedUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        employeeId = getIntent().getStringExtra("inputUID");
        currentUserID = getIntent().getStringExtra("currentUserID");

        mDisplayNameTextView = findViewById(R.id.displayNameTextView);
        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        mSubmitRatingButton = findViewById(R.id.submitRatingButton);
        backButton = findViewById(R.id.backButtonEmployeeProfile);

        // Retrieve and display the employee's information
        retrieveEmployeeInformation(employeeId);

        // Initialize RecyclerView for ratings
        mRatingsRecyclerView = findViewById(R.id.ratingsRecyclerView);
        mRatingsAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecyclerView.setAdapter(mRatingsAdapter);

        // Retrieve and display the employee's ratings
        retrieveEmployeeRatings(employeeId);

        // Check if the current user is viewing their own profile


        if (currentUserID.equals(employeeId)) {
            // Hide the RatingBar, EditText, and Button
            findViewById(R.id.ratingBar).setVisibility(View.GONE);
            findViewById(R.id.commentsEditText).setVisibility(View.GONE);
            findViewById(R.id.submitRatingButton).setVisibility(View.GONE);
        }

        mSubmitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue = mRatingBar.getRating();
                String comments = mCommentsEditText.getText().toString();
                submitRating(employeeId, ratingValue, comments);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
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
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Check if the current user is trying to rate their own profile
        if (currentUserID.equals(userId)) {
            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "You cannot rate your own profile!", Snackbar.LENGTH_SHORT).show();
            return; // Exit the method to prevent rating self
        }

        // Check if the current user has already submitted a review
        mDatabase.child("employeeRatings").child(currentUserID).child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "You've already submitted a review!", Snackbar.LENGTH_SHORT).show();
                        } else {
                            // Store the new rating in the Firebase database
                            mDatabase.child("employeeRatings").child(currentUserID).child(userId).setValue(new Rating(currentUserID, ratingValue, comments));
                            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "Review submitted successfully!", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
    }
}

