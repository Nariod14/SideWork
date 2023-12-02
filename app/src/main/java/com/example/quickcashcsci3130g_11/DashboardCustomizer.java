package com.example.quickcashcsci3130g_11;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardCustomizer {
    public void customizeDashboardBasedOnRole(Context context, String userRole) {
        Button mainButton = ((AppCompatActivity) context).findViewById(R.id.dashboardMainButton);
        Button dashboardButton1 = ((AppCompatActivity) context).findViewById(R.id.dashboardButton1);
        Button dashboardButton2 = ((AppCompatActivity) context).findViewById(R.id.dashboardButton2);
        Button dashboardButton3 = ((AppCompatActivity) context).findViewById(R.id.dashboardButton3);
        LinearLayout incomeHistoryLayout = ((AppCompatActivity) context).findViewById(R.id.incomeHistory);
        Button incomeHistoryButton = ((AppCompatActivity)context).findViewById(R.id.incomeHistoryButton);



        if (context.getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            mainButton.setText(context.getString(R.string.SUBMIT_JOB));
            mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SubmitJobActivity.class);
                    context.startActivity(intent);
                }
            });

            dashboardButton1.setText(context.getString(R.string.POSTED_JOBS));
            dashboardButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobPostingsActivity.class);
                    context.startActivity(intent);
                }
            });
            dashboardButton2.setText(context.getString(R.string.JOBS_IN_PROGRESS));
            dashboardButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobsInProgressActivity.class);
                    context.startActivity(intent);
                }
            });
            dashboardButton3.setText(context.getString(R.string.PREFERRED_EMPLOYEES));
            dashboardButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PreferredEmployees.class);
                    context.startActivity(intent);
                }
            });
            incomeHistoryLayout.setVisibility(View.GONE);
        } else if (context.getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            mainButton.setText(context.getString(R.string.SEARCH_JOBS));
            mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobSearchActivity.class);
                    context.startActivity(intent);
                }
            });
            dashboardButton1.setText(context.getString(R.string.AVAILABLE_JOBS));
            dashboardButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobPostingsActivity.class);
                    context.startActivity(intent);
                }
            });
            dashboardButton2.setText(context.getString(R.string.PREFERRED_JOBS));
            dashboardButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PreferredJobs.class);
                    context.startActivity(intent);
                }
            });
            dashboardButton3.setText(context.getString(R.string.ACCEPTED_JOBS));
            dashboardButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AcceptedJobsActivity.class);
                    context.startActivity(intent);
                }
            });
            incomeHistoryLayout.setVisibility(View.VISIBLE);
            incomeHistoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, IncomeHistory.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}

