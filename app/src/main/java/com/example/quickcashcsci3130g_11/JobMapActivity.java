package com.example.quickcashcsci3130g_11;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class JobMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "JobMapActivity";
    private GoogleMap mMap;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_map_activity);

        initializeMap();

        // Initialize the Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        // Fetch and display all job postings on the map
        fetchAndDisplayAllJobPostingsOnMap();
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapContainer, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void fetchAndDisplayAllJobPostingsOnMap() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Job> jobList = new ArrayList<>();

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null && job.getLocationString() != null) {
                        // Convert locationString to Location
                        job.setLocation(convertStringToLocation(job.getLocationString()));
                        jobList.add(job);
                        displayJobOnMap(job);
                    }
                }

                // After adding all job markers, adjust camera to show all markers
                if (!jobList.isEmpty() && mMap != null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Job job : jobList) {
                        LatLng jobLocation = new LatLng(job.getLocation().getLatitude(), job.getLocation().getLongitude());
                        builder.include(jobLocation);
                    }
                    LatLngBounds bounds = builder.build();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50)); // '50' is padding
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void displayJobOnMap(Job job) {
        if (mMap != null && job.getLocation() != null) {
            LatLng jobLocation = new LatLng(job.getLocation().getLatitude(), job.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(jobLocation).title(job.getTitle()));
        }
    }

    private Location convertStringToLocation(String locationString) {
        Location location = new Location("provider"); // You can provide any provider name

        // Sample locationString: "Latitude: 37.7749, Longitude: -122.4194"
        String[] parts = locationString.split(", ");
        if (parts.length == 2) {
            // Extract latitude and longitude values
            String latitudeString = parts[0].substring(parts[0].indexOf(":") + 2);
            String longitudeString = parts[1].substring(parts[1].indexOf(":") + 2);

            try {
                // Parse latitude and longitude as doubles
                double latitude = Double.parseDouble(latitudeString);
                double longitude = Double.parseDouble(longitudeString);

                // Set the values to the Location object
                location.setLatitude(latitude);
                location.setLongitude(longitude);

                return location;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if parsing fails
    }
}