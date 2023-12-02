package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quickcashcsci3130g_11.databinding.ActivityJobsInProgressBinding;

public class JobsInProgressActivity extends BaseActivity {
    ActivityJobsInProgressBinding jobsInProgressBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobsInProgressBinding = ActivityJobsInProgressBinding.inflate(getLayoutInflater());
        setContentView(jobsInProgressBinding.getRoot());

        String activityTitle = getString(R.string.JOBS_IN_PROGRESS);
        setToolbarTitle(activityTitle);
    }
}