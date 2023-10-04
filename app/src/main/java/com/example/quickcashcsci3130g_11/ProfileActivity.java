package com.example.quickcashcsci3130g_11;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mRoleTextView;
    private Button mSwitchRoleButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



}