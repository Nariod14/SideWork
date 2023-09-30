package com.example.quickcashcsci3130g_11;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ResetPasswordActivityTest {

    @Rule
    public ActivityScenarioRule<ResetPasswordActivity> activityRule =
            new ActivityScenarioRule<>(ResetPasswordActivity.class);

    @Test
    public void testResetPasswordWithValidEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_reset_password))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("We have sent you instructions to reset your password!"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testResetPasswordWithInvalidEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("invalid_email"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_reset_password))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Failed to send reset email!"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}

