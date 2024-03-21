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

    public boolean updateAttendance(String Id, AttendanceStatus status){
        try {
            // Read the existing JSON content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            
            // Check if the specified student exists in the "Entries" object
            if (!jsonObject.getJSONObject("Entries").has(Id)) {
                System.err.println("Student ID not found: " + Id);
                return false;
            }

            // Update the attendance status for the specified student
            jsonObject.getJSONObject("Entries").getJSONObject(Id).put("Attendance status", status);

            // Write the updated JSON back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(jsonObject.toString(4)); // Writing with indentation for readability
            }

            return true; // Update was successful
        } catch (IOException e) {
            System.err.println("An error occurred while updating attendance: " + e.getMessage());
            return false;
        }
    }
}
