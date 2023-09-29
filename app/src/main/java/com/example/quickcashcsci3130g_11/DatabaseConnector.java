package com.example.quickcashcsci3130g_11;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseConnector {

    private final DatabaseReference mDatabase;

    public DatabaseConnector() {
        // Get a reference to the Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void writeData(String userId, String data) {
        // Write data to the database
        mDatabase.child("users").child(userId).setValue(data);
    }

    public void readData(String userId) {
        // Read data from the database
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                // Do something with the data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }

}