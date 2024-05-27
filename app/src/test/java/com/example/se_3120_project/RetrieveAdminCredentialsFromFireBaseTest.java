package com.example.se_3120_project;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class RetrieveAdminCredentialsFromFireBaseTest {

    @Mock
    DatabaseReference mockDatabaseReference;
    @Mock
    FirebaseDatabase mockFirebaseDatabase;
    @Mock
    DataSnapshot mockDataSnapshot;
    @Mock
    DatabaseError mockDatabaseError;

    private RetrieveAdminCredentialsFromFireBase retrieveAdminCredentialsFromFireBase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the FirebaseDatabase instance
        when(mockFirebaseDatabase.getReferenceFromUrl(any(String.class))).thenReturn(mockDatabaseReference);
        DatabaseReference mockUserRef = mock(DatabaseReference.class);
        DatabaseReference mockAdminRef = mock(DatabaseReference.class);

        // Mock the child references
        when(mockDatabaseReference.child("user")).thenReturn(mockUserRef);
        when(mockUserRef.child("admin")).thenReturn(mockAdminRef);

        ConnectToFireBase.setInstance(mockDatabaseReference); // Set the mocked instance

        retrieveAdminCredentialsFromFireBase = new RetrieveAdminCredentialsFromFireBase();
    }

    @Test
    public void testGetAdminCredentials_Success() {
        // Arrange
        String adminId = "admin123";
        String adminPassword = "password123";

        // Properly mock the child DataSnapshot objects
        DataSnapshot mockChildSnapshotId = mock(DataSnapshot.class);
        when(mockChildSnapshotId.getValue(String.class)).thenReturn(adminId);

        DataSnapshot mockChildSnapshotPassword = mock(DataSnapshot.class);
        when(mockChildSnapshotPassword.getValue(String.class)).thenReturn(adminPassword);

        // Mock the behavior of the parent snapshot to return the child snapshots
        when(mockDataSnapshot.child("id")).thenReturn(mockChildSnapshotId);
        OngoingStubbing<DataSnapshot> dataSnapshotOngoingStubbing = when(mockDataSnapshot.child(adminId)).thenReturn(mockChildSnapshotPassword);

//        doAnswer((Answer<Void>) invocation -> {
//            ValueEventListener listener = invocation.getArgument(0);
//            listener.onDataChange(mockDataSnapshot);
//            return null;
//        }).when(mockDatabaseReference.child("user").child("admin")).addListenerForSingleValueEvent(any(ValueEventListener.class));

        RetrieveAdminCredentialsFromFireBase.AdminCredentialsCallback callback = new RetrieveAdminCredentialsFromFireBase.AdminCredentialsCallback() {
            @Override
            public void onAdminCredentialsRetrieved(String id, String password) {
                // Assert
                assertEquals(adminId, id);
                assertEquals(adminPassword, password);
            }

            @Override
            public void onError(String errorMessage) {
                // Fail the test if this is called
                throw new AssertionError("onError should not be called");
            }
        };

        // Act
        retrieveAdminCredentialsFromFireBase.getAdminCredentials(callback);

        // Assert
        verify(mockDatabaseReference.child("user").child("admin")).addListenerForSingleValueEvent(any(ValueEventListener.class));
    }

    @Test
    public void testGetAdminCredentials_Error() {
        // Arrange
        String errorMessage = "Error: Database error";

        when(mockDatabaseError.getMessage()).thenReturn("Database error");

//        doAnswer((Answer<Void>) invocation -> {
//            ValueEventListener listener = invocation.getArgument(0);
//            listener.onCancelled(mockDatabaseError);
//            return null;
//        }).when(mockDatabaseReference.child("user").child("admin")).addListenerForSingleValueEvent(any(ValueEventListener.class));

        RetrieveAdminCredentialsFromFireBase.AdminCredentialsCallback callback = new RetrieveAdminCredentialsFromFireBase.AdminCredentialsCallback() {
            @Override
            public void onAdminCredentialsRetrieved(String id, String password) {
                // Fail the test if this is called
                throw new AssertionError("onAdminCredentialsRetrieved should not be called");
            }

            @Override
            public void onError(String error) {
                // Assert
                assertEquals(errorMessage, error);
            }
        };

        // Act
        retrieveAdminCredentialsFromFireBase.getAdminCredentials(callback);

        // Assert
        verify(mockDatabaseReference.child("user").child("admin")).addListenerForSingleValueEvent(any(ValueEventListener.class));
    }
}
