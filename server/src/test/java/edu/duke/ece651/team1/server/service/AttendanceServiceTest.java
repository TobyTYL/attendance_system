package edu.duke.ece651.team1.server.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AttendanceServiceTest {

    @Autowired
    AttendanceService service;  

    //@Value("${attendanceRecords.path}")
    //private String attendanceRecordsPath;
    

    @Test
    void testModifyStudentEntry() throws Exception {

        // Assuming the method to set the base path for attendance records
          AttendanceService service = new AttendanceService();
          //assertEquals("src/data/attendanceRecord/", service.getPath());

          String userName = "duke";
          String sessionDate = "2024-03-21";
          String attendanceEntryJson = "{\"Legal Name\":\"yitiao\", \"Attendance Status\":\"ABSENT\"}";
          
          
          Path testFilePath = Path.of("src/data/attendanceRecord/" + userName + "/Attendance-" + sessionDate + ".json");//find file path
          // Ensure the test file exists in 'src/test/resources/duke' directory
          assertTrue(Files.exists(testFilePath), "Test file does not exist: " + testFilePath);

          String result = service.modifyStudentEntry(userName, sessionDate, attendanceEntryJson);
          assertEquals("Successfully updated attendance status for yitiao", result);

          String content = Files.readString(testFilePath);
          System.out.println(content);
          //assertTrue(content.contains("\"yitiao\":{\"Display Name\":\"yitiao\",\"Email\":\"\",\"Attendance status\":\"ABSENT\"}"));
          assertTrue(content.contains("\"yitiao\":{\"Display Name\":\"yitiao\",\"Email\":\"\",\"Attendance status\":\"Absent\"}"),
                "The attendance record does not contain the expected entry.");
    }
}

