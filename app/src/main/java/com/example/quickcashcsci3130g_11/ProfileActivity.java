package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    private TextView mEmailTextView;
    private TextView mRoleTextView;
    private Button mSwitchRoleButton;

    private  FirebaseUser user;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mSwitchRoleButton = findViewById(R.id.switchRoleButton);
        mEmailTextView = findViewById(R.id.emailTextView);
        String userID = new String();

        this.showProfileInfo();
        this.showEmployerMessage();;
        this.switch2Employee();


    }

    protected void showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
        }
    }

    protected void showEmployerMessage() {
        RelativeLayout relativeLayout = findViewById(R.id.rLauout);

        String userEmail = user != null ? user.getEmail() : "Unknown";
        String employerMessage = getString(R.string.EMPLOYER_MESSAGE, userEmail);

        Snackbar employerSnack = Snackbar.make(relativeLayout, employerMessage, Snackbar.LENGTH_SHORT);
        employerSnack.show();
    }

    protected void switch2Employee() {
        mSwitchRoleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(ProfileActivity.this, EmployeeProfileActivity.class);
                startActivity(intent);

            }
        });
    }
}