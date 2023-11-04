package com.example.quickcashcsci3130g_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Activity that handles PayPal payments.
 *
 * This activity facilitates PayPal payments, allowing users to enter a payment amount and initiate the payment process
 * through PayPal's SDK. It handles the PayPal payment callbacks and displays the payment confirmation status.
 */
public class PaymentsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_payment);

        amountEditText = findViewById(R.id.amountEditText);
        makePaymentButton = findViewById(R.id.makePaymentButton);

        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    /**
     * Initiates the payment process by collecting the payment details and starting the PayPal payment activity.
     */
    public void processPayment() {
        String amount = amountEditText.getText().toString();

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
                        showDetails(paymentDetails);
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
    private void showDetails(String paymentDetails) {
        TextView confirmationTextView = findViewById(R.id.confirmationTextView);
        try {
            JSONObject paymentDetailsJson = new JSONObject(paymentDetails);
            String state = paymentDetailsJson.getJSONObject("response").getString("state");
            if ("approved".equals(state)) {
                // Show confirmation message on successful payment
                confirmationTextView.setText("Payment confirmed");
                confirmationTextView.setVisibility(View.VISIBLE);
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
}

