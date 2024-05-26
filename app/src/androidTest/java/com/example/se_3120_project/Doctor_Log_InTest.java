package com.example.se_3120_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

//@RunWith(AndroidJUnit4.class)
public class Doctor_Log_InTest {
    @Rule
    public ActivityTestRule<Doctor_Log_In> activityRule = new ActivityTestRule<>(Doctor_Log_In.class);

    @Mock
    private DatabaseReference mockDatabaseReference;

    @Mock
    private DataSnapshot mockDataSnapshot;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mock the FirebaseDatabase instance
        ConnectToFireBase.setInstance(mockDatabaseReference);
    }

    @Test
    public void testDoctorLogIn_Success() {
        // Set up the mock data snapshot for a successful login
        Mockito.when(mockDataSnapshot.hasChild("doctor123")).thenReturn(true);
        Mockito.when(mockDataSnapshot.child("doctor123").child("Password").getValue(String.class)).thenReturn("password");
        Mockito.when(mockDataSnapshot.child("doctor123").child("Name").getValue(String.class)).thenReturn("John Doe");

        Mockito.doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));

        // Perform UI interactions
        onView(withId(R.id.doctorLogInidID)).perform(replaceText("doctor123"));
        onView(withId(R.id.doctorLogInPasswordID)).perform(replaceText("password"));
        onView(withId(R.id.doctorLogInButtonID)).perform(click());

        // Verify the welcome message and navigation
        onView(withId(R.id.texviewdoctorLogInID)).check(matches(withText("Welcome Dr. John Doe")));

        Intents.init();
        intended(hasComponent(Doctors_Own_Page.class.getName()));
        Intents.release();
    }

    @Test
    public void testDoctorLogIn_Failure() {
        // Set up the mock data snapshot for a failed login
        Mockito.when(mockDataSnapshot.hasChild("doctor123")).thenReturn(false);

        Mockito.doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));

        // Perform UI interactions
        onView(withId(R.id.doctorLogInidID)).perform(replaceText("doctor123"));
        onView(withId(R.id.doctorLogInPasswordID)).perform(replaceText("wrongpassword"));
        onView(withId(R.id.doctorLogInButtonID)).perform(click());

        // Verify the failure message
        onView(withText("Doctor ID isn't Exists")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigateToRegister() {
        // Perform UI interactions
        onView(withId(R.id.DoctorRegisterFromLogINID)).perform(click());

        // Verify the navigation to the registration activity
        Intents.init();
        intended(hasComponent(Doctor.class.getName()));
        Intents.release();
    }

}