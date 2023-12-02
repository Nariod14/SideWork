package com.example.quickcashcsci3130g_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quickcashcsci3130g_11.databinding.ActivityPaymentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity that handles PayPal payments.
 *
 * This activity facilitates PayPal payments, allowing users to enter a payment amount and initiate the payment process
 * through PayPal's SDK. It handles the PayPal payment callbacks and displays the payment confirmation status.
 */
public class PaymentsActivity extends BaseActivity {
    ActivityPaymentBinding paymentBinding;
    TextView jobTitleTextView;
    TextView jobTypeTextView;
    TextView todaysDateTextView;
    TextView employerNameTextView;
    TextView employeeDisplayNameTextView;
    TextView employeeEmailTextView;
    private double amountPaid;

    private static final String PAYPAL_CLIENT_ID = "ATZtaMEgQFDP3ooX9ZfplTVZwNLi8KrXkjwCy90hv1C5FHX-Cz5SguVWlUhhN12PMp8-3dWOGujzXZVp";
    private static final int PAYPAL_REQUEST_CODE = 123;

    private EditText amountEditText;
    private Button makePaymentButton;

    private PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(paymentBinding.getRoot());

        String activityTitle = getString(R.string.PAYMENT);
        setToolbarTitle(activityTitle);

        Intent intent = getIntent();
        if (intent != null) {
            String jobId = intent.getStringExtra("jobId");
            String jobTitle = intent.getStringExtra("jobTitle");
            String jobType = intent.getStringExtra("jobType");
            String employerUserId = intent.getStringExtra("employerUserId");
            String employeeUserId = intent.getStringExtra("employeeUserId");

            populatePaymentDetails(jobTitle, jobType, employerUserId,employeeUserId);
        }


        amountEditText = findViewById(R.id.amountEditText);
        makePaymentButton = findViewById(R.id.makePaymentButton);

        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountPaid = Double.parseDouble(amountEditText.getText().toString());
                processPayment(amountPaid);
            }
        });
    }

    /**
     * Initiates the payment process by collecting the payment details and starting the PayPal payment activity.
     */
    public void processPayment(Double amountPaid) {
//        String amount = amountEditText.getText().toString();
        String amount = String.valueOf(amountPaid);

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "CAD",
                "Payment for Job", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    /**
     * Handles the result from PayPal's payment activity.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        showDetails(paymentDetails, amountPaid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Parses the payment details and updates the UI to show the payment confirmation status.
     *
     * @param paymentDetails A JSON string containing the details of the payment response.
     */
    private void showDetails(String paymentDetails, double amountPaid) {
        TextView confirmationTextView = findViewById(R.id.confirmationTextView);
        try {
            JSONObject paymentDetailsJson = new JSONObject(paymentDetails);
            String state = paymentDetailsJson.getJSONObject("response").getString("state");
            if ("approved".equals(state)) {
                // Show confirmation message on successful payment
                confirmationTextView.setText("Payment confirmed");
                confirmationTextView.setVisibility(View.VISIBLE);

                String jobId = getIntent().getStringExtra("jobId");
                String jobTitle = getIntent().getStringExtra("jobTitle");
                String jobType = getIntent().getStringExtra("jobType");
                String employerId = getIntent().getStringExtra("employerUserId");
                String employeeId = getIntent().getStringExtra("employeeUserId");
                String today = getCurrentDate();

                // Save payment details
                savePaymentDetails(jobId, jobTitle, jobType, employerId, employeeId, today, amountPaid);
            } else {
                // Show failure message on unsuccessful payment
                confirmationTextView.setText("Payment failed");
                confirmationTextView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            confirmationTextView.setText("Error in payment details");
            confirmationTextView.setVisibility(View.VISIBLE);
        }
    }

    private void populatePaymentDetails(String jobTitle, String jobType, String employerUserId, String employeeUserId) {
        jobTitleTextView = findViewById(R.id.jobTitleTextView);
        jobTypeTextView = findViewById(R.id.jobTypeTextView);
        todaysDateTextView = findViewById(R.id.todaysDateTextView);
        employerNameTextView = findViewById(R.id.employerNameTextView);
        employeeDisplayNameTextView = findViewById(R.id.employeeDisplayNameTextView);
        employeeEmailTextView = findViewById(R.id.employeeEmailTextView);

        jobTitleTextView.setText(jobTitle);
        jobTypeTextView.setText(jobType);
        jobTitleTextView.setText(jobTitle);
        jobTypeTextView.setText(jobType);
        String today = getCurrentDate();
        todaysDateTextView.setText(today);
        retrieveEmployerDetails(employerUserId, employerNameTextView);
        retrieveEmployeeDetails(employeeUserId, employeeDisplayNameTextView, employeeEmailTextView);
    }

    private void retrieveEmployerDetails(String userId, final TextView textView){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String displayName = snapshot.child("displayName").getValue(String.class);
                    if (textView != null) {
                        textView.setText(displayName);
                    }
                } else {
                    // Handle the case where the user document doesn't exist
                    if (textView != null) {
                        textView.setText("User not found");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors in fetching user details
                if (textView != null) {
                    textView.setText("Error retrieving user details: " + error.getMessage());
                }
            }
        });
    }
    private void retrieveEmployeeDetails(String userId, final TextView textView, final TextView textView2){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String displayName = snapshot.child("displayName").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    if (textView != null && textView2 != null ) {
                        textView.setText(displayName);
                        textView2.setText(email);
                    }
                } else {
                    // Handle the case where the user document doesn't exist
                    if (textView != null && textView2 != null ) {
                        textView.setText("User not found");
                        textView2.setText("User not found");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors in fetching user details
                if (textView != null && textView2 != null ) {
                    textView.setText("Error retrieving user details: " + error.getMessage());
                    textView2.setText("Error retrieving user details: " + error.getMessage());
                }
            }
        });
    }
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(currentDate);
        return date;
    }

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

