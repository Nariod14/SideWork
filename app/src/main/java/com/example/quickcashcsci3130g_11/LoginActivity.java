package com.example.quickcashcsci3130g_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.textfield.TextInputEditText;

/**
 * This class represents the login activity where users can log in to their accounts using email
 * and password. It also provides a way to navigate to the registration (sign-up) activity.
 */
public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;



    /**
     * Called when the activity is first created. Initializes views, sets up click listeners, and
     * handles user login.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       mAuth = FirebaseAuth.getInstance();

        LocationAccess locationAccess;
        locationAccess = new LocationAccess(this);
        locationAccess.requestLocationPermission();

        emailEditText = findViewById(R.id.emailLogin);
        passwordEditText = findViewById(R.id.passwordLogin);

        Button loginButton;
        TextView signUpText;
        loginButton = findViewById(R.id.login_button);
        signUpText = findViewById(R.id.signup);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    /**
     * Handles the user login process, validating the email and password fields.
     * If login is successful, it navigates to the main activity.
     */
    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    goToMainActivity();
                } else {
                    showError();
                }
            }
        });
    }

    /**
     * Navigates to the main activity (Employer Activity) when the user successfully logs in.
     */
    private void goToMainActivity() {
        Toast.makeText(getApplicationContext(),"login successful",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, EmployerActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Displays an error message when the login attempt fails.
     */
    private void showError() {
        Toast.makeText(LoginActivity.this, "Invalid email/password. Please try again.", Toast.LENGTH_SHORT).show();
    }
}
