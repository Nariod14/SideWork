package com.example.quickcashcsci3130g_11;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
public class LoginActivityTest {
    private ActivityScenario<LoginActivity> scenario;
    @Before
    public void setup(){
        scenario = ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.emailLogin)).perform(typeText(""));
        onView(withId(R.id.passwordLogin)).perform(typeText("12345678"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.emailLogin)).check(matches(withText("")));
    }

    @Test
    public void checkIfPasswordIsEmpty(){
        onView(withId(R.id.emailLogin)).perform(typeText("test@example.com"));
        onView(withId(R.id.passwordLogin)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.passwordLogin)).check(matches(withText("")));
    }
}
