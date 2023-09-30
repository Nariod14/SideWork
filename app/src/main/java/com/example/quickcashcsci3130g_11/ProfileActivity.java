package com.example.quickcashcsci3130g_11;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mRoleTextView;
    private Button mSwitchRoleButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameTextView = findViewById(R.id.nameTextView);
        mEmailTextView = findViewById(R.id.emailTextView);
        mRoleTextView = findViewById(R.id.roleTextView);
        mSwitchRoleButton = findViewById(R.id.switchRoleButton);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String role = snapshot.child("role").getValue(String.class);
                    mNameTextView.setText(name);
                    mEmailTextView.setText(email);
                    mRoleTextView.setText(role);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Failed to read user data", error.toException());
                }
            });
        }

        mSwitchRoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference userRef = mDatabase.child("users").child(userId);
                    userRef.child("role").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String currentRole = snapshot.getValue(String.class);
                            String newRole = currentRole.equals("employee") ? "employer" : "employee";
                            userRef.child("role").setValue(newRole);
                            mRoleTextView.setText(newRole);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, "Failed to read user role", error.toException());
                        }
                    });
                }
            }
        });
    }
}