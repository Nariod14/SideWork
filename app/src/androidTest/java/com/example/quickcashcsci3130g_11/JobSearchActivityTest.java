package com.example.quickcashcsci3130g_11;

import static android.content.ContentValues.TAG;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobSearchActivityTest {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Rule
    public ActivityScenarioRule<JobSearchActivity> mActivityRule = new ActivityScenarioRule<>(JobSearchActivity.class);

    @Before
    public void setUp() {
        // Initialize Firebase database
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();
        // Create test job objects
//        Job job1 = new Job("1", "Software Engineer", "Full-time", "2023-10-27", "1", "year", "Urgent", "per year", "$100,000", "Toronto", "description", "johndoe");
//        Job job2 = new Job("2", "Data Analyst", "Part-time", "2023-10-28", "6", "months", "Normal", "per year", "$50,000", "Vancouver", "description", "janedoe");
//        // Sign in with test account
        mAuth.signInWithEmailAndPassword("existing_email@example.com", "password")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
//                        // Add test job objects to Firebase database
//                        mDatabase.child("jobs").child(job1.getJobId()).setValue(job1);
//                        mDatabase.child("jobs").child(job2.getJobId()).setValue(job2);
                        Log.d(TAG, "signInWithEmail:success");
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                });
    }

    @After
    public void tearDown() {
        // Delete test job objects from Firebase database
        mDatabase.child("jobs").child("1").removeValue();
        mDatabase.child("jobs").child("2").removeValue();
        // Sign out
        mAuth.signOut();
    }

    @Test
    public void testSearchByTitle() {
        onView(withId(R.id.search_view)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("Mow the lawn"), pressImeActionButton());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("Mow the lawn"))));
    }

    @Test
    public void testSearchByJobType() {
        onView(withId(R.id.search_view)).perform(click());
        onView(isAssignableFrom(Spinner.class)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Full-time"))).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("Full-time"))));
    }

    @Test
    public void testSearchByEmployerId() {
        onView(withId(R.id.search_view)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("johndoe"), pressImeActionButton());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("123456"))));
    }

}