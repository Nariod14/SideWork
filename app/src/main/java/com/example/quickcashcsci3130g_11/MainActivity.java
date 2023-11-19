package com.example.quickcashcsci3130g_11;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static boolean isLoggedIn() {
        return true;
    }

    public static Object getIncome() {
        return true;
    }

    public static Collection<Object> getEarningsHistory() {
        return Collections.singleton(true);
    }

    public static Object getJobHistory() {
        return true;
    }

    /**
     * This is the main activity of the Android application.
     * It serves as the entry point of the app and defines the initial user interface.
     * It will eventually be converted into the main activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
