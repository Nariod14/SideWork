package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quickcashcsci3130g_11.databinding.ActivityPreferredJobsBinding;

public class PreferredJobs extends BaseActivity {
    ActivityPreferredJobsBinding preferredJobsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferredJobsBinding = ActivityPreferredJobsBinding.inflate(getLayoutInflater());
        setContentView(preferredJobsBinding.getRoot());

        String activityTitle = getString(R.string.PREFERRED_JOBS);
        setToolbarTitle(activityTitle);
    }
}