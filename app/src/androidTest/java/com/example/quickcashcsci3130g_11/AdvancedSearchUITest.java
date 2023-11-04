package com.example.quickcashcsci3130g_11;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class AdvancedSearchUITest {

    private DatabaseReference mDatabase;
    private UiDevice device;

    @Rule
    public
    ActivityScenarioRule<AdvancedSearchActivity> mActivityRule =
            new ActivityScenarioRule<>(AdvancedSearchActivity.class);

    @Before
    public void setUp() throws IOException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        String uiHierarchy;
        device.dumpWindowHierarchy(System.out);



        // Set up the Firebase database
        FirebaseApp.initializeApp(getInstrumentation().getTargetContext());
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        // Sign in with the provided email and password
        FirebaseAuth.getInstance().signInWithEmailAndPassword("existing_email@example.com", "password")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Create a new job
                        Job job = new Job(
                                "12345", // Job ID
                                "Software Engineer", // Title
                                "Full-Time", // Job Type
                                "2023-11-05", // Date
                                "6", // Duration
                                "months", // Duration Type
                                "Urgent", // Urgency Type
                                "50000", // Salary
                                "USD", // Salary Type
                                "Location XYZ", // Location
                                "Description: This is a sample job.", // Description
                                "Employer 1" // Employer ID
                        );

                        // Submit the job to the database
                        DatabaseReference jobRef = mDatabase.push();
                        jobRef.setValue(job);
                    } else {
                        fail("Failed to sign in");
                    }
                });


        ActivityScenario.launch(AdvancedSearchActivity.class);
    }

    @Test
    public void testSearchByTitle() throws UiObjectNotFoundException {
        // Find and interact with UI elements
        UiObject titleEditText = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/titleEditText"));
        UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));

        // Enter the title
        titleEditText.setText("Software Engineer");

        // Click the search button
        searchButton.click();

        // Verify search results
        UiObject recyclerView = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/recyclerView"));

        // Check the number of items in the RecyclerView
        int itemCount = recyclerView.getChildCount();
        assertTrue(itemCount > 0);

        UiScrollable recyclerViewScroll = new UiScrollable(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/recyclerView"));
        // Scroll through the RecyclerView to load all items
        recyclerViewScroll.scrollForward();

        // Check if "Software Engineer" exists in any of the items
        boolean found = false;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            UiObject item = recyclerView.getChild(new UiSelector().index(i));
            String itemText = item.getText();

            if (itemText.contains("Software Engineer")) {
                found = true;
                break; // Stop searching once found
            }
        }

        assertTrue("Software Engineer is not found in the search results", found);
    }

    @Test
    public void testSearchByJobType() throws UiObjectNotFoundException {
        // Find and interact with UI elements
        UiObject jobTypeEditText = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/jobTypeEditText"));
        UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));

        // Enter the job type
        jobTypeEditText.setText("Full-Time");

        // Click the search button
        searchButton.click();

        // Verify search results
        // Implement your verification logic here
    }

    @Test
    public void testSearchByDate() throws UiObjectNotFoundException {
        // Find and interact with UI elements
        UiObject dateEditText = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/dateEditText"));
        UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/searchButton"));

        // Enter the date
        dateEditText.setText("2023-11-05");

        // Click the search button
        searchButton.click();

        // Verify search results
        // Implement your verification logic here
    }

    @Test
    public void testSearchByDuration() throws UiObjectNotFoundException {
        // Find and interact with UI elements
        UiObject durationEditText = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/durationEditText"));
        UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/searchButton"));

        // Enter the duration
        durationEditText.setText("6");

        // Click the search button
        searchButton.click();

        // Verify search results
        // Implement your verification logic here
    }

    @Test
    public void testSearchByUrgency() throws UiObjectNotFoundException {
        // Find and interact with UI elements
        UiObject urgencyEditText = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/urgencyEditText"));
        UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/searchButton"));

        // Enter the urgency
        urgencyEditText.setText("Urgent");

        // Click the search button
        searchButton.click();

        // Verify search results
        // Implement your verification logic here
    }

    @Test
    public void testSearchBySalary() throws UiObjectNotFoundException {
        // Find and interact with UI elements
        UiObject salaryEditText = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/salaryEditText"));
        UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g_11:id/searchButton"));

        // Enter the salary
        salaryEditText.setText("50000");

        // Click the search button
        searchButton.click();

        // Verify search results
        // Implement your verification logic here
    }


    @After
    public void tearDown() {
        // Delete the test job from the database
        mDatabase.orderByChild("jobId").equalTo("12345")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                            jobSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        fail("Failed to delete test job");
                    }
                });
    }




}
