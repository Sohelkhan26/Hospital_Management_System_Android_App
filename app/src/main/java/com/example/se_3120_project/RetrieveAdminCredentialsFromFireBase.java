package com.example.se_3120_project;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RetrieveAdminCredentialsFromFireBase {
    DatabaseReference databaseReference = ConnectToFireBase.getInstance();
    public String admin_id , admin_password;
    public String error_message;
    interface AdminCredentialsCallback {
        void onAdminCredentialsRetrieved(String adminId, String adminPassword);
        void onError(String errorMessage);
    }

    public void getAdminCredentials(AdminCredentialsCallback callback) {
        databaseReference.child("user").child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_id = snapshot.child("id").getValue(String.class);
                admin_password = snapshot.child(admin_id).child("Password").getValue(String.class);
                callback.onAdminCredentialsRetrieved(admin_id, admin_password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error_message = "Error: "+ error.getMessage();
                callback.onError(error_message);
            }
        });
    }
}
