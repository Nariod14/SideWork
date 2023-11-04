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

/**
 * This class provides functionality to request and update location information, including handling
 * location permissions and updating the location in Firebase.
 */
public class LocationAccess {
    private double latitude;
    private double longitude;
    private Activity mainActivity;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseReference databaseReference;

    /**
     * Constructs a LocationAccess object with the provided main activity.
     *
     * @param mainActivity The main activity to request location permissions and access location data.
     */
    public LocationAccess(Activity mainActivity) {
        this.mainActivity = mainActivity;
        // Default constructor required for Firebase
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity);
        databaseReference = FirebaseDatabase.getInstance().getReference("Location_Access");
    }

    /**
     * Request location permission from the user.
     * If permission is not granted, it will request permission from the user.
     * If permission is granted, it will update the location information.
     */
    public void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            updateLocation();
        }
    }

    /**
     * Updates the latitude and longitude with the user's current location and stores it in Firebase.
     * It also displays a toast message to notify the user.
     */
    public void updateLocation() {
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    /**
     * Get the latitude of the user's current location.
     *
     * @return The latitude value.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude of the user's current location.
     *
     * @return The longitude value.
     */
    public double getLongitude() {
        return longitude;
    }
}
