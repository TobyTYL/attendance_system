package edu.duke.ece651.team1.server.service;                                              

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import edu.duke.ece651.team1.shared.AttendanceStatus;

public class ModifyAttendanceService{
    private String filePath;

    public ModifyAttendanceService(String filePath){
        this.filePath = filePath;
    }

    public boolean updateAttendance(String name, AttendanceStatus status){
        try {
            // Read the existing JSON content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONObject entries = jsonObject.getJSONObject("Entries");
            //boolean found = false;
            // // Check if the specified student exists in the "Entries" object
            // if (!jsonObject.getJSONObject("Entries").has(name)) {
            //     System.err.println("Student not found: " + name);
            //     return false;
            // }

            // // Update the attendance status for the specified student
            // jsonObject.getJSONObject("Entries").getJSONObject(name).put("Attendance status", status);
            if (entries.has(name)) {
                // If the student is found, update the "Attendance status" for that student
                entries.getJSONObject(name).put("Attendance status", status.toString());

                // Write the updated JSON back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(jsonObject.toString(4)); // Write with indentation for readability
                }
                return true; // Update was successful
            } else {
                // If the student name (key) is not found in "Entries", print an error and return false
                System.err.println("Student name not found: " + name);
                return false;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while updating attendance: " + e.getMessage());
            return false;
        }
    }
}
