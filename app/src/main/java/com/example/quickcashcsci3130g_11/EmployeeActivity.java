package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EmployeeActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MapView mapView;
    private TextView mEmailTextView;
    private TextView EmployeeTextView;
    private Button switch2EmployerButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private LocationAccess locationAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationAccess = new LocationAccess(this);
        locationAccess.requestLocationPermission();
        setContentView(R.layout.activity_employee);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Button AvailableJobs = (Button) findViewById(R.id.availableJobsButton);
        Button AppliedJobs = (Button) findViewById(R.id.appliedJob);
        Button AcceptedJobs = (Button) findViewById(R.id.acceptedJobsButton);
        Button Report = (Button) findViewById(R.id.employeeReportButton);

        Button logout=(Button)findViewById(R.id.logout);

        switch2EmployerButton = findViewById(R.id.switch2EmployerButton);
        mEmailTextView = findViewById(R.id.emailTextView);

        String email = this.showProfileInfo();
        this.showEmployeeMessage(email);
        this.switch2Employer();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        AvailableJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });

        AppliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here

            }
        });

        AcceptedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here

            }
        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do what you want here
            }
        });
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
        user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mEmailTextView.setText(email);
            return email;
        }
//        if no profile of user, handle that scenario
        return null;
    }

    protected void showEmployeeMessage(String email) {
        ConstraintLayout constraintLayout = findViewById(R.id.eLayout);
        String employeeMessage = getString(R.string.EMPLOYEE_MESSAGE, email);

        Snackbar employeeSnack = Snackbar.make(constraintLayout, employeeMessage, Snackbar.LENGTH_SHORT);
        employeeSnack.show();
    }
    protected void switch2Employer() {
        switch2EmployerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(EmployeeActivity.this, EmployerActivity.class);
                startActivity(intent);

            }
        });

    }


}