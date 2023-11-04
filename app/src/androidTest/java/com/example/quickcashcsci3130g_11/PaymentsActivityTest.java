package com.example.quickcashcsci3130g_11;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PaymentsActivityTest {

    @Rule
    public ActivityTestRule<PaymentsActivity> activityTestRule = new ActivityTestRule<>(PaymentsActivity.class);

    @Test
    public void ensurePaymentProcessWorks() {
        onView(withId(R.id.amountEditText)).perform(typeText("10.00"));
        onView(withId(R.id.makePaymentButton)).perform(click());
        onView(withId(R.id.confirmationTextView)).check(matches(withText("Payment confirmed")));
    }
}

