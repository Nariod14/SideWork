package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This activity displays the user's profile information, including their email address.
 */
public class ProfileActivity extends AppCompatActivity {

    private TextView mEmailTextView;
    private TextView mRoleTextView;
    private Button mRateEmployeeButton;
    private Button mRateEmployerButton;

    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mEmailTextView = findViewById(R.id.emailTextView);
        mRoleTextView = findViewById(R.id.roleTextView);
        mRateEmployeeButton = findViewById(R.id.rateEmployeeButton);
        mRateEmployerButton = findViewById(R.id.rateEmployerButton);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mUser != null) {
            String email = mUser.getEmail();
            mEmailTextView.setText(email);


        }
    }
}