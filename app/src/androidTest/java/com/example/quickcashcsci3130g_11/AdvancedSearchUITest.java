package com.example.quickcashcsci3130g_11;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AdvancedSearchUITest {

    private UiDevice mDevice;
    private DatabaseReference mDatabase;

    @Rule
    public ActivityScenarioRule<AdvancedSearchActivity> mActivityRule = new ActivityScenarioRule<>(AdvancedSearchActivity.class);

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        ActivityScenario.launch(AdvancedSearchActivity.class);



        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg("com.example.quickcashcsci3130g_11").depth(0)), 5000);

        // Initialize the Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");

        // Create sample jobs in the Firebase database
        String jobId1 = mDatabase.push().getKey();
        Job job1 = new Job(jobId1, "Test Job 1", "Test Job Type 1", "2023-11-30", "1 month", "days", "High", "$1000", "CAD", "Halifax", "Test description 1", "employerId1");
        mDatabase.child(job1.getJobId()).setValue(job1);

        String jobId2 = mDatabase.push().getKey();
        Job job2 = new Job(jobId2, "Test Job 2", "Test Job Type 2", "2023-12-01", "2 months", "weeks", "Medium", "$2000", "CAD", "Toronto", "Test description 2", "employerId2");
        mDatabase.child(job2.getJobId()).setValue(job2);

        String jobId3 = mDatabase.push().getKey();
        Job job3 = new Job(jobId3, "Test Job 3", "Test Job Type 3", "2023-12-02", "3 months", "months", "Low", "$3000", "CAD", "Vancouver", "Test description 3", "employerId3");
        mDatabase.child(job3.getJobId()).setValue(job3);

    }

    @After
    public void tearDown() {
        // Delete the sample jobs from the Firebase database

        mDatabase.orderByChild("title").equalTo("TestApply").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    jobSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("DELETION FAILED");
            }
        });

        mDatabase.orderByChild("title").equalTo("Test Job 1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    jobSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("DELETION FAILED");
            }
        });
        mDatabase.orderByChild("title").equalTo("Test Job 2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    jobSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("DELETION FAILED");
            }
        });
        mDatabase.orderByChild("title").equalTo("Test Job 3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    jobSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("DELETION FAILED");
            }
        });
    }

    @Test
    public void testSearchByTitle() throws Exception {

        // Enter the search criteria
        UiObject titleEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/titleEditText"));
        titleEditText.setText("Test Job 1");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the search results contain the sample job
        UiObject jobTitle = mDevice.findObject(new UiSelector().text("Test Job 1"));
        assertTrue(jobTitle.exists());
    }

    @Test
    public void testSearchByJobType() throws Exception {
        // Enter the search criteria
        UiObject jobTypeEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/jobTypeEditText"));
        jobTypeEditText.setText("Test Job Type 1");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the search results contain the sample job
        UiObject jobTitle = mDevice.findObject(new UiSelector().text("Test Job 1"));
        assertTrue(jobTitle.exists());
    }

    @Test
    public void testSearchByDate() throws Exception {

        // Enter the search criteria
        UiObject dateEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/dateEditText"));
        dateEditText.setText("2023-12-01");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the search results contain the sample job
        UiObject jobTitle = mDevice.findObject(new UiSelector().text("Test Job 2"));
        assertTrue(jobTitle.exists());
    }

    @Test
    public void testSearchByDuration() throws Exception {

        // Enter the search criteria
        UiObject durationEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/durationEditText"));
        durationEditText.setText("1 month");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the search results contain the sample job
        UiObject jobTitle = mDevice.findObject(new UiSelector().text("Test Job 1"));
        assertTrue(jobTitle.exists());
    }


    @Test
    public void testSearchByUrgency() throws Exception {

        // Enter the search query for urgency
        UiObject urgencyEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/urgencyEditText"));
        urgencyEditText.setText("High");

        // Click the search button
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the correct jobs are displayed in the search results
        UiObject jobTitle = mDevice.findObject(new UiSelector().text("Test Job 1"));
        assertTrue(jobTitle.exists());
        UiObject jobUrgency = mDevice.findObject(new UiSelector().text("High"));
        assertTrue(jobUrgency.exists());
    }

    @Test
    public void testSearchBySalary() throws Exception {

        // Enter search criteria
        UiObject salaryEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/salaryEditText"));
        salaryEditText.setText("$1000");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the job is in the search results
        UiScrollable jobList = new UiScrollable(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/recyclerView"));
        jobList.scrollIntoView(new UiSelector().text("Test Job 1"));
        UiObject jobItem = jobList.getChild(new UiSelector().textContains("$1000"));
        assertNotNull(jobItem);
    }

    @Test
    public void testSearchByLocation() throws Exception {

        // Enter search criteria
        UiObject locationEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/locationEditText"));
        locationEditText.setText("Halifax");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Verify that the job is in the search results
        UiScrollable jobList = new UiScrollable(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/recyclerView"));
        jobList.scrollIntoView(new UiSelector().text("Test Job 1"));
        UiObject jobItem = jobList.getChild(new UiSelector().textContains("Halifax"));
        assertNotNull(jobItem);
    }

    @Test
    public void testApplyForJob() throws UiObjectNotFoundException {
        // Create a new job to apply for
        String jobId = mDatabase.push().getKey();
        Job job = new Job(jobId, "TestApply", "Test Job Type", "2023-11-30", "1 month", "days", "Urgent", "$1000", "CAD", "Halifax", "Test description", "employerId");

        assert jobId != null;
        mDatabase.child(jobId).setValue(job);

        // Enter search criteria
        UiObject titleEditText = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/titleEditText"));
        titleEditText.setText("TestApply");

        // Perform the search
        UiObject searchButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/searchButton"));
        searchButton.click();

        // Click on the job in the search results
        UiScrollable jobList = new UiScrollable(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/recyclerView"));
        jobList.scrollIntoView(new UiSelector().text("TestApply"));
        UiObject jobItem = jobList.getChild(new UiSelector().textContains("TestApply"));
        jobItem.click();

        // Click the apply button
        UiObject applyButton = mDevice.findObject(new UiSelector().resourceId("com.example.quickcashcsci3130g11:id/applyButton"));
        applyButton.click();

        // Verify that the user's ID has been added to the list of applicants for the job
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobId);
        ValueEventListener jobListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Job updatedJob = dataSnapshot.getValue(Job.class);
                if (updatedJob != null) {
                    assertTrue(updatedJob.getApplicants().contains("TestUser"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        };
        jobRef.addListenerForSingleValueEvent(jobListener);
    }


}
