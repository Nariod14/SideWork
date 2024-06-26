package com.example.quickcashcsci3130g_11;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcashcsci3130g_11.databinding.ActivityDashboardBinding;
import com.example.quickcashcsci3130g_11.databinding.ActivityTestBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * TestActivity is a sample activity used for testing purposes. It includes functionality to
 * simulate saving payment details to the Firebase Realtime Database.
 */
public class TestActivity extends BaseActivity {
    ActivityTestBinding activityTestBinding;

    /**
     * Initializes the TestActivity and sets up the layout, including the test button for
     * simulating payment details saving.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if any.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBinding activityTestBinding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(activityTestBinding.getRoot());
        Button payTestButton = findViewById(R.id.testPayButton);

        payTestButton.setOnClickListener(view -> {
            // Sample Payment Details
            String jobId = "NkYA7nYhEucZxJ2WqC";
            String jobTitle = "Personal Training";
            String jobType = "Fitness Services";
            String employerId = "2HQbpynKB8hXWDaIgNEl5K8zAGy2";
            String employeeId = "9xjjLjkO0XWQKK2LZ5jvGTDTZos1";
            String today = "2023-10-05";
            double amount = 75.00;

            // Call the method to save payment details
            savePaymentDetails(jobId, jobTitle, jobType, employerId, employeeId, today, amount);
        });
    }

    /**
     * Saves payment details to the Firebase Realtime Database.
     *
     * @param jobId       The unique identifier of the associated job.
     * @param jobTitle    The title of the associated job.
     * @param jobType     The type of the associated job.
     * @param employerId  The unique identifier of the employer associated with the job.
     * @param employeeId  The unique identifier of the employee associated with the job.
     * @param today       The date when the payment was made.
     * @param amount      The amount paid for the job.
     */
    private void savePaymentDetails(String jobId, String jobTitle, String jobType, String employerId, String employeeId, String today, double amount) {
        DatabaseReference paymentsRef = FirebaseDatabase.getInstance().getReference().child("payments");

        String paymentId = paymentsRef.push().getKey();

        if (paymentId != null) {
            Payment paymentDetails = new Payment(jobId, jobTitle, jobType, employerId, employeeId, today, amount);

            paymentsRef.child(paymentId).setValue(paymentDetails)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Payment details saved successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to save payment details", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getApplicationContext(), "Failed to generate a payment ID", Toast.LENGTH_SHORT).show();
        }
    }
}