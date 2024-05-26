package com.example.se_3120_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Doctor extends AppCompatActivity {
    DatabaseReference databaseReference = ConnectToFireBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        final EditText Name = findViewById(R.id.doctorNameID);
        final EditText id = findViewById(R.id.doctoridID);
        final EditText password = findViewById(R.id.doctorPasswordID);
        final Button createBtn = findViewById(R.id.doctorButtonID);
        final Spinner spinner = findViewById(R.id.spinneID);
        final TextView textView = findViewById(R.id.texviewID);
        final String[] selectedValue = new String[1];

        // Load hospital names from Firebase and populate the spinner
        databaseReference.child("user").child("Hospital").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> stringList = new ArrayList<>();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String data = childSnapshot.child("Name").getValue(String.class);
                    if (data != null) {
                        Toast.makeText(Doctor.this, data, Toast.LENGTH_SHORT).show();
                        stringList.add(data);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Doctor.this, android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedValue[0] = (String) adapterView.getItemAtPosition(i);
                        textView.setText(selectedValue[0]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        selectedValue[0] = null;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Doctor.this, "Failed to load hospital data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Register a doctor on button click
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = Name.getText().toString().trim();
                String getID = id.getText().toString().trim();
                String getPass = password.getText().toString().trim();

                if (selectedValue[0] == null) {
                    Toast.makeText(Doctor.this, "Please select a hospital", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (getName.isEmpty() || getID.isEmpty() || getPass.isEmpty()) {
                    Toast.makeText(Doctor.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference doctorRef = databaseReference.child("user").child("Hospital").child(selectedValue[0]).child("Doctors");

                doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(getID)) {
                            doctorRef.child(getID).child("Name").setValue(getName);
                            doctorRef.child(getID).child("Password").setValue(getPass);
                            databaseReference.child("user").child("Doctors").child(getID).child("ID").setValue(getID);
                            databaseReference.child("user").child("Doctors").child(getID).child("Name").setValue(getName);
                            databaseReference.child("user").child("Doctors").child(getID).child("Serial");
                            databaseReference.child("user").child("Doctors").child(getID).child("Appointment").child("Today").child("Date").setValue(getCurrentDate());
                            databaseReference.child("user").child("Doctors").child(getID).child("Appointment").child("Tomorrow").child("Date").setValue(getNextDate());
                            databaseReference.child("user").child("Doctors").child(getID).child("Appointment").child("Today").child("Serial1").setValue(null);
                            databaseReference.child("user").child("Doctors").child(getID).child("Appointment").child("Tomorrow").child("Serial2").setValue(null);

                            Toast.makeText(Doctor.this, "Successfully Doctor ID Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Doctor.this, "Doctor ID isn't permitted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Doctor.this, "Failed to read data from Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String getNextDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}
