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

    private FirebaseAuth mAuth;
    private Button mSubmitRatingButton;

    private FirebaseUser selectedUser;

    private RecyclerView mRatingsRecyclerView;
    private RatingsAdapter mRatingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        selectedUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        employeeId = selectedUser.getUid(); // Replace with the actual employee's ID

        mDisplayNameTextView = findViewById(R.id.displayNameTextView);
        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        mSubmitRatingButton = findViewById(R.id.submitRatingButton);

        // Retrieve and display the employee's information
        retrieveEmployeeInformation(employeeId);

        // Initialize RecyclerView for ratings
        mRatingsRecyclerView = findViewById(R.id.ratingsRecyclerView);
        mRatingsAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRatingsRecyclerView.setAdapter(mRatingsAdapter);

        // Retrieve and display the employee's ratings
        retrieveEmployeeRatings(employeeId);

        mSubmitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue = mRatingBar.getRating();
                String comments = mCommentsEditText.getText().toString();
                submitRating(employeeId, ratingValue, comments);
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

        // You can similarly update other UI elements with additional employee information
        // For example: findViewById(R.id.emailTextView).setText(employee.getEmail());
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

    private void submitRating(String employeeId, float ratingValue, String comments) {
        // Create a new Rating object
        Rating rating = new Rating(employeeId, ratingValue, comments);

        // Store the new rating in the Firebase database
        mDatabase.child("employeeRatings").child(employeeId).push().setValue(rating);
    }
}