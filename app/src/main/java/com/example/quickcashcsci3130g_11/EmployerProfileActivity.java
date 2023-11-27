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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployerProfileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String employerId;
    private TextView mDisplayNameTextView;
    private ImageButton backButton;
    private RatingBar mRatingBar;
    private EditText mCommentsEditText;
    private Button mSubmitRatingButton;

    private RecyclerView mRatingsRecyclerView;
    private RatingsAdapter mRatingsAdapter;

    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        employerId = getIntent().getStringExtra("inputUID");
        currentUserID = getIntent().getStringExtra("currentUserID");


        mDisplayNameTextView = findViewById(R.id.displayNameTextView);
        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        mSubmitRatingButton = findViewById(R.id.submitRatingButton);
        backButton = findViewById(R.id.backButtonEmployerProfile);

        mRatingsRecyclerView = findViewById(R.id.ratingsRecyclerView);
        mRatingsAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecyclerView.setAdapter(mRatingsAdapter);

        // Retrieve and display the employer's information
        retrieveEmployerInformation(employerId);

        // Retrieve and display the employer's ratings
        retrieveEmployerRatings(employerId);




        if (currentUserID.equals(employerId)) {
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
                submitRating(employerId, ratingValue, comments);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
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
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Rating rating = new Rating(employerId, ratingValue, comments);

        mDatabase.child("employeeRatings").child(currentUserID).child(employerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User employer = dataSnapshot.getValue(User.class);
                        if (employer != null) {
                            Snackbar.make(findViewById(R.id.backButtonEmployerProfile), "You've already submitted a review!", Snackbar.LENGTH_SHORT).show();
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
