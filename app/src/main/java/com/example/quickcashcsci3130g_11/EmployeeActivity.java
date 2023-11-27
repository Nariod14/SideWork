package com.example.quickcashcsci3130g_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private TextView mDisplaNameTextView;
    private TextView mEmployeeTextView;
    private Button mSwitch2EmployerButton;
    private Button mRateEmployerButton;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private LocationAccess mLocationAccess;

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferences = new AppPreferences(this);
        appPreferences.setUserRole("employee");


        mLocationAccess = new LocationAccess(this);
        mLocationAccess.requestLocationPermission();

        setContentView(R.layout.activity_employee);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button availableJobsButton = findViewById(R.id.availableJobsButton);
        Button appliedJobButton = findViewById(R.id.appliedJob);
        Button acceptedJobsButton = findViewById(R.id.acceptedJobsButton);
        Button reportButton = findViewById(R.id.employeeReportButton);
        Button logoutButton = findViewById(R.id.logout);
        mSwitch2EmployerButton = findViewById(R.id.switch2EmployerButton);
        mDisplaNameTextView = findViewById(R.id.displayNameTextView);
        mEmployeeTextView = findViewById(R.id.employeeTextView);
        mRateEmployerButton = findViewById(R.id.rateEmployerButton);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        if (mEmployeeTextView.getText().equals("Employee Profile")) {
            mSwitch2EmployerButton.setVisibility(View.VISIBLE);
        } else {
            mSwitch2EmployerButton.setVisibility(View.GONE);
        }

        Button myProfileButton = findViewById(R.id.myProfileButton);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a popup dialog to choose between employee and employer profile
                Intent intent = new Intent(EmployeeActivity.this, EmployeeProfileActivity.class);

                // Pass the current user's UID and role to the profile activity
                String currentUserID = mUser.getUid();
                intent.putExtra("currentUserID", currentUserID);
                intent.putExtra("inputUID", currentUserID);
                intent.putExtra("userRole", "employee");

                startActivity(intent);
            }
        });



        if (mUser != null) {
            String email = showProfileInfo();
            showEmployeeMessage(email);
            switch2Employer();
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            availableJobsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do what you want here
                }
            });

            appliedJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do what you want here
                }
            });

            acceptedJobsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do what you want here
                }
            });

            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do what you want here
                }
            });

            if (mUser != null) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String role = snapshot.child("role").getValue(String.class);
                            mEmployeeTextView.setText(role + " Profile");

                            if (role.equals("employee")) {
                                mRateEmployerButton.setVisibility(View.VISIBLE);
                                mRateEmployerButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Open the rating activity for the employer
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // You can add job markers here, for example:
        // Replace these with actual job locations
        LatLng jobLocation1 = new LatLng(40.7128, -74.0060);
        LatLng jobLocation2 = new LatLng(34.0522, -118.2437);

        // Add markers for job locations
        mMap.addMarker(new MarkerOptions().position(jobLocation1).title("Job Title 1"));
        mMap.addMarker(new MarkerOptions().position(jobLocation2).title("Job Title 2"));

        // Move camera to the first marker
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jobLocation1));
    }

    protected String showProfileInfo() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            String displayName = mUser.getDisplayName();
            mDisplaNameTextView.setText(displayName);
            return displayName;
        }
        // if no profile of user, handle that scenario
        //TODO: Handle this error.
        return null;
    }

    protected void showEmployeeMessage(String displayName) {
        ConstraintLayout constraintLayout = findViewById(R.id.eLayout);
        String employeeMessage = getString(R.string.EMPLOYEE_MESSAGE, displayName);
        Snackbar employeeSnack = Snackbar.make(constraintLayout, employeeMessage, Snackbar.LENGTH_SHORT);
        employeeSnack.show();
    }

    protected void switch2Employer() {
        mSwitch2EmployerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferences.setUserRole("employer");
                Intent intent = new Intent(EmployeeActivity.this, EmployerActivity.class);
                startActivity(intent);
            }
        });
    }
}