package com.example.quickcashcsci3130g_11;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashcsci3130g_11.databinding.ActivityEmployeeBinding;
import com.example.quickcashcsci3130g_11.databinding.ActivityEmployerProfileBinding;
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

public class EmployerProfileActivity extends BaseActivity {
    private DatabaseReference mDatabase;

    private TextView mDisplayNameTextView;


    private TextView mReputationScoreTextView;

    private RatingsAdapter mRatingsAdapter;
    ActivityEmployerProfileBinding employerProfileBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText mCommentsEditText;
        RatingBar mRatingBar;
        String employerId;
        super.onCreate(savedInstanceState);
        employerProfileBinding = ActivityEmployerProfileBinding.inflate(getLayoutInflater());
        setContentView(employerProfileBinding.getRoot());

        String activityTitle = getString(R.string.MY_PROFILE);
        setToolbarTitle(activityTitle);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        employerId = getIntent().getStringExtra("inputUID");
        String currentUserID = getIntent().getStringExtra("currentUserID");


        mReputationScoreTextView = findViewById(R.id.reputationScoreTextView);
        mDisplayNameTextView = findViewById(R.id.displayNameTextView);
        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        Button mSubmitRatingButton = findViewById(R.id.submitRatingButton);

        RecyclerView mRatingsRecyclerView = findViewById(R.id.ratingsRecyclerView);
        mRatingsAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecyclerView.setAdapter(mRatingsAdapter);

        // Retrieve and display the employer's information
        retrieveEmployerInformation(employerId);

        // Retrieve and display the employer's ratings
        retrieveEmployerRatings(employerId);

        retrieveReputationScore(employerId);


        assert currentUserID != null;
        if (currentUserID.equals(employerId)) {
            // Hide the RatingBar, EditText, and Button
            findViewById(R.id.ratingBar).setVisibility(View.GONE);
            findViewById(R.id.commentsEditText).setVisibility(View.GONE);
            findViewById(R.id.submitRatingButton).setVisibility(View.GONE);
        }

        mSubmitRatingButton.setOnClickListener(v -> {
            float ratingValue = mRatingBar.getRating();
            String comments = mCommentsEditText.getText().toString();
            submitRating(employerId, ratingValue, comments);
        });
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
                            assert rating != null;
                            totalScore += rating.getRatingValue();
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

    private void retrieveEmployerRatings(String employerId) {
        mDatabase.child("employerRatings").child(employerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Rating> ratings = new ArrayList<>();
                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    Rating rating = ratingSnapshot.getValue(Rating.class);
                    ratings.add(rating);
                }

                mRatingsAdapter.setRatingsList(ratings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    private void retrieveEmployerInformation(String employerId) {
        mDatabase.child("users").child(employerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User employer = dataSnapshot.getValue(User.class);
                        if (employer != null) {
                            // Update the UI with employer information
                            updateUIWithEmployerInformation(employer);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
    }

    private void updateUIWithEmployerInformation(User employer) {
        mDisplayNameTextView.setText(employer.getDisplayName());
    }

    private void submitRating(String employerId, float ratingValue, String comments) {
        // Create a new Rating object
        String currentUserID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Rating rating = new Rating(employerId, ratingValue, comments);

        mDatabase.child("employeeRatings").child(currentUserID).child(employerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User employer = dataSnapshot.getValue(User.class);
                        if (employer != null) {
                            LinearLayout employerProfileLayout = findViewById(R.id.employerProfileLayout);
                            Snackbar.make(employerProfileLayout, "You've already submitted a review!", BaseTransientBottomBar.LENGTH_SHORT).show();
                        } else
                            // Store the new rating in the Firebase database
                            mDatabase.child("employerRatings").child(employerId).push().setValue(rating);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });



    }
}
