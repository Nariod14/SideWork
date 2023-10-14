package com.example.quickcashcsci3130g_11;



import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LandingPageUIAutomator {
    @Rule
    public ActivityScenarioRule<EmployerActivity> ActivityRule = new ActivityScenarioRule<>(EmployerActivity.class);

    private UiDevice device;

    @Test
    public void EmployeeActivityVisible() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(EmployeeActivity.class);
    }
    @Test
    public void EmployerActivityVisible() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(EmployerActivity.class);
    }


}
