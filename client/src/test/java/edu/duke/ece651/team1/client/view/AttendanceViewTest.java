package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.EOFException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.*; 
import java.io.EOFException;

import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AttendanceViewTest {
    @Mock
    private BufferedReader inputReader;
    
    @Mock
    private PrintStream out;

    private AttendanceView attendanceView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attendanceView = new AttendanceView(inputReader, out);
    }

    @Test
    void testShowTakeAttendanceMenu() {
        String sessionDate = "2023-03-27";
        attendanceView.showTakeAttendanceMenu(sessionDate);
        verify(out).printf("=============== Take Attendance for session: %s ===============%n", sessionDate);
        verify(out, times(6)).println(anyString());
    }

    @Test
    void testPromptForStudentAttendance() throws IOException {
        when(inputReader.readLine()).thenReturn("P");
        String studentName = "John Doe";
        String response = attendanceView.promptForStudentAttendance(studentName,true);
        verify(out).println("Student Name: " + studentName);
        verify(out).println("Press 'P' to mark Present, 'A' to mark Absent:");
        assertEquals("P", response);
    }
    @Test
    void testPromptForStudentAttendanceRetake() throws IOException {
        when(inputReader.readLine()).thenReturn("P");
        String studentName = "John Doe";
        String response = attendanceView.promptForStudentAttendance(studentName,false);
        verify(out).println("Student Name: " + studentName);
        verify(out).println("Press 'A' to mark Absent, Press 'P' to mark Present, 'T' to mark Tardy:");
        assertEquals("P", response);
    }
    @Test
    void testPromptForStudentAttendanceWithInvalidFormat() {
      String studentName = "John Doe";
      try {
          when(inputReader.readLine()).thenReturn("X"); // Invalid input
          attendanceView.promptForStudentAttendance(studentName,true);
          fail("Should have thrown IllegalArgumentException for invalid format.");
      } catch (IOException e) {
          fail("IOException should not have occurred.");
      } catch (IllegalArgumentException e) {
          assertEquals("That action is invalid: it does not have the correct format.", e.getMessage());
      }
  }

    @Test
    void testShowMarkSuccessMessage() {
        String student = "John Doe";
        String status = "Present";
        attendanceView.showMarkSuccessMessage(student, status);
        verify(out).println("You successfully marked " + student + " to " + status);
    }
    @Test
    void testShowFinalAttendanceRecord() {
        LocalDate sessionDate = LocalDate.parse("2023-03-28");
        AttendanceRecord record = new AttendanceRecord(sessionDate);
        Student student1 = new Student("John Doe", "John", "123");
        Student student2 = new Student("Jane Smith", "Jane", "456");

        record.initializeAttendanceEntry(student1);
        record.initializeAttendanceEntry(student2);
        record.markPresent(student1);
        record.markAbsent(student2); // Assuming there's a way to mark a student as absent.

        // Execute the method under test
        attendanceView.showFinalAttendanceRecord(record);

        // Verify the output
        verify(out).println("Attendance Record for 2023-03-28");
        verify(out, times(2)).println("=====================================");
        // Assuming the sorting by display name places "Jane Doe" before "John Doe"
        // and the AttendanceRecord.toString() method matches this format.
        verify(out).println("Jane: Absent");
        verify(out).println("John: Present");
        verify(out, times(2)).println("=====================================");
    }


    @Test
    void testShowAttendanceFinishMessage() {
        LocalDate sessionDate = LocalDate.parse("2023-03-28");
        AttendanceRecord record = new AttendanceRecord(sessionDate);
        Student student1 = new Student("John Doe", "John", "123");
        record.initializeAttendanceEntry(student1);
        record.markPresent(student1);
        attendanceView.showAttenceFinishMessage(record);
        verify(out).println("Attendance marking completed.");
        verify(out).println("Attendance records List blow:");
    }
    @Test
    void testShowAttendanceUpdateMessage() {
        LocalDate sessionDate = LocalDate.parse("2023-03-28");
        AttendanceRecord record = new AttendanceRecord(sessionDate);
        Student student1 = new Student("John Doe", "John", "123");
        record.initializeAttendanceEntry(student1);
        record.markPresent(student1);
        attendanceView.showUpdateFinishMessage(record);
        verify(out).println("Attendance update completed.");
        verify(out).println("New Attendance records List blow:");
    }

    @Test
    void testShowModifySuccessMessage() {
        String student = "Jane Doe";
        String status = "Absent";
        attendanceView.showModifySuccessMessage(student, status);
        verify(out).println("You successfully marked " + student + " to " + status);
    }

    @Test
    void testPromptForDateSelectionWithEmptyList() throws IOException {
        List<String> dates = new ArrayList<>();
        when(inputReader.readLine()).thenReturn("back");
        String result = attendanceView.promptForDateSelection(dates);
        assertEquals("back", result);
        verify(out).println("No Attendance record available. Please try to take attendance first");
    }

    @Test
    void testPromptForDateSelectionWithInvalidSelection() throws IOException {
        List<String> dates = List.of("2023-03-28", "2023-03-29");
        when(inputReader.readLine()).thenReturn("3", "back"); // First input invalid, then exit
        String result = attendanceView.promptForDateSelection(dates);
        assertEquals("back", result);
        verify(out, times(1)).println("Invalid selection. Please try again.");
    }

    @Test
    void testPromptForDateSelectionValid() throws IOException {
        List<String> dates = List.of("2023-03-28", "2023-03-29");
        when(inputReader.readLine()).thenReturn("1"); // Valid selection
        String result = attendanceView.promptForDateSelection(dates);
        assertEquals("2023-03-28", result); // Assuming valid selection returns the date
    }
    @Test
    void testPromptForStudentNameWithEmptyRecord() throws IOException {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        when(inputReader.readLine()).thenReturn("back");
        String result = attendanceView.promptForStudentName(record);
        assertEquals("back", result);
        verify(out).println("No students available for attendance modification.");
    }
    @Test
    void testPromptForStudentNameNotFound() throws IOException {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student = new Student("Jane Doe", "Jane", "123");
        record.initializeAttendanceEntry(student);
        when(inputReader.readLine()).thenReturn("John Doe", "back");
        String result = attendanceView.promptForStudentName(record);
        assertEquals("back", result);
        verify(out).println("Student name not found in the record. Please try again.");
    }

    
    @Test
    void testPromptForStudentNameBackInput() throws IOException {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student = new Student("Jane Doe", "Jane", "jane@example.com");
        record.initializeAttendanceEntry(student);
        when(inputReader.readLine()).thenReturn("back");
        String result = attendanceView.promptForStudentName(record);
        assertEquals("back", result);
        verify(out, atLeastOnce()).println("Please select a student to modify their attendance status:");
    }
    
    @Test
    void testPromptForStudentNameFound() throws IOException {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student = new Student("Jane Doe", "Jane", "jane@example.com");
        record.initializeAttendanceEntry(student);
        when(inputReader.readLine()).thenReturn("Jane");
        String result = attendanceView.promptForStudentName(record);
        assertEquals("Jane Doe", result); // Expecting the legal name as return value
    }
    
    @Test
    void testPromptForAttendanceStatusWithInvalidInput() throws IOException {
        when(inputReader.readLine()).thenReturn("X", "P"); // First input invalid, second valid
        AttendanceStatus status = attendanceView.promptForAttendanceStatus();
        assertEquals(AttendanceStatus.PRESENT, status);
        verify(out).println("Invalid status. Please enter P for Present or T for Tardy.");
    }

    @Test
    void testPromptForAttendanceStatusWithValidInput() throws IOException {
        when(inputReader.readLine()).thenReturn("T");
        AttendanceStatus status = attendanceView.promptForAttendanceStatus();
        assertEquals(AttendanceStatus.TARDY, status);
    }
    @Test
    void testReadAttendanceOption() throws IOException {
        when(inputReader.readLine()).thenReturn("1", "2", "3", "4","5");
        assertEquals("take", attendanceView.readAttendanceOption(), "Option 1 should return 'take'");
        assertEquals("modify", attendanceView.readAttendanceOption(), "Option 2 should return 'modify'");
        assertEquals("export", attendanceView.readAttendanceOption(), "Option 3 should return 'export'");
        assertEquals("report", attendanceView.readAttendanceOption(), "option4 should return 'report'");
        assertEquals("back",attendanceView.readAttendanceOption(),"option 5 should back");
    }
    @Test
    void testShowAttendanceManageOption() {
        attendanceView.showAttendanceManageOption();
        verify(out).println("Please select an option to begin:");
        verify(out).println("1. Take attendance for today's class");
        verify(out).println("2. Edit your previous attendance");
        verify(out).println("3. Export attendance records");
        verify(out).println("4. Obtain class Attendance report ");
        verify(out).println("5. Go back");
    }
    @Test
    void testShowExportOption() {
        attendanceView.showExportOption();
        verify(out).println("Please select an export format:");
        verify(out).println("1. Export as JSON");
        verify(out).println("2. Export as XML");
        verify(out).println("3. Export as CSV");
        verify(out).println("4. Back to attendance Record List");
    }
    @Test
    void testShowExportSuccessMessage() {
        String fileName = "attendance.json";
        attendanceView.showExportSuccessMessage(fileName);
        verify(out).println("Export successful. Your file has been saved to client/src/data directory/" + fileName);
    }
    @Test
    void testShowEmptyDatesMessage() {
        attendanceView.showEmptyDatesMessage();
        verify(out).println("No Attendance record available. Please try to take attendance first");
    }

    @Test
    void testShowAttendanceDateList() {
        List<String> dates = Arrays.asList("2023-03-28", "2023-04-01");
        attendanceView.showAttendanceDateList(dates);
        verify(out).println("1. 2023-03-28");
        verify(out).println("2. 2023-04-01");
    }
    @Test
    void testReadExportDateFromPromptWithValidSelection() throws IOException {
        List<String> dates = Arrays.asList("2023-03-28", "2023-04-01");
        when(inputReader.readLine()).thenReturn("1");
        String result = attendanceView.readExportDateFromPrompt(dates);
        assertEquals("2023-03-28", result);
    }

    @Test
    void testReadExportDateFromPromptWithBackSelection() throws IOException {
        List<String> dates = Arrays.asList("2023-03-28", "2023-04-01");
        when(inputReader.readLine()).thenReturn(String.valueOf(dates.size() + 1)); // User chooses to go back
        String result = attendanceView.readExportDateFromPrompt(dates);
        assertEquals("back", result);
    }

    @Test
    void testReadExportDateFromPromptEmptyList() throws IOException {
        List<String> dates = new ArrayList<>();
        when(inputReader.readLine()).thenReturn("1"); // Attempt to select a date when the list is empty
        String result = attendanceView.readExportDateFromPrompt(dates);
        assertEquals("back", result); // Expecting "back" because the list is empty
        verify(out).println("No Attendance record available. Please try to take attendance first");
    }
    @Test
    void testReadFormatFromPrompt() throws IOException {
        when(inputReader.readLine()).thenReturn("1", "2", "3", "4");
        assertEquals("json", attendanceView.readFormtFromPrompt());
        assertEquals("xml", attendanceView.readFormtFromPrompt());
        assertEquals("csv", attendanceView.readFormtFromPrompt());
        assertEquals("back", attendanceView.readFormtFromPrompt());
    }
    @Test
    void testShowNoRosterMessage() {
        attendanceView.showNoRosterMessage();
        verify(out).println("You cannot take attendance Now, please back to Student Management Menue");
        verify(out).println("Load a student Roster first");
    }
    @Test
    void testPromptForStudentAttendanceEOFException() {
        assertThrows(EOFException.class, () -> {
            when(inputReader.readLine()).thenReturn(null); // Simulate EOF
            attendanceView.promptForStudentAttendance("John Doe",true);
        });
    }



    @Test
    void testPromptForDateSelectionWithInvalidNumberInput() throws IOException {
        // Setup: a list of dates to select from
        List<String> dates = Arrays.asList("2023-03-28", "2023-04-01");
        // Simulate user inputs: first an invalid number input, then "back" to exit the loop
        when(inputReader.readLine()).thenReturn("not a number", "back");
        
        // Execution: invoke the method under test
        String result = attendanceView.promptForDateSelection(dates);
        
        // Assertions: verify that the method handles the invalid input as expected
        assertEquals("back", result, "Method should return 'back' after invalid input and then 'back'");
        // Verify that the specific error message is printed after the invalid input
        verify(out).println("Please enter a valid number.");
        // Optionally, verify that the prompt is shown again after the invalid input
        verify(out, times(2)).println("Please select the date of the attendance record you want to modify:");
    }

    @Test
    void testPromptForAttendanceStatusWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            when(inputReader.readLine()).thenReturn(null); // Simulate null input
            attendanceView.promptForAttendanceStatus();
        });
        verify(out).println("Enter the new attendance status (P for Present, T for Tardy):");
    }

    @Test
    void testReadExportDateFromPromptWithInvalidOption() throws IOException {
        // Setup: Assuming you have a setup where getUserOption behavior can be simulated
        List<String> dates = Arrays.asList("2023-03-28", "2023-04-01");
        
        // Mock inputReader to trigger the condition leading to an IllegalArgumentException
        // and eventually to select "back" to exit the loop
        when(inputReader.readLine())
            .thenThrow(new IllegalArgumentException("Simulated invalid option"))
            .thenReturn(String.valueOf(dates.size() + 1)); // User chooses to go back

        // Execute the method
        String result = attendanceView.readExportDateFromPrompt(dates);

        // Validate that the method returned "back" indicating the loop exited after the exception handling
        assertEquals("back", result);

        // Verify that the specific error message is printed
        verify(out).println("Invalid option for Export Menue.");
    }

    @Test
    void testReadFormatFromPromptWithInvalidOption() throws IOException {
        // Assuming the setup allows mocking or simulating the behavior of getUserOption
        // First, simulate the throwing of IllegalArgumentException to trigger the catch block
        // Then, provide a valid "back" option to exit the loop
        when(inputReader.readLine())
            .thenThrow(new IllegalArgumentException("Simulated invalid option"))
            .thenReturn("4"); // Assuming the option for "back" is 4

        // Execute the method under test
        String result = attendanceView.readFormtFromPrompt();

        // Validate that the method handled the exception and proceeded as expected
        assertEquals("back", result, "Method should return 'back' after handling the exception.");

        // Verify that the correct error message is printed to the user
        verify(out).println("Invalid option for Export format");
    }

    @Test
    void testUserSelects() throws IOException {
        when(inputReader.readLine()).thenReturn("1","2");
        assertEquals("retake", attendanceView.readModifyOption());
        assertEquals("modify", attendanceView.readModifyOption());
    }

    @Test
    void testUserEntersInvalidThenValidOption() throws IOException {
        when(inputReader.readLine()).thenReturn("invalid", "2");
        assertEquals("modify", attendanceView.readModifyOption());
        verify(out).println("Invalid option for Modification");
    }
    @Test
    void testShowModifyMenu() {
        attendanceView.showModifyMenue();
        verify(out).println("Would you like to:");
        verify(out).println("1. Retake entire class attendance");
        verify(out).println("2. Modify attendance for a specific student");
    }

    @Test
    void testShowClassReport() {
        String reportContent = "John Doe: Present";
        attendanceView.showClassReport(reportContent);
        verify(out).println("=============== Attendance Participation for class:  ===============");
        verify(out).println();
        verify(out).println(reportContent);
        verify(out).println("=====================================================================");
    }
    
    @Test
    void testShowUpdateSuccessMessage() {
        String studentName = "John Doe";
        String status = "Present";
        attendanceView.showUpdateSuccessMessage(studentName, status);
        verify(out).println("You successfully update " + studentName + " attendance status to " + status);
    }

}

    
