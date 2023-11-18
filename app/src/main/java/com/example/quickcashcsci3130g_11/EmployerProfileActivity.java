package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private RatingBar mRatingBar;
    private EditText mCommentsEditText;
    private Button mSubmitRatingButton;

    private RecyclerView mRatingsRecyclerView;
    private RatingsAdapter mRatingsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        employerId = "employer123"; // Replace with the actual employer's ID

        mDisplayNameTextView = findViewById(R.id.displayNameTextView);
        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        mSubmitRatingButton = findViewById(R.id.submitRatingButton);

        mRatingsRecyclerView = findViewById(R.id.ratingsRecyclerView);
        mRatingsAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecyclerView.setAdapter(mRatingsAdapter);

        // Retrieve and display the employer's information
        retrieveEmployerInformation(employerId);

        // Retrieve and display the employer's ratings
        retrieveEmployerRatings(employerId);

        mSubmitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue = mRatingBar.getRating();
                String comments = mCommentsEditText.getText().toString();
                submitRating(employerId, ratingValue, comments);
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
        // Assuming you have a TextView in your layout with the ID displayNameTextView
        TextView displayNameTextView = findViewById(R.id.displayNameTextView);
        displayNameTextView.setText(employer.getDisplayName());
        // You can similarly update other UI elements with additional employer information
        // For example: findViewById(R.id.emailTextView).setText(employer.getEmail());
    }

    private void submitRating(String employerId, float ratingValue, String comments) {
        // Create a new Rating object
        Rating rating = new Rating(employerId, ratingValue, comments);

        // Store the new rating in the Firebase database
        mDatabase.child("employerRatings").child(employerId).push().setValue(rating);
    }
}
