package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * This activity displays the user's profile information, including their email address.
 */
public class ProfileActivity extends AppCompatActivity {


    private TextView mEmailTextView;
    private TextView mRoleTextView;
    private Button mSwitchRoleButton;

    private  FirebaseUser user;

    private FirebaseAuth mAuth;

    /**
     * Retrieves the user's email and displays it in the email TextView.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mSwitchRoleButton = findViewById(R.id.switchRoleButton);
        mEmailTextView = findViewById(R.id.emailTextView);
        this.showProfileInfo();

    }

    protected void showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
        }
    }
}