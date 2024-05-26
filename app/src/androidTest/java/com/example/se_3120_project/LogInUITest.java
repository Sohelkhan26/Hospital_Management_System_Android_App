package com.example.se_3120_project;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import java.util.UUID;

@RunWith(AndroidJUnit4.class)
public class LogInUITest {

    @Rule
    public ActivityScenarioRule<LogIn> activityRule =
            new ActivityScenarioRule<>(LogIn.class);

    @Test
    public void testLogInCardEditTextIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.logInCardID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLogInEmailEditTextIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.logInEmailID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLogInPasswordEditTextIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.logInPasswordID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLogInButtonIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.logInButtonID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testToSignUpButtonIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.toSignUpButtonID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAdminLogInButtonIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.adminLogInID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testDoctorLogInButtonIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.doctorLogInID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testHospitalLogInButtonIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.hospitalLogInID))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testLoginWithCorrectCredentials() {
        Espresso.onView(ViewMatchers.withId(R.id.logInCardID))
                .perform(ViewActions.typeText("1"));
        Espresso.onView(ViewMatchers.withId(R.id.logInEmailID))
                .perform(ViewActions.typeText("gabbu@gmail.com "));
        Espresso.onView(ViewMatchers.withId(R.id.logInPasswordID))
                .perform(ViewActions.typeText("123"));


        Espresso.closeSoftKeyboard();


        Espresso.onView(ViewMatchers.withId(R.id.logInButtonID))
                .perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Check that the login was successful by checking if the Revive activity is displayed
        Espresso.onView(ViewMatchers.withId(R.id.patientTextID))
                .check(matches(isDisplayed()));
    }
    @Test
    public void adminButtonClicked()
    {
        Espresso.onView((ViewMatchers.withId(R.id.adminLogInID)))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.adminidID)).perform(ViewActions.typeText("abc"));
        Espresso.onView(ViewMatchers.withId(R.id.adminPasswordID)).perform(ViewActions.typeText("okay"));
        Espresso.onView(ViewMatchers.withId(R.id.adminButtonID)).perform(ViewActions.click());

        try{
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        // Checks if the Hospital_Name activity is displayed
        Espresso.onView(ViewMatchers.withId(R.id.hospitalNameID)).check(matches(isDisplayed()));
//        write random string as hospital name for testing purpose
        Espresso.onView(ViewMatchers.withId(R.id.hospitalNameID)).perform(ViewActions.typeText(UUID.randomUUID().toString()));
        Espresso.onView(ViewMatchers.withId(R.id.hospitalPasswordID)).perform(ViewActions.typeText("123"));
        Espresso.onView(ViewMatchers.withId(R.id.HospitalButtonID)).perform(ViewActions.click());

        try{
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Espresso.onView(ViewMatchers.withId(R.id.textView2)).check(matches(ViewMatchers.withText("Hospital Successfully added")));
    }
    @Test
    public void doctorButtonClicked()
    {
        Espresso.onView((ViewMatchers.withId(R.id.doctorLogInID)))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.doctorLogInidID))
                .check(matches(isDisplayed()));
    }
    @Test
    public void hospitalButtonClicked()
    {
        Espresso.onView((ViewMatchers.withId(R.id.hospitalLogInID)))
                .perform(ViewActions.click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Espresso.onView(ViewMatchers.withId(R.id.LogINhospitalNameID))
                .check(matches(isDisplayed()));
    }
    @Test
    public void toSignUpButtonClicked()
    {
        Espresso.onView((ViewMatchers.withId(R.id.toSignUpButtonID)))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.signUpNameID))
                .check(matches(isDisplayed()));
    }

}