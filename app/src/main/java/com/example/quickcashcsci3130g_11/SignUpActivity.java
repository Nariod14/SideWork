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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the sign-up activity where users can create new accounts with their
 * email and password. It also provides validation for email and password formats.
 */
public class SignUpActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    ProgressBar progressBar;
    FirebaseAuth auth;
    private LocationAccess locationAccess;

    FirebaseDatabase database = null;
    private DatabaseConnector mDatabaseConnector;

    /**
     * Initializes the sign-up activity, sets up click listeners, and handles the user
     * registration process.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        locationAccess = new LocationAccess(this);
        locationAccess.requestLocationPermission();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabaseConnector = new DatabaseConnector();

        Button btnSignIn = findViewById(R.id.sign_in_button);
        Button btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.emailSignup);
        inputPassword = findViewById(R.id.passwordSignup);
        progressBar = findViewById(R.id.progressBar);
        Button btnResetPassword = findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class)));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

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
                                Snackbar.make(v, R.string.ACCOUNT_EXISTS, Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(v, "Authentication failed." + task.getException(), Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            startActivity(new Intent(SignUpActivity.this, EmployerActivity.class));
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

    /**
     * Initializes the Firebase database connection.
     */
    protected void initializeDatabase() {
        database = FirebaseDatabase.getInstance("https://csci3130-fall2023-a2-8bc9b-default-rtdb.firebaseio.com/");
    }

    /**
     * Validates an email address using a regular expression.
     *
     * @param target The email address to be validated.
     * @return True if the email address is valid, false otherwise.
     */
    public boolean isValidEmail(CharSequence target) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    /**
     * Validates a password to ensure it contains at least 6 characters and only consists of
     * letters (A-Za-z) and digits (0-9).
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    public boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches("[A-Za-z0-9]+");
    }
}