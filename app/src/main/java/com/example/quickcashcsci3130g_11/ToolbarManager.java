package com.example.quickcashcsci3130g_11;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ToolbarManager extends AppCompatActivity {

    private TextView headerTextView;
    private TextView subheaderTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar(@LayoutRes int layoutResID, int toolbarResId) {
        setContentView(layoutResID);

        Toolbar toolbar = findViewById(toolbarResId);
        setSupportActionBar(toolbar);

        // Initialize TextViews after calling setContentView
        headerTextView = findViewById(R.id.headerTextView);
        subheaderTextView = findViewById(R.id.subHeaderTextView);

//        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//        ActionBarDrawerToggle actionBarDrawerToggle =
//                new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        // Call the method to set up the menu button
//        setupMenuButton(drawerLayout);

        // Customize toolbar based on user role
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userRole = preferences.getString("userRole", "");
        customizeToolbarBasedOnRole(toolbar, userRole);
    }

    private void customizeToolbarBasedOnRole(Toolbar toolbar, String userRole) {
        // Customize toolbar based on user role
        if ("employer".equals(userRole)) {
            getSupportActionBar().setTitle("Employer Profile");
            subheaderTextView.setText("Employer Dashboard");
        } else if ("employee".equals(userRole)) {
            getSupportActionBar().setTitle("Employee Profile");
            subheaderTextView.setText("Employee Dashboard");
        }
    }

    protected void updateToolbar(String title) {
        getSupportActionBar().setTitle(title);
        headerTextView.setText(title);
    }

//    private void setupMenuButton(final DrawerLayout drawerLayout) {
//        ImageButton menuButton = findViewById(R.id.menuButton);
//        menuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.END);
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        // Handle other menu item clicks if needed
//        return super.onOptionsItemSelected(item);
//    }
}