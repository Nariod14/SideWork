package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.quickcashcsci3130g_11.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity {
    private LocationAccess locationAccess;
    ActivityMainBinding activityMainBinding;
    private IncomeManager incomeManager; // Create an instance of IncomeManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        DashboardCustomizer dashboardCustomizer = new DashboardCustomizer();

        appPreferences = new AppPreferences(this);
        String userRole = appPreferences.getUserRole();

        String[] profileInfo = getProfileInfo();
        String displayName = profileInfo[0];
        String dashboardWelcome = getString(R.string.WELCOME_MESSAGE, displayName);
        setToolbarTitle(dashboardWelcome);
        setBackButtonVisibility(false);
        showUserMessage(displayName, userRole);

        dashboardCustomizer.customizeDashboardBasedOnRole(this, userRole);

        // Create an instance of IncomeManager
        incomeManager = new IncomeManager();
        incomeManager.retrieveAndOrganizePayments(this, userRole);
    }

    protected void showUserMessage(String displayName, String userRole) {
        LinearLayout linearLayout = findViewById(R.id.eLayout);
        String userMessage = "";
        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            userMessage = getString(R.string.EMPLOYER_MESSAGE, displayName);
        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            userMessage = getString(R.string.EMPLOYEE_MESSAGE, displayName);
        }
        Snackbar userSnack = Snackbar.make(linearLayout, userMessage, BaseTransientBottomBar.LENGTH_SHORT);
        userSnack.show();
    }
}
