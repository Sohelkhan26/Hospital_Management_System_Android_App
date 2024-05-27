package com.example.se_3120_project;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AdminUITest {

    @Rule
    public ActivityScenarioRule<Admin> activityRule =
            new ActivityScenarioRule<>(Admin.class);

    @Test
    public void testAdminLoginSuccess() {
        // Type text and then press the button
        Espresso.onView(ViewMatchers.withId(R.id.adminidID)).perform(ViewActions.typeText("admin123"));
        Espresso.onView(ViewMatchers.withId(R.id.adminPasswordID)).perform(ViewActions.typeText("password123"));
        Espresso.onView(ViewMatchers.withId(R.id.adminButtonID)).perform(ViewActions.click());

        // Check if the toast message is displayed
        Espresso.onView(withText("Successfully Logged In"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        // Check if the Hospital_Name activity is launched
        ActivityScenario<Hospital_Name> hospitalNameScenario = ActivityScenario.launch(new Intent());
        hospitalNameScenario.onActivity(activity -> {
            // Assert that the Hospital_Name activity is in the foreground
            // You can perform additional assertions specific to Hospital_Name activity here
        });
    }

    @Test
    public void testAdminLoginFailure() {
        // Type text and then press the button
        Espresso.onView(ViewMatchers.withId(R.id.adminidID)).perform(ViewActions.typeText("admin123"));
        Espresso.onView(ViewMatchers.withId(R.id.adminPasswordID)).perform(ViewActions.typeText("wrongpassword"));
        Espresso.onView(ViewMatchers.withId(R.id.adminButtonID)).perform(ViewActions.click());

        // Check if the toast message is displayed
        Espresso.onView(withText("Password Wrong"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAdminEmptyFields() {
        // Click the button without entering any text
        Espresso.onView(ViewMatchers.withId(R.id.adminButtonID)).perform(ViewActions.click());

        // Check if the toast message is displayed
        Espresso.onView(withText("Fill Both Info, please!"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
