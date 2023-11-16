package com.example.quickcashcsci3130g_11;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

public class IncomeAndReputationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> ActivityRule = new ActivityScenarioRule<>(MainActivity.class);

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
    public void testIncomeSectionDisplayed() {
        // Given I am logged in
        // When I navigate to the app's main screen
        // Then there should be a dedicated section displaying my income and earnings history
        assertTrue(MainActivity.isLoggedIn());
        assertNotNull(MainActivity.getIncome());
    }

    @Test
    public void testEarningsHistoryDetails() {
        // Given I have an earnings history
        // When I view the earnings history section
        // Then it should include details of each completed job's compensation
        assertTrue(MainActivity.isLoggedIn());
        assertNotNull(MainActivity.getEarningsHistory());

        // Details to check
        assertTrue(MainActivity.getEarningsHistory().contains("Amount Earned"));
        assertTrue(MainActivity.getEarningsHistory().contains("Date of Payment"));
    }

    @Test
    public void testVisualElementsForIncomeData() {
        // Given I am logged in
        // When I navigate to the income section
        // Then the app should use visual elements like charts or graphs to present income data
        assertTrue(MainActivity.isLoggedIn());
        assertNotNull(MainActivity.getIncome());

        // Example checks for the presence of visual elements
        UiObject titleIncomeChart = device.findObject(new UiSelector().textContains("Chart"));
        assertTrue(titleIncomeChart.exists());
    }

    @Test
    public void testJobHistoryVisibility() {
        // Given I am a user of the app
        // When I log in
        // Then I should be able to view my job history
        assertTrue(MainActivity.isLoggedIn());
        assertNotNull(MainActivity.getJobHistory());
    }

}
