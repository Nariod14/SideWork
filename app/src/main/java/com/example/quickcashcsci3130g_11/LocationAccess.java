package com.example.quickcashcsci3130g_11;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationAccess {
    private double latitude;
    private double longitude;
    private Activity mainActivity;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseReference databaseReference;

    public LocationAccess(Activity mainActivity) {
        this.mainActivity = mainActivity;
        // Default constructor required for Firebase
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity);
        databaseReference = FirebaseDatabase.getInstance().getReference("Location_Access");
    }

    public void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            updateLocation();
        }
    }

    public void updateLocation() {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(mainActivity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            // Store the new location in Firebase
                            databaseReference.setValue(LocationAccess.this); // Set the current LocationAccess object
                            Toast.makeText(mainActivity, "Location updated in Firebase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
