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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SwitchRoleUIAutomator {
    @Rule
    public ActivityScenarioRule<ProfileActivity> ActivityRule = new ActivityScenarioRule<>(ProfileActivity.class);

    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(ProfileActivity.class);
    }

    @Test
    public void checkIfSwitched2EmployeeP() throws UiObjectNotFoundException {
        UiObject employerBox = device.findObject(new UiSelector().textContains("Employer"));
        assertTrue(employerBox.exists());

        UiObject switch2EmployeeB = device.findObject(new UiSelector().text("Switch Role"));
        assertTrue(switch2EmployeeB.exists());

        boolean employeeProfileOpen = switch2EmployeeB.clickAndWaitForNewWindow();
        assertTrue(employeeProfileOpen);

        UiObject employerLabel = device.findObject(new UiSelector().textContains("Employee"));
        assertTrue(employerLabel.exists());
    }
}
