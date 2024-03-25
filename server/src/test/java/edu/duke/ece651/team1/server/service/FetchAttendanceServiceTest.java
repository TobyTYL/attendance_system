// package edu.duke.ece651.team1.server.service;

// import static org.junit.jupiter.api.Assertions.*;

// import java.io.BufferedReader;
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.io.PrintStream;
// import java.io.StringReader;

// import org.junit.jupiter.api.Test;

// //import edu.duke.ece651.team1.shared.FetchAttendanceService;

// public class FetchAttendanceServiceTest {  
//   @Test
//   public void test_displayJsonFile() {
//     FetchAttendanceService testFetch = new FetchAttendanceService();
//     //testFetch.displayJsonFile(attendanceRecordPath);
//     //testFetch.displayJsonFile("../../../../../../../../data/attendanceRecord/Attendance-2024-03-18.json");
//     testFetch.displayJsonFile("./src/data/attendanceRecord/Attendance-2024-03-18.json");
//     //testFetch.displayJsonFile("/home/ww202/project-team-1/server/src/data/attendanceRecord/Attendance-2024-03-18.json");
//   }
//   @Test
//   public void test_displayJsonFileInvalidPath() {
//     FetchAttendanceService testFetch = new FetchAttendanceService();
//     //testFetch.displayJsonFile(attendanceRecordPath);
//     testFetch.displayJsonFile("../../../../../../../../data/attendanceRecord/Attendance-1900-03-18.json");
//   }
//   @Test
//     void testPromptDateAndDisplayAttendance() {
//         BufferedReader in = new BufferedReader(new StringReader("2024-03-18\n"));
//         ByteArrayOutputStream baos = new ByteArrayOutputStream();
//         PrintStream out = new PrintStream(baos);

//         FetchAttendanceService service = new FetchAttendanceService();
//         service.promptDateAndDisplayAttendance(in, out);

//         String output = baos.toString();
//         assertTrue(output.contains("Enter the date (YYYY-MM-DD):"));
//         // Further assertions can be made based on the expected output
//         // Note: This test might need to be adjusted based on the actual implementation of displayJsonFile
//     }
  
// }
