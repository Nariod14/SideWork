package com.example.quickcashcsci3130g_11;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobSearchUIAutomator {

    private UiDevice mDevice;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Log in as an employee
    }

    @Test
    public void testJobSearchFilterOptions() {
        // Access the job search feature
        // Find the filter options for job title, salary range, expected duration, and vicinity
        // Assert that the filter options are visible on the screen
        UiObject jobTitleFilterOption = mDevice.findObject(new UiSelector().text("Job Title"));
        UiObject salaryRangeFilterOption = mDevice.findObject(new UiSelector().text("Salary Range"));
        UiObject expectedDurationFilterOption = mDevice.findObject(new UiSelector().text("Expected Duration"));
        UiObject vicinityFilterOption = mDevice.findObject(new UiSelector().text("Vicinity"));
        assertTrue(jobTitleFilterOption.exists());
        assertTrue(salaryRangeFilterOption.exists());
        assertTrue(expectedDurationFilterOption.exists());
        assertTrue(vicinityFilterOption.exists());
    }

    @Test
    public void testJobSearchResults() throws UiObjectNotFoundException {
        // Access the job search feature
        // Enter search parameters for job title, salary range, expected duration, and vicinity
        // Click the search button
        // Find the job listings that match the selected criteria
        // Assert that the job listings are visible on the screen
        UiObject jobTitleField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/jobTitleField"));
        UiObject salaryRangeField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/salaryRangeField"));
        UiObject expectedDurationField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/expectedDurationField"));
        UiObject vicinityField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/vicinityField"));
        jobTitleField.setText("jobTitle");
        salaryRangeField.setText("salaryRange");
        expectedDurationField.setText("expectedDuration");
        vicinityField.setText("vicinity");
        UiObject searchButton = mDevice.findObject(new UiSelector().text("Search"));
        searchButton.click();
        UiObject jobListings = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/jobListings"));
        assertTrue(jobListings.exists());
    }

    @Test
    public void testNoJobSearchResults() throws UiObjectNotFoundException {
        // Access the job search feature
        // Enter search parameters that do not match any job listings
        // Click the search button
        // Find the message indicating that no results were found
        // Assert that the message is visible on the screen
        UiObject jobTitleField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/jobTitleField"));
        UiObject salaryRangeField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/salaryRangeField"));
        UiObject expectedDurationField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/expectedDurationField"));
        UiObject vicinityField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/vicinityField"));
        jobTitleField.setText("jobTitle");
        salaryRangeField.setText("salaryRange");
        expectedDurationField.setText("expectedDuration");
        vicinityField.setText("vicinity");
        UiObject searchButton = mDevice.findObject(new UiSelector().text("Search"));
        searchButton.click();
        UiObject noResultsMessage = mDevice.findObject(new UiSelector().text("No results found. Please adjust your search criteria."));
        assertTrue(noResultsMessage.exists());
    }

    @Test
    public void testJobApplication() throws UiObjectNotFoundException {
        // Access the job search feature
        // Enter search parameters for a relevant job
        // Click the search button
        // Find the relevant job listing
        // Click the apply button
        // Access the job history feature
        // Find the relevant job listing in the current job section
        // Assert that the job listing is visible on the screen
        UiObject jobTitleField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/jobTitleField"));
        UiObject salaryRangeField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/salaryRangeField"));
        UiObject expectedDurationField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/expectedDurationField"));
        UiObject vicinityField = mDevice.findObject(new UiSelector().resourceId("com.example.app:id/vicinityField"));
        jobTitleField.setText("jobTitle");
        salaryRangeField.setText("salaryRange");
        expectedDurationField.setText("expectedDuration");
        vicinityField.setText("vicinity");
        UiObject searchButton = mDevice.findObject(new UiSelector().text("Search"));
        searchButton.click();
        UiObject relevantJobListing = mDevice.findObject(new UiSelector().text("Relevant Job Listing"));
        UiObject applyButton = relevantJobListing.getChild(new UiSelector().text("Apply"));
        applyButton.click();
        UiObject jobHistoryButton = mDevice.findObject(new UiSelector().text("Job History"));
        jobHistoryButton.click();
        UiObject currentJobSection = mDevice.findObject(new UiSelector().text("Current Job"));
        assertTrue(currentJobSection.exists());
    }

    @After
    public void tearDown() {
        // Log out as an employee
    }
}