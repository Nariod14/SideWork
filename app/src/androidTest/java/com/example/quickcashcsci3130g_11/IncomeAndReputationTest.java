package com.example.quickcashcsci3130g_11;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import com.google.firebase.auth.FirebaseAuth;

public class IncomeAndReputationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> ActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(MainActivity.class);

        String password = "test246";
        String user = "test246@dal.ca";
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

        UiObject incomeHistoryButton = device.findObject(new UiSelector().textContains("Income History"));
        assertTrue(incomeHistoryButton.exists());
    }

    @Test
    public void testEarningsHistoryDetails() {
        // Given I have an earnings history
        // When I view the earnings history section
        // Then it should include details of each completed job's compensation
        UiObject overallIncome = device.findObject(new UiSelector().textContains("Overall Income"));
        assertTrue(overallIncome.exists());
    }

    @Test
    public void testVisualElementsForIncomeData() throws UiObjectNotFoundException {
        // Given I am logged in
        // When I navigate to the income section
        // Then the app should use visual elements like charts or graphs to present income data

        UiObject monthlyIncome = device.findObject(new UiSelector().textContains("Monthly Income"));
        assertTrue(monthlyIncome.exists());
    }

    @Test
    public void testReputationVisibility() {
        // Given I am a user of the app
        // When I log in
        // Then I should be able to view my reputation
        UiObject reputation = device.findObject(new UiSelector().textContains("User Popularity"));
        assertTrue(reputation.exists());
    }

}
