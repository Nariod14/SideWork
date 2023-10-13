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
public class SwitchBack2Employer {
    @Rule
    public ActivityScenarioRule<EmployeeActivity> ActivityRule = new ActivityScenarioRule<>(EmployeeActivity.class);

    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(EmployeeActivity.class);
    }

    @Test
    public void confirmEmployeeProfileVisible () throws UiObjectNotFoundException {
        UiObject employeeBox = device.findObject(new UiSelector().textContains("Employee Profile"));
        assertTrue(employeeBox.exists());
    }

    @Test
    public void checkIfSwitched2EmployerP() throws UiObjectNotFoundException {
        UiObject employeeBox = device.findObject(new UiSelector().textContains("Employee"));
        assertTrue(employeeBox.exists());

        UiObject switch2EmployerB = device.findObject(new UiSelector().text("Switch Role to Employer"));
        assertTrue(switch2EmployerB.exists());

        boolean employeeProfileOpen = switch2EmployerB.clickAndWaitForNewWindow();
        assertTrue(employeeProfileOpen);

        UiObject employerLabel = device.findObject(new UiSelector().textContains("Employer"));
        assertTrue(employerLabel.exists());
    }
}
