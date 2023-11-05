package com.example.quickcashcsci3130g_11;


import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SubmitJobUIAutomatorTest {
    @Rule
    public ActivityScenarioRule<SubmitJobActivity> ActivityRule = new ActivityScenarioRule<>(SubmitJobActivity.class);

        private UiDevice device;

        @Before
        public void setup() {
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            ActivityScenario.launch(SubmitJobActivity.class);

            String password = "test123";
            String user = "test123@dal.ca";
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(user, password);

            grantPermissions();
        }

        private void grantPermissions() {
            // Grant location permission programmatically
            UiObject allowButton = device.findObject(new UiSelector().text("While using the app"));
            if (allowButton.exists()) {
                try {
                    allowButton.click();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        @Test
        public void testSubmitJob() throws UiObjectNotFoundException {
            // Given I am logged in as an employer
            // When I fill in all required fields and submit the job
            // Then the job should be listed on the app for potential employees to see

            UiObject titleEditText = device.findObject(new UiSelector().textContains("Enter Title"));
            assertTrue(titleEditText.exists());
            titleEditText.setText("Sample Job Title");

            UiObject dateEditText = device.findObject(new UiSelector().textContains("Press button to"));
            assertTrue(dateEditText.exists());
            UiObject datePickerButton = device.findObject(new UiSelector().textContains("Pick a Date"));
            assertTrue(datePickerButton.exists());
            datePickerButton.click();
            UiObject okButton = device.findObject(new UiSelector().text("OK"));
            okButton.waitForExists(5000);
            okButton.click();

            UiObject durationEditText = device.findObject(new UiSelector().textContains("Enter Duration"));
            assertTrue(durationEditText.exists());
            durationEditText.setText("2");

            device.swipe(0, device.getDisplayHeight() / 2, 0, 0, 10);

            UiObject salaryEditText = device.findObject(new UiSelector().textContains("Enter Salary"));
            assertTrue(salaryEditText.exists());
            salaryEditText.setText("13");

            UiObject locationButton = device.findObject(new UiSelector().textContains("Get Location"));
            assertTrue(locationButton.exists());
            locationButton.click();

            UiObject descriptionEditText = device.findObject(new UiSelector().textContains("Enter Description"));
            assertTrue(descriptionEditText.exists());
            descriptionEditText.setText("Sample Description");

            UiObject submitButton = device.findObject(new UiSelector().textContains("Submit"));
            assertTrue(submitButton.exists());
            submitButton.click();

            UiObject jobPostingsTextView = device.findObject(new UiSelector().textContains("Job Postings"));
            assertTrue(jobPostingsTextView.exists());
        }
}
