package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class JsonAttendanceRecordExporterTest {

    @TempDir
    Path tempDir; // JUnit 5 temporary directory feature

    @Test
    public void testExportToFile() throws IOException {
        // Assuming AttendanceRecord and JsonAttendanceSerializer are implemented correctly
        AttendanceRecord record = new AttendanceRecord(LocalDate.of(2024, 03, 26)); // Populate your AttendanceRecord as necessary
        JsonAttendanceRecordExporter exporter = new JsonAttendanceRecordExporter();

        String filename = "attendance";
        String filePath = tempDir.toString() + File.separator;

        // Call the method under test
        exporter.exportToFile(record, filename, filePath);

        // Verify the file was created
        File outputFile = new File(filePath + filename + ".json");
        assertTrue(outputFile.exists(), "The file should have been created.");

        // Verify the file content (simplified)
        String content = readFileContent(outputFile);
        // Assuming the serialize method of JsonAttendanceSerializer outputs some identifiable content based on the record
        //String expectedContent = "{\"sessionDate\":\"2024-03-26\",\"Entries\":{}}";
        //assertEquals(expectedContent, content, "The file content does not match expected JSON content.");
        assertTrue(content.contains("\"sessionDate\""), "The file content should contain the sessionDate field.");
        assertTrue(content.contains("\"Entries\""), "The file content should contain the Entries field.");
    }
    @Test
    public void testExportToFileWithNonExistentParentDir() throws IOException {
        // Create a test AttendanceRecord
        AttendanceRecord record = new AttendanceRecord(); // Make sure this is properly populated

        JsonAttendanceRecordExporter exporter = new JsonAttendanceRecordExporter();
        
        // Use a non-existent directory path
        String nonExistentDir = "nonExistentDir";
        String filename = "attendance";
        String filePath = tempDir.resolve(nonExistentDir).toString() + File.separator;

        // Call the method under test
        exporter.exportToFile(record, filename, filePath);

        // Verify the file was created in the new directory
        File outputFile = new File(filePath + filename + ".json");
        assertTrue(outputFile.exists(), "The file should have been created.");
        assertTrue(outputFile.getParentFile().exists(), "Parent directory should have been created.");

        // Optionally, verify file content if necessary
        String content = readFileContent(outputFile);
        assertNotNull(content, "File content should not be null");
        // Further assertions can be added here to verify the content
    }

    // Utility method to read file content
    private static String readFileContent(File file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }
        return contentBuilder.toString().trim(); // Trim to remove the last newline character
    }
}

