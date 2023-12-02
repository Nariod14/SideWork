package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quickcashcsci3130g_11.databinding.ActivityPreferredEmployeesBinding;

public class PreferredEmployees extends BaseActivity {
    ActivityPreferredEmployeesBinding preferredEmployeesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferredEmployeesBinding = ActivityPreferredEmployeesBinding.inflate(getLayoutInflater());
        setContentView(preferredEmployeesBinding.getRoot());

        String activityTitle = getString(R.string.PREFERRED_EMPLOYEES);
        setToolbarTitle(activityTitle);
    }
}