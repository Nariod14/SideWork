package com.example.quickcashcsci3130g_11;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SignUpUnitTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Test
    public void testIsValidEmail() {
        SignUpActivity activity = new SignUpActivity();
        assertTrue(activity.isValidEmail("test@example.com"));
        assertFalse(activity.isValidEmail("test@"));
        assertFalse(activity.isValidEmail("test@example"));
        assertFalse(activity.isValidEmail(""));
    }

    @Test
    public void testIsValidPassword() {
        SignUpActivity activity = new SignUpActivity();
        assertTrue(activity.isValidPassword("password1"));
        assertFalse(activity.isValidPassword("pass"));
        assertFalse(activity.isValidPassword("password!"));
        assertFalse(activity.isValidPassword(""));
    }
}