package com.example.quickcashcsci3130g_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcashcsci3130g_11.databinding.ActivityDashboardBinding;
import com.example.quickcashcsci3130g_11.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private AppPreferences appPreferences;
    private LocationAccess locationAccess;
    ActivityMainBinding activityMainBinding;
    /**
     * This is the main activity of the Android application.
     * It serves as the entry point of the app and defines the initial user interface.
     * It will eventually be converted into the main activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = activityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        appPreferences = new AppPreferences(this);
        String userRole = appPreferences.getUserRole();

        String[] profileInfo = getProfileInfo();
        String displayName = profileInfo[0];
        String dashboardWelcome = getString(R.string.WELCOME_MESSAGE, displayName);
        setToolbarTitle(dashboardWelcome);
        setBackButtonVisibility(false);
        showUserMessage(displayName, userRole);
        customizeDashboardBasedOnRole(userRole);

    }

    /**
     * Display a welcome message for the employer using a Snackbar.
     */
    protected void showUserMessage (String displayName, String userRole){
        LinearLayout linearLayout = findViewById(R.id.eLayout);
        String userMessage = "";
        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            userMessage = getString(R.string.EMPLOYER_MESSAGE, displayName);
        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            userMessage = getString(R.string.EMPLOYEE_MESSAGE, displayName);
        }
        Snackbar userSnack = Snackbar.make(linearLayout, userMessage, Snackbar.LENGTH_SHORT);
        userSnack.show();
    }


    private void displayTotalIncome ( double totalIncome){
        TextView incomeTextView = findViewById(R.id.incomeTextView);
        incomeTextView.setText(String.format("%.2f", totalIncome));
    }

    public void customizeDashboardBasedOnRole(String userRole) {
        Button mainButton = findViewById(R.id.dashboardMainButton);
        Button dashboardButton1 = findViewById(R.id.dashboardButton1);
        Button dashboardButton2 = findViewById(R.id.dashboardButton2);
        Button dashboardButton3 = findViewById(R.id.dashboardButton3);
        LinearLayout incomeHistoryLayout = findViewById(R.id.incomeHistory);

        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            mainButton.setText(getString(R.string.SUBMIT_JOB));
            mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SubmitJobActivity.class);
                    startActivity(intent);
                }
            });

            dashboardButton1.setText(getString(R.string.POSTED_JOBS));
            dashboardButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, JobPostingsActivity.class);
                    startActivity(intent);
                }
            });
            dashboardButton2.setText(getString(R.string.PREFERRED_EMPLOYEES));
            dashboardButton3.setText(getString(R.string.ACCEPTED_JOBS));
            incomeHistoryLayout.setVisibility(View.GONE);
        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            mainButton.setText(getString(R.string.SEARCH_JOBS));
            mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, JobSearchActivity.class);
                    startActivity(intent);
                }
            });
            dashboardButton1.setText(getString(R.string.AVAILABLE_JOBS));
            dashboardButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, JobPostingsActivity.class);
                    startActivity(intent);
                }
            });
            dashboardButton2.setText(getString(R.string.PREFERRED_JOBS));
            dashboardButton3.setText(getString(R.string.ACCEPTED_JOBS));
            incomeHistoryLayout.setVisibility(View.VISIBLE);
        }
    }
}
