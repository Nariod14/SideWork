package com.example.quickcashcsci3130g_11;

import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface Income {
    void onPaymentsLoaded(List<Payment> payments);
    void onError(DatabaseError databaseError);
}
