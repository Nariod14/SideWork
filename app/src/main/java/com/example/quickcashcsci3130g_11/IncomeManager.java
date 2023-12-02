package com.example.quickcashcsci3130g_11;

import static org.apache.http.client.utils.DateUtils.parseDate;

import android.content.Context;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class IncomeManager {

    private static ChartManager chartManager;

    public IncomeManager() {
        chartManager = new ChartManager();
    }

    public static void retrieveAndOrganizePayments(Context context, String userRole) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && context.getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            retrievePaymentsForEmployee(context, currentUser.getUid());
        }
    }

    private static void retrievePaymentsForEmployee(Context context, String employeeId) {
        DatabaseReference paymentsRef = FirebaseDatabase.getInstance().getReference().child("payments");

        paymentsRef.orderByChild("employeeId").equalTo(employeeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Payment> payments = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Payment payment = snapshot.getValue(Payment.class);
                    Log.e("Payment", String.valueOf(payment));
                    payments.add(payment);
                }

                // Now 'payments' list contains all payments for the employee
                Log.e("PaymentNumber", String.valueOf(payments.size()));
                calculateTotalIncome(context, payments);
                organizeData(context, payments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private static void calculateTotalIncome(Context context, List<Payment> payments) {
        double totalIncome = 0.0;
        for (Payment payment : payments) {
            totalIncome += payment.getAmountPaid();
        }
        displayTotalIncome(context, totalIncome);
    }

    private static void displayTotalIncome(Context context, double totalIncome) {
        TextView incomeTextView = ((AppCompatActivity) context).findViewById(R.id.incomeTextView);
        String displayedIncome = ("$" + String.format("%.2f", totalIncome));
        incomeTextView.setText(displayedIncome);
    }

    private static void organizeData(Context context, List<Payment> payments) {
        Map<String, Float> monthlyIncomes = new HashMap<>();
        Map<String, Integer> jobCounts = new HashMap<>();

        for (Payment payment : payments) {
            Date date = parseDate(payment.getDatePaid());
            String monthYear = getMonthYearFromDate(date);

            float currentIncome = monthlyIncomes.getOrDefault(monthYear, 0f);
            currentIncome += payment.getAmountPaid();
            monthlyIncomes.put(monthYear, currentIncome);

            int currentJobCount = jobCounts.getOrDefault(monthYear, 0);
            currentJobCount++;
            jobCounts.put(monthYear, currentJobCount);
        }

        chartManager.displayLineChart(context, monthlyIncomes);
        chartManager.displayBarGraph(context, jobCounts);
    }

    private static String getMonthYearFromDate(Date date) {
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return monthYearFormat.format(date);
    }

    private static Date parseDate(String datePaid) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(datePaid);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }
}
