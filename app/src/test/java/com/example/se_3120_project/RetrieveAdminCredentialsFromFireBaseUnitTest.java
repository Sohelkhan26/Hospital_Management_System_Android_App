package com.example.se_3120_project;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class RetrieveAdminCredentialsFromFireBaseUnitTest {
    @Test
    public void testGetAdminCredentials() throws InterruptedException {
        RetrieveAdminCredentialsFromFireBase retrieveAdminCredentialsFromFireBase = new RetrieveAdminCredentialsFromFireBase();
        CountDownLatch latch = new CountDownLatch(1);
        retrieveAdminCredentialsFromFireBase.getAdminCredentials(new RetrieveAdminCredentialsFromFireBase.AdminCredentialsCallback() {
            @Override
            public void onAdminCredentialsRetrieved(String adminId, String adminPassword) {
                assertEquals("abc", adminId);
                assertEquals("okay", adminPassword);
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
                latch.countDown();
            }
        });
        latch.await();  // wait for callback to be invoked
    }
}
