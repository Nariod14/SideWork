package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collection;
import java.util.Collections;

public class MainActivity extends ToolbarManager {
    private GoogleMap mMap;
    private MapView mapView;
    private TextView mEmailTextView;
    private TextView EmployeeTextView;
    private Button switch2EmployerButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private LocationAccess locationAccess;

    public static boolean isLoggedIn() {
        return true;
    }

    public static Object getIncome() {
        return true;
    }

    public static Collection<Object> getEarningsHistory() {
        return Collections.singleton(true);
    }

    public static Object getJobHistory() {
        return true;
    }

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     * This is the main activity of the Android application.
     * It serves as the entry point of the app and defines the initial user interface.
     * It will eventually be converted into the main activity.
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            setupToolbar(R.layout.activity_main, R.id.toolbarWidget);

            String[] profileInfo = this.getProfileInfo();
            if (profileInfo != null && profileInfo.length == 2) {
                String displayName = profileInfo[0];
                String email = profileInfo[1];

                String welcomeTitle = getString(R.string.WELCOME_MESSAGE, displayName);
                updateToolbar(welcomeTitle);

                this.showEmployeeMessage(email);
            } else {
                // Handle the case where profile information is not available
                // TODO: Handle this error.
            }
        }

    protected String[] getProfileInfo() {
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

    protected void showEmployeeMessage(String displayName) {
        LinearLayout linearLayout = findViewById(R.id.eLayout);
        String employeeMessage = getString(R.string.EMPLOYEE_MESSAGE, displayName);
        Snackbar employeeSnack = Snackbar.make(linearLayout, employeeMessage, Snackbar.LENGTH_SHORT);
        employeeSnack.show();
    }
}

