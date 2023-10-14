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
public class LocationAccessTest{

    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://csci3130-fall2023-a2-8bc9b-default-rtdb.firebaseio.com/");
        database.setPersistenceEnabled(true); // Enable Firebase Database persistence for offline testing
        databaseReference = database.getReference("test_location");
    }

    @After
    public void tearDown() {
        databaseReference.removeValue();
    }

    @Test
    public void testLocationStorage() {
        double testLatitude = 123.456;
        double testLongitude = 789.012;

        LocationAccess testLocation = new LocationAccess(testLatitude, testLongitude);

        databaseReference.setValue(testLocation);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocationAccess storedLocation = dataSnapshot.getValue(LocationAccess.class);
                assertEquals(testLatitude, storedLocation.getLatitude(), 0.001); // You can adjust the delta value
                assertEquals(testLongitude, storedLocation.getLongitude(), 0.001); // based on your expected precision
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
