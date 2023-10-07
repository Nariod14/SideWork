package com.example.quickcashcsci3130g_11;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.google.firebase.firestore.util.Assert.fail;
import static org.junit.Assert.assertTrue;


import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
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
    public ActivityScenarioRule<SignUpActivity> ActivityRule = new ActivityScenarioRule<>(SignUpActivity.class);

    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(ProfileActivity.class);
    }

    @Test
    public void checkIfSwitched2EmployerP() throws UiObjectNotFoundException {
        UiObject emailBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailBox.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());

        String emailTest = "abc123@dal.ca";
        String passwordTest = "abc123890";

        try {
            emailBox.setText(emailTest);
            passwordBox.setText(passwordTest);
        } catch (UiObjectNotFoundException e) {
            fail("Failed to input values");
        }

        UiObject singUpButton = device.findObject(new UiSelector().text("Sign Up"));
        assertTrue(singUpButton.exists());

        boolean employerProfileOpen = singUpButton.clickAndWaitForNewWindow();
        assertTrue(employerProfileOpen);

        UiObject employerLabel = device.findObject(new UiSelector().textContains("Employer"));
        assertTrue(employerLabel.exists());
    }
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
