package com.example.se_3120_project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class AdminTest {

    @Mock
    private DatabaseReference mockDatabaseReference;

    @Mock
    private DataSnapshot mockDataSnapshot;

    private Admin adminActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ConnectToFireBase.setInstance(mockDatabaseReference);

        adminActivity = Robolectric.buildActivity(Admin.class).create().start().resume().get();
    }

    @Test
    public void testAdminLogIn_Success() {
        when(mockDataSnapshot.hasChild("admin123")).thenReturn(true);
        when(mockDataSnapshot.child("admin123").child("Password").getValue(String.class)).thenReturn("password");

        Mockito.doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));

        EditText id = adminActivity.findViewById(R.id.adminidID);
        EditText pass = adminActivity.findViewById(R.id.adminPasswordID);
        Button adminBtn = adminActivity.findViewById(R.id.adminButtonID);

        id.setText("admin123");
        pass.setText("password");
        adminBtn.performClick();

        String toastMessage = ShadowToast.getTextOfLatestToast();
        assertEquals("Successfully Logged In", toastMessage);
    }

    @Test
    public void testAdminLogIn_Failure() {
        when(mockDataSnapshot.hasChild("admin123")).thenReturn(false);

        Mockito.doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));

        EditText id = adminActivity.findViewById(R.id.adminidID);
        EditText pass = adminActivity.findViewById(R.id.adminPasswordID);
        Button adminBtn = adminActivity.findViewById(R.id.adminButtonID);

        id.setText("admin123");
        pass.setText("wrongpassword");
        adminBtn.performClick();

        String toastMessage = ShadowToast.getTextOfLatestToast();
        assertEquals("Admin not found", toastMessage);
    }

    @Test
    public void testAdminLogIn_PasswordWrong() {
        when(mockDataSnapshot.hasChild("admin123")).thenReturn(true);
        when(mockDataSnapshot.child("admin123").child("Password").getValue(String.class)).thenReturn("password");

        Mockito.doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(Mockito.any(ValueEventListener.class));

        EditText id = adminActivity.findViewById(R.id.adminidID);
        EditText pass = adminActivity.findViewById(R.id.adminPasswordID);
        Button adminBtn = adminActivity.findViewById(R.id.adminButtonID);

        id.setText("admin123");
        pass.setText("wrongpassword");
        adminBtn.performClick();

        String toastMessage = ShadowToast.getTextOfLatestToast();
        assertEquals("Password Wrong", toastMessage);
    }

    @Test
    public void testEmptyFields() {
        Button adminBtn = adminActivity.findViewById(R.id.adminButtonID);
        adminBtn.performClick();

        String toastMessage = ShadowToast.getTextOfLatestToast();
        assertEquals("Fill Both Info, please!", toastMessage);
    }
}
