package com.example.quickcashcsci3130g_11;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
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
                .atPosition(3)
                .perform(ViewActions.click());
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
    public void testEnterJobDetails() {
        // Given I am logged in as an employer
        // When I navigate to the "Submit a Job" section
        // Then I should be able to enter job details such as title, date, expected duration, urgency, salary, and job location
        Espresso.onView(ViewMatchers.withId(R.id.titleEditText))
                .perform(ViewActions.typeText("Sample Job Title"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.datePickerButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 11, 30));
        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.durationEditText))
                .perform(ViewActions.typeText("2"), ViewActions.closeSoftKeyboard());

        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.durationTypeSpinner))
                .atPosition(0)
                .perform(ViewActions.click());

        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.urgencyTypeSpinner))
                .atPosition(0)
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText))
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());

        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.salaryTypeSpinner))
                .atPosition(0)
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.locationEditText))
                .perform(ViewActions.typeText("Sample Location"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.descriptionEditText))
                .perform(ViewActions.typeText("Sample Description"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.submitButton))
                .perform(ViewActions.click());
    }

    @Test
    public void testInputFieldValidations() {
        // Given I am on the job submission screen
        // When I submit the job without filling in all required fields
        // Then validations should be made for the input fields
        Espresso.onView(ViewMatchers.withId(R.id.submitButton))
                .perform(ViewActions.click());

        // Validate that error messages are displayed for the empty fields
        Espresso.onView(ViewMatchers.withId(R.id.titleEditText))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Title is required")));
        Espresso.onView(ViewMatchers.withId(R.id.setDateTextView))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Date is required")));
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Duration is required")));
        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Salary is required")));
        Espresso.onView(ViewMatchers.withId(R.id.locationEditText))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Location is required")));
    }

    @Test
    public void testSubmitJob() {
        // Given I am logged in as an employer

        // When I fill in all required fields and submit the job
        Espresso.onView(ViewMatchers.withId(R.id.titleEditText))
                .perform(ViewActions.typeText("Sample Job Title"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.datePickerButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2023, 10, 31));
        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.durationEditText))
                .perform(ViewActions.typeText("2"), ViewActions.closeSoftKeyboard());

        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.urgencyTypeSpinner))
                .atPosition(1)
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText))
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.locationEditText))
                .perform(ViewActions.typeText("Sample Location"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.submitButton))
                .perform(ViewActions.click());

        // Then the job should be listed on the app for potential employees to see
        Espresso.onView(ViewMatchers.withText("Job submitted successfully"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
