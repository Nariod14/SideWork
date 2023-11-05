package com.example.quickcashcsci3130g_11;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class EnterSubmitJob {
    @Rule
    public ActivityScenarioRule<EmployerActivity> activityRule = new ActivityScenarioRule<>(EmployerActivity.class);

    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(EmployerActivity.class);
    }

    @Test
    public void checkJobFormOpens() throws UiObjectNotFoundException {
        // Given I am logged in as an employer
        // When I navigate to the "Submit a Job" section
        // Then I should be able to enter job details such as title, date, expected duration, urgency, salary, and job location
        UiObject employerBox = device.findObject(new UiSelector().textContains("Employer"));
        assertTrue(employerBox.exists());

        UiObject enterSubmitJob = device.findObject(new UiSelector().text("Add Job Posting"));
        assertTrue(enterSubmitJob.exists());

        boolean jobFormOpen = enterSubmitJob.clickAndWaitForNewWindow();
        assertTrue(jobFormOpen);

        UiObject submitJobTextView = device.findObject(new UiSelector().textContains("Submit a Job"));
        assertTrue(submitJobTextView.exists());
        UiObject jobTitleTextView = device.findObject(new UiSelector().text("Title:"));
        assertTrue(jobTitleTextView.exists());
        UiObject dateTextView = device.findObject(new UiSelector().text("Date:"));
        assertTrue(dateTextView.exists());
        UiObject jobTypeTextView = device.findObject(new UiSelector().text("Job Type:"));
        assertTrue(jobTypeTextView.exists());
        UiObject expectedDurationTextView = device.findObject(new UiSelector().text("Expected Duration:"));
        assertTrue(expectedDurationTextView.exists());
        UiObject urgencyTextView = device.findObject(new UiSelector().text("Urgency:"));
        assertTrue(urgencyTextView.exists());
        UiObject salaryTextView = device.findObject(new UiSelector().text("Salary:"));
        assertTrue(salaryTextView.exists());
        UiObject locationTextView = device.findObject(new UiSelector().text("Location:"));
        assertTrue(locationTextView.exists());
        UiObject descriptionTextView = device.findObject(new UiSelector().text("Description:"));
        assertTrue(descriptionTextView.exists());
    }
}

