package edu.duke.ece651.team1.shared;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

public class CsvAttendanceRecordExporterTest {
    CsvAttendanceRecordExporter exporter = new CsvAttendanceRecordExporter();
    AttendanceRecord record = new AttendanceRecord();
    List<Student> students = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @TempDir
    Path tempDir;
    
    @Test
    public void convertToCsvFormatTest(){
        Student student1 = new Student("John", "Doe","john.com");
        Student student2 = new Student("Alice", "Smith","alice.com");
        students.add(student1);
        students.add(student2);
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        record.markAbsent(student2);
        String expectedCsv = "sessionDate,"+LocalDateTime.now().format(formatter)+"\n"+
                            "Legal Name,display Name,Email,Attendance Status"+"\n"+
                            "John,Doe,john.com,Present"+"\n"+
                            "Alice,Smith,alice.com,Absent";
        List<String[]> dataLines =exporter.convertToCsvFormat(record);
         String actualCsv = dataLines.stream()
                                    .map(data -> exporter.convertToCSV(data))
                                    .collect(Collectors.joining("\n"));
        assertEquals(expectedCsv, actualCsv);


    }
    @Test
    public void testExportToFile() throws IOException {
        // Setup test data as in previous test

        CsvAttendanceRecordExporter exporter = new CsvAttendanceRecordExporter();
        String filename = "testAttendanceRecord";
        String filePath = tempDir.toString();

        exporter.exportToFile(record, filename, filePath);

        // Verify file contents
        File file = new File(tempDir.toFile(), filename + ".csv");
        assertTrue(file.exists());

        // Read file and assert content
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
            }
        }
    }
}
