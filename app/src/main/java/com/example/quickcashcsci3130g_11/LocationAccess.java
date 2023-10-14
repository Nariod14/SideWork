package com.example.quickcashcsci3130g_11;

public class LocationAccess {
    private double latitude;
    private double longitude;

    public LocationAccess() {
    }

    public LocationAccess(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

