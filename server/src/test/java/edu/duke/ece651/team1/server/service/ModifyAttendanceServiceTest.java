package edu.duke.ece651.team1.server.service;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.duke.ece651.team1.shared.AttendanceStatus;

import java.nio.file.Files; // For Files.writeString and Files.readString
import org.json.JSONObject; // For handling JSON data

public class ModifyAttendanceServiceTest {
   @TempDir
    Path tempDir; // JUnit will inject a temporary directory here

    @Test
    public void testUpdateAttendanceStatus() throws Exception {
        // Set up the initial JSON content
        String initialJson = "{\"sessionDate\":\"2024-03-18\",\"Entries\":{\"yitiao\":{\"Display Name\":\"yitiao\",\"Email\":\"\",\"Attendance status\":\"Present\"},\"zhecheng\":{\"Display Name\":\"zhecheng\",\"Email\":\"\",\"Attendance status\":\"Present\"}}}";
        // Create and write to the temporary file
        Path tempFile = tempDir.resolve("attendance.json");
        Files.writeString(tempFile, initialJson);

        // Instance of the service to test
        ModifyAttendanceService service = new ModifyAttendanceService(tempFile.toString());
        // Update the attendance status of "yitiao"
        boolean updateResult = service.updateAttendance("yitiao", AttendanceStatus.ABSENT);

        // Read back the content of the file to verify the update
        String updatedContent = Files.readString(tempFile);
        JSONObject updatedJson = new JSONObject(updatedContent);

        String updatedStatus = updatedJson.getJSONObject("Entries").getJSONObject("yitiao").getString("Attendance status");

        // Assert that the status of "yitiao" was updated successfully and the method returned true
        assertTrue(updateResult);
        // Assert that the status of "yitiao" was updated to "Absent"
        assertEquals("ABSENT", updatedStatus);
    }
  
  
}
