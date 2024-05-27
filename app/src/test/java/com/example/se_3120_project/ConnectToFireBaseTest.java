package com.example.se_3120_project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ConnectToFireBaseTest {

    @Mock
    FirebaseDatabase mockFirebaseDatabase;

    @Mock
    DatabaseReference mockDatabaseReference;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockFirebaseDatabase.getReferenceFromUrl("https://se-3120-project-default-rtdb.firebaseio.com/")).thenReturn(mockDatabaseReference);
        ConnectToFireBase.databaseReference = null; // Reset the singleton instance
    }

    @Test
    public void testGetInstance_FirstCall() {
        try (MockedStatic<FirebaseDatabase> mockedFirebaseDatabase = Mockito.mockStatic(FirebaseDatabase.class)) {
            mockedFirebaseDatabase.when(FirebaseDatabase::getInstance).thenReturn(mockFirebaseDatabase);
            DatabaseReference instance = ConnectToFireBase.getInstance();
            assertNotNull(instance);
            assertEquals(mockDatabaseReference, instance);
        }
    }

    @Test
    public void testSetInstance() {
        ConnectToFireBase.setInstance(mockDatabaseReference);
        DatabaseReference instance = ConnectToFireBase.getInstance();
        assertEquals(mockDatabaseReference, instance);
    }

    @Test
    public void testGetInstance_AfterSetInstance() {
        ConnectToFireBase.setInstance(mockDatabaseReference);
        DatabaseReference instance = ConnectToFireBase.getInstance();
        assertEquals(mockDatabaseReference, instance);
    }
}
