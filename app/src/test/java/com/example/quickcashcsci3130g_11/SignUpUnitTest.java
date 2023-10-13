package com.example.quickcashcsci3130g_11;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SignUpUnitTest {

    @Test
    public void testIsValidEmail() {
        SignUpMethodsTest activity = new SignUpMethodsTest();
        assertTrue(activity.isValidEmail("test@example.com"));
        assertFalse(activity.isValidEmail("test@"));
        assertFalse(activity.isValidEmail("test@example"));
        assertFalse(activity.isValidEmail(""));
    }

    @Test
    public void testIsValidPassword() {
        SignUpMethodsTest activity = new SignUpMethodsTest();
        assertTrue(activity.isValidPassword("password1"));
        assertFalse(activity.isValidPassword("pass"));
        assertFalse(activity.isValidPassword("password!"));
        assertFalse(activity.isValidPassword(""));
    }
}