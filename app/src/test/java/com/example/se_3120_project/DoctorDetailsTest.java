package com.example.se_3120_project;

import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import static org.junit.Assert.assertEquals;

public class DoctorDetailsTest {

    @Test
    public void testGetNextDate() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String expectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());

        // Act
        DoctorDetails doctorDetails = new DoctorDetails(); // Instantiate your DoctorDetails class
        String actualDate = doctorDetails.getNextDate();

        // Assert
        assertEquals(expectedDate, actualDate);
    }
}
