package com.example.quickcashcsci3130g_11;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SignUpActivityTest {

    @Before
    public void setUp() {
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext());
    }

    @Rule
    public ActivityScenarioRule<SignUpActivity> intentsRule = new ActivityScenarioRule<>(SignUpActivity.class);

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityRule =
            new ActivityScenarioRule<>(SignUpActivity.class);

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

