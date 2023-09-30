package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseConnector mDatabaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabaseConnector = new DatabaseConnector();

        Button btnSignIn = findViewById(R.id.sign_in_button);
        Button btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        Button btnResetPassword = findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class)));

        btnSignIn.setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> {

            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Snackbar.make(v, "Enter email address!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Snackbar.make(v, "Enter a valid email address", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Snackbar.make(v, "Enter password!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!isValidPassword(password)) {
                Snackbar.make(v, "Password must contain at least 6 characters, Allowed characters – A-Za-z0-9", Snackbar.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //create user w firebase
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Snackbar.make(v, "Account with this email id already exists. Kindly click on login or try again with a different email id.", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(v, "Authentication failed." + task.getException(), Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            // Write user data to the database
                            String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                            String data = "User data";
                            mDatabaseConnector.writeData(userId, data);

                            startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                            finish();
                        }
                        progressBar.setVisibility(View.GONE);
                    });

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches("[A-Za-z0-9]+");
    }
}