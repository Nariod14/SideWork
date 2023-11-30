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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private GoogleMap mMap;
    private MapView mapView;
    private TextView mEmailTextView;
    private TextView EmployeeTextView;
    private Button switch2EmployerButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private AppPreferences appPreferences;
    private LocationAccess locationAccess;

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView headerTextView;
    private TextView subheaderTextView;

    /**
     * This is the main activity of the Android application.
     * It serves as the entry point of the app and defines the initial user interface.
     * It will eventually be converted into the main activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferences = new AppPreferences(this);
        appPreferences.setUserRole(getString(R.string.ROLE_EMPLOYER));

        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbarWidget);
        setSupportActionBar(toolbar);

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        navigationView.bringToFront();
//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer,R.string.close_drawer);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        headerTextView = findViewById(R.id.headerTextView);
        subheaderTextView = findViewById(R.id.subHeaderTextView);

        String userRole = appPreferences.getUserRole();
        customizeToolbarBasedOnRole(userRole);
        customizeDashboardBasedOnRole(userRole);


        String[] profileInfo = this.getProfileInfo();
        if (profileInfo != null && profileInfo.length == 2) {
            String displayName = profileInfo[0];
            String email = profileInfo[1];

            String welcomeTitle = getString(R.string.WELCOME_MESSAGE, displayName);
            headerTextView.setText(welcomeTitle);

            this.showEmployeeMessage(email);
        } else {
            // Handle the case where profile information is not available
            // TODO: Handle this error.
        }
    }
        protected String[] getProfileInfo () {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            if (user != null) {
                String displayName = user.getDisplayName();
                String email = user.getEmail();
                return new String[]{displayName, email};
            }
            // if no profile of user, handle that scenario
            //TODO: Handle this error.
            return null;
        }

        /**
         * Display a welcome message for the employer using a Snackbar.
         */
        protected void showEmployeeMessage (String displayName){
            LinearLayout linearLayout = findViewById(R.id.eLayout);
            String userMessage = getString(R.string.EMPLOYER_MESSAGE, displayName);
            Snackbar userSnack = Snackbar.make(linearLayout, userMessage, Snackbar.LENGTH_SHORT);
            userSnack.show();
        }


        private void displayTotalIncome ( double totalIncome){
            TextView incomeTextView = findViewById(R.id.incomeTextView);
            incomeTextView.setText(String.format("%.2f", totalIncome));
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            if (!(this instanceof MainActivity)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                item.setVisible(false);
            }
        } else if (id == R.id.menu_switch_role) {
            String userRole = appPreferences.getUserRole();
            if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
                appPreferences.setUserRole(getString(R.string.ROLE_EMPLOYEE));
            } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
                appPreferences.setUserRole(getString(R.string.ROLE_EMPLOYER));
            }
            userRole = appPreferences.getUserRole();
            customizeToolbarBasedOnRole(userRole);
            customizeDashboardBasedOnRole(userRole);

        } else if (id == R.id.menu_reputation) {

        } else if (id == R.id.menu_location_settings) {

        } else if (id ==R.id.menu_report) {
            //TODO: do something with report or discard it.
        } else if (id == R.id.menu_logout) {
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();;
        }
        return true;
    }

    private void customizeToolbarBasedOnRole(String userRole) {
        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            subheaderTextView.setText(getString(R.string.TOOLBAR_EMPLOYER));
        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            subheaderTextView.setText(getString(R.string.TOOLBAR_EMPLOYEE));
        }
    }
    private void customizeDashboardBasedOnRole(String userRole) {
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
            dashboardButton2.setText(getString(R.string.PREFERRED_JOBS));
            dashboardButton3.setText(getString(R.string.ACCEPTED_JOBS));

            incomeHistoryLayout.setVisibility(View.VISIBLE);
        }
    }
}
