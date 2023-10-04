package com.example.quickcashcsci3130g_11;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SignUpActivityTest {

    @Before
    public void setUp() {
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext());
    }

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityRule =
            new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void testSignUpWithValidCredentials() throws IOException {
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
                .perform(ViewActions.click());

        // Check if ProfileActivity is launched
        Intents.init();
        intended(hasComponent(ProfileActivity.class.getName()));
        Intents.release();

    }

    @Test
    public void testSignUpWithExistingEmail() throws InterruptedException {
        // Type an email that already exists in the database
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("existing_email@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
                .perform(ViewActions.click());
        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withText(R.string.ACCOUNT_EXISTS))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    @Test
    public void testSignUpWithInvalidPassword() {
        // Type an email that does not exist in the database
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("new_email@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("short"));
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
                .perform(ViewActions.click());

        // Check that the error message is displayed
        Espresso.onView(ViewMatchers.withText("Password must contain at least 6 characters, Allowed characters – A-Za-z0-9"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUpWithInvalidEmail() {
        // Type an invalid email address
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("invalid_email"));
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
                .perform(ViewActions.click());

        // Check that the error message is displayed
        Espresso.onView(ViewMatchers.withText("Enter a valid email address"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}

