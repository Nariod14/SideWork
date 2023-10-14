
package com.example.quickcashcsci3130g_11;

import static androidx.fragment.app.FragmentManager.TAG;
import static org.junit.Assert.assertNotNull;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpUiAutomator {

    @Rule
    public ActivityScenarioRule<SignUpActivity> mActivityRule = new ActivityScenarioRule<>(SignUpActivity.class);

    private UiDevice device;
    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ActivityScenario.launch(SignUpActivity.class);
    }

    @Test
    public void testSignUpWithValidCredentials() throws InterruptedException, UiObjectNotFoundException {
        // Type email
        UiObject emailEditText = device.findObject(new UiSelector().textContains("Email"));
        emailEditText.setText("test@example.com");

        // Type password
        UiObject passwordEditText = device.findObject(new UiSelector().textContains("Password"));
        passwordEditText.setText("password");

        // Click sign up button
        UiObject signUpButton = device.findObject(new UiSelector().text("Sign Up"));
        signUpButton.click();
        device.wait(Until.hasObject(By.pkg("com.example.quickcashcsci3130g_11.ProfileActivity").depth(0)), 2000);
        // Check if ProfileActivity is launched
        UiObject2 profileActivityObject = device.findObject(By.text("Employer Profile"));
        assertNotNull("ProfileActivity not launched", profileActivityObject);

        // Delete the user account
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         if (user != null) {
             user.delete()
                     .addOnCompleteListener(task -> {
                         if (task.isSuccessful()) {
                             Log.d(TAG, "User account deleted.");
                        } else {
                            Log.w(TAG, "Error deleting user account.", task.getException());
                        }
                     });
        }
    }
}
