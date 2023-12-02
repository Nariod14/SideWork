package com.example.quickcashcsci3130g_11;




import android.os.Bundle;

import com.example.quickcashcsci3130g_11.databinding.ActivityIncomeHistoryBinding;

/**
 * Activity for displaying the income history of the user.
 */
public class IncomeHistory extends BaseActivity {

    ActivityIncomeHistoryBinding incomeHistoryBinding;

    /**
     * Called when the activity is first created. Initializes UI elements and retrieves income/payment history.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        incomeHistoryBinding = ActivityIncomeHistoryBinding.inflate(getLayoutInflater());
        setContentView(incomeHistoryBinding.getRoot());

        String activityTitle = getString(R.string.INCOME_HISTORY);
        setToolbarTitle(activityTitle);

        appPreferences = new AppPreferences(this);
        String userRole = appPreferences.getUserRole();

        IncomeManager.retrieveAndOrganizePayments(this, userRole);
    }
}