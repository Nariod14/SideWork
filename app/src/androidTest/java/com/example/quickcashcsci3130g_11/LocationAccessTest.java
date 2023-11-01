package com.example.quickcashcsci3130g_11;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LocationAccessTest {
    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://csci3130-fall2023-a2-8bc9b-default-rtdb.firebaseio.com/");
        database.setPersistenceEnabled(true); // Enable Firebase Database persistence for offline testing
        databaseReference = database.getReference("test_location");
    }

    @After
    public void tearDown() {
        // Clean up any test data
        databaseReference.removeValue();
    }

    @Test
    public void testLocationStorage() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocationAccess storedLocation = dataSnapshot.getValue(LocationAccess.class);
                assertEquals(123.456, storedLocation.getLatitude(), 0.001);
                assertEquals(789.012, storedLocation.getLongitude(), 0.001);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }
}