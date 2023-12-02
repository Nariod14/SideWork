package com.example.quickcashcsci3130g_11;




import android.os.Bundle;

import com.example.quickcashcsci3130g_11.databinding.ActivityIncomeHistoryBinding;

public class IncomeHistory extends BaseActivity {

    ActivityIncomeHistoryBinding incomeHistoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        incomeHistoryBinding = ActivityIncomeHistoryBinding.inflate(getLayoutInflater());
        setContentView(incomeHistoryBinding.getRoot());

        String activityTitle = getString(R.string.INCOME_HISTORY);
        setToolbarTitle(activityTitle);

        IncomeManager incomeManager = new IncomeManager();

        appPreferences = new AppPreferences(this);
        String userRole = appPreferences.getUserRole();

        incomeManager.retrieveAndOrganizePayments(this, userRole);
    }
}