package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;

public class EmployeeProfileActivity extends AppCompatActivity {

    private TextView mEmailTextView;
    private TextView EmployeeTextView;
    private Button switch2EmployerButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        switch2EmployerButton = findViewById(R.id.switch2EmployerButton);
        mEmailTextView = findViewById(R.id.emailTextView);
        this.showProfileInfo();
        this.switch2Employer();
        this.showEmployeeMessage();


    }

    protected void showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
        }
    }

    protected void showEmployeeMessage() {
        RelativeLayout relativeLayout = findViewById(R.id.eLayout);

        String employeeMessage = getString(R.string.EMPLOYEE_MESSAGE, user);
        Snackbar employeeSnack = Snackbar.make(relativeLayout, employeeMessage, Snackbar.LENGTH_SHORT);
        employeeSnack.show();
    }
    protected void switch2Employer() {
        switch2EmployerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployeeProfileActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

    }


}
