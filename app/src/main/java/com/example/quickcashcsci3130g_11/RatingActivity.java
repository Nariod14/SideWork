package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RatingActivity extends AppCompatActivity {

    private RatingBar mRatingBar;
    private EditText mCommentsEditText;
    private Button mSubmitButton;

    private String mJobId;
    private String mEmployeeId;
    private String mEmployerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        mRatingBar = findViewById(R.id.ratingBar);
        mCommentsEditText = findViewById(R.id.commentsEditText);
        mSubmitButton = findViewById(R.id.submitButton);

        mJobId = getIntent().getStringExtra("jobId");
        mEmployeeId = getIntent().getStringExtra("employeeId");
        mEmployerId = getIntent().getStringExtra("employerId");

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue = mRatingBar.getRating();
                String comments = mCommentsEditText.getText().toString();
                if (mEmployeeId != null) {
                    submitRating(mJobId, mEmployeeId, ratingValue, comments);
                } else if (mEmployerId != null) {
                    submitRating(mJobId, mEmployerId, ratingValue, comments);
                }
                finish();
            }
        });
    }

    private void submitRating(String jobId, String userId, float ratingValue, String comments) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String raterId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        com.example.quickcashcsci3130g_11.Rating rating = new Rating(userId,ratingValue,comments);
        database.child("ratings").child(jobId).child(userId).setValue(rating);
    }
}