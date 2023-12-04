package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Base activity class for common functionality and UI setup across different activities.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    protected TextView headerTextView;
    protected TextView subheaderTextView;
    protected FirebaseUser user;
    protected FirebaseAuth mAuth;
    protected AppPreferences appPreferences;

    /**
     * Sets the context for the frame layout.
     *
     * @param view to be displayed in.
     */
    @Override
    public void setContentView(View view){
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.menu_dashboard, null);
        FrameLayout container = drawerLayout.findViewById(R.id.frameLayout);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbarWidget);
        setSupportActionBar(toolbar);
        setAndClickBackButton();

        headerTextView = findViewById(R.id.headerTextView);
        subheaderTextView = findViewById(R.id.subHeaderTextView);

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        appPreferences = new AppPreferences(this);
        String userRole = appPreferences.getUserRole();
        customizeToolbarBasedOnRole(userRole);

        View headerMenu = navigationView.getHeaderView(0);
        TextView usernameTextView = headerMenu.findViewById(R.id.menu_profile_username);
        TextView emailTextView = headerMenu.findViewById(R.id.menu_profile_email);

        String[] profileInfo = getProfileInfo();
        if (profileInfo != null && profileInfo.length == 2) {
            String displayName = profileInfo[0];
            String email = profileInfo[1];
            usernameTextView.setText(displayName);
            emailTextView.setText(email);
        } else {
            // Handle the case where profile information is not available
            // TODO: Handle this error.
        }
    }

    /**
     * Sets the title for the toolbar and header text view.
     *
     * @param activityTitle The title to be set for the toolbar and header.
     */
    protected void setToolbarTitle (String activityTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(activityTitle);
            headerTextView.setText(activityTitle);
        }
    }
    /**
     * Sets the visibility of the back button in the toolbar.
     *
     * @param visible True if the back button should be visible, false otherwise.
     */
    protected void setBackButtonVisibility(boolean visible) {
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    /**
     * Sets the visibility of the subheader text view.
     *
     * @param visible True if the subheader text view should be visible, false otherwise.
     */
    protected void setSubheaderVisibility(boolean visible) {
        subheaderTextView = findViewById(R.id.subHeaderTextView);
        subheaderTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Sets the visibility of the header text view.
     *
     * @param visible True if the header text view should be visible, false otherwise.
     */
    protected void setHeaderVisibility(boolean visible) {
        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Sets the click listener for the back button to finish the current activity.
     */
    protected void setAndClickBackButton() {
        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton.getVisibility() == View.VISIBLE) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    /**
     * Retrieves the profile information of the current user.
     *
     * @return An array containing the display name and email of the user, or null if not available.
     */
    protected String[] getProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            return new String[]{displayName, email};
        }
        // if no profile of user, handle that scenario
        // TODO: Handle this error.
        return null;
    }

    /**
     * Navigates through the drawer menu.
     *
     * @param item to be selected on the menu.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.menu_switch_role) {
            String userRole = appPreferences.getUserRole();
            if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
                appPreferences.setUserRole(getString(R.string.ROLE_EMPLOYEE));
            } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
                appPreferences.setUserRole(getString(R.string.ROLE_EMPLOYER));
            }
            userRole = appPreferences.getUserRole();
            customizeToolbarBasedOnRole(userRole);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.menu_reputation) {

            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            String userRole = appPreferences.getUserRole();
            userRole = appPreferences.getUserRole();

            if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
                // Show a popup dialog to choose between employee and employer profile
                Intent intent = new Intent(getApplicationContext(), EmployerProfileActivity.class);

                // Pass the current user's UID and role to the profile activity
                String currentUserID = mUser.getUid();
                intent.putExtra("currentUserID", currentUserID);
                intent.putExtra("inputUID", currentUserID);
                intent.putExtra("userRole", "employer");
                startActivity(intent);

            } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
                // Show a popup dialog to choose between employee and employer profile
                Intent intent = new Intent(getApplicationContext(), EmployeeProfileActivity.class);

                // Pass the current user's UID and role to the profile activity
                String currentUserID = mUser.getUid();
                intent.putExtra("currentUserID", currentUserID);
                intent.putExtra("inputUID", currentUserID);
                intent.putExtra("userRole", "employer");
                startActivity(intent);
            }


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


    /**
     * Customizes the toolbar based on the user role.
     *
     * @param userRole of the user.
     */
    protected void customizeToolbarBasedOnRole(String userRole) {
        if (getString(R.string.ROLE_EMPLOYER).equals(userRole)) {
            subheaderTextView.setText(getString(R.string.TOOLBAR_EMPLOYER));
        } else if (getString(R.string.ROLE_EMPLOYEE).equals(userRole)) {
            subheaderTextView.setText(getString(R.string.TOOLBAR_EMPLOYEE));
        }
    }
}
