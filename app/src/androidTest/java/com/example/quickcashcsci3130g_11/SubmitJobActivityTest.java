package com.example.quickcashcsci3130g_11;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.widget.DatePicker;


import androidx.test.espresso.Espresso;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SubmitJobActivityTest {
    @Rule
    public ActivityScenarioRule<SubmitJobActivity> activityRule = new ActivityScenarioRule<>(SubmitJobActivity.class);

    @Test
    public void testSelectPredefinedJobType() {
        // Given I am logged in as an employer
        // When I navigate to the "Submit a Job" section
        // Then I should be able to select from one of the predefined small paid jobs
        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.jobTypeSpinner))
                .atPosition(5)
                .perform(ViewActions.click());
    }

    @Test
    // Given I am logged in as an employer
    // When I navigate to the "Submit a Job" section
    // Then I should be able to enter job details such as title, date, expected duration, urgency, salary, and job location
    public void testEnterJobDetails() {
        Espresso.onView(ViewMatchers.withId(R.id.titleEditText))
                .perform(ViewActions.typeText("Sample Job Title"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.datePickerButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 11, 30));
        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.typeText("2"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.getLocationButton))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.descriptionEditText))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.typeText("Sample Description"), ViewActions.closeSoftKeyboard());
    }
    @Test
    public void testSelectDate() {
        Espresso.onView(ViewMatchers.withId(R.id.datePickerButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 12, 25));
        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(ViewActions.click());
    }

    @Test
    public void testDurationSpinner() {
        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.durationTypeSpinner))
                .atPosition(1)
                .perform(ViewActions.click());

    }
    @Test
    public void testUrgencySpinner() {
        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.urgencyTypeSpinner))
                .atPosition(1)
                .perform(ViewActions.click());

    }

    @Test
    public void testSalarySpinner() {
        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText))
                .perform(ViewActions.scrollTo());
        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.salaryTypeSpinner))
                .atPosition(1)
                .perform(ViewActions.click());

    }

    @Test
    // Given I am on the job submission screen
    // When I submit the job without filling in all required fields
    public void testInputFieldValidations() {

        Espresso.onView(ViewMatchers.withId(R.id.submitButton))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.titleEditText))
                .check(matches(ViewMatchers.hasErrorText("Title is required")));
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText))
                .check(matches(ViewMatchers.hasErrorText("Duration is required")));
        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText))
                .check(matches(ViewMatchers.hasErrorText("Salary is required")));
        Espresso.onView(ViewMatchers.withId(R.id.locationEditText))
                .check(matches(ViewMatchers.hasErrorText("Location is required")));
        Espresso.onView(ViewMatchers.withId(R.id.descriptionEditText))
                .check(matches(ViewMatchers.hasErrorText("Description is required")));
    }
}
