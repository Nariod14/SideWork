package com.example.quickcashcsci3130g_11;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The DatabaseConnector class provides methods for reading and writing data to a Firebase Realtime Database.
 * It allows users to store and retrieve data associated with specific user IDs.
 */
public class DatabaseConnector {

    private final DatabaseReference mDatabase;

    /**
     * Initializes a new instance of the DatabaseConnector class.
     * It gets a reference to the Firebase Realtime Database.
     */
    public DatabaseConnector() {
        // Get a reference to the Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Writes the provided data to the Firebase Realtime Database under a specific user ID.
     *
     * @param userId The user ID associated with the data.
     * @param data   The data to be written to the database.
     */
    public void writeData(String userId, String data) {
        // Write data to the database
        mDatabase.child("users").child(userId).setValue(data);
    }

    /**
     * Reads data from the Firebase Realtime Database for a specific user ID.
     *
     * @param userId The user ID for which data should be read.
     */
    public void readData(String userId) {
        // Read data from the database
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * This method is called when data is successfully retrieved from the Firebase Realtime Database.
             *
             * @param snapshot A DataSnapshot containing the retrieved data.
             *                You can use snapshot.getValue() to access the data.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getValue(String.class);
            }

            /**
             * This method is called when there is an error while reading data from the Firebase Realtime Database.
             *
             * @param error A DatabaseError object representing the encountered error.
             *              You can inspect the error for details on what went wrong.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }

}