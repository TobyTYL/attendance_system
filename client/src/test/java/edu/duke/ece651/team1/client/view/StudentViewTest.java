package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team1.shared.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


public class StudentViewTest {
    private BufferedReader inputReader;
    private PrintStream out;
    private StudentView studentView;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);
        studentView = new StudentView(inputReader, out);
    }

    @Test
    void testGetInputReader() {
        // Call the method under test
        BufferedReader returnedReader = studentView.getInputReader();
        
        // Assert that the returned BufferedReader is the same as the mock
        assertSame(inputReader, returnedReader, "The getInputReader method should return the instance passed in the constructor.");
    }
    
    @Test
    void testGetOut() {
        // Call the method under test
        PrintStream returnedOut = studentView.getOut();
        
        // Assert that the returned PrintStream is the same as the mock
        assertSame(out, returnedOut, "The getOut method should return the instance passed in the constructor.");
    }
    @Test
    void testShowStudentMenu() {
        studentView.showStudentMenu();
        verify(out).println("Please select an option:");
        verify(out).println("1. Add a student");
        verify(out).println("2. Remove a student");
        verify(out).println("3. Load a Student info CSV file");
        verify(out).println("4. Edit a student display name");
        verify(out).println("5. Back to main menue");
    }
    @Test
    void testReadStudentOptionAdd() throws IOException {
        when(inputReader.readLine()).thenReturn("1");
        assertEquals("add", studentView.readStudentOption());
    }

    @Test
    void testReadStudentOptionBack() throws IOException {
        when(inputReader.readLine()).thenReturn("5");
        assertEquals("back", studentView.readStudentOption());
    }

    @Test
    void testReadStudentOptionInvalid() throws IOException {
        when(inputReader.readLine()).thenThrow(new IOException());
        assertThrows(IOException.class, () -> studentView.readStudentOption());
    }
    @Test
    void testReadStudentName() throws IOException {
        when(inputReader.readLine()).thenReturn("John Doe");
        assertEquals("John Doe", studentView.readStudentName().trim());
    }

    @Test
    void testGetFileNameEOFException() {
        assertThrows(EOFException.class, () -> {
            when(inputReader.readLine()).thenReturn(null);
            studentView.getFileName();
        });
    }
    @Test
    void testShowSuccessEditNameMessage() {
        studentView.showSuccessEditNameMessage("John Doe", "JD");
        verify(out).println("You successfully edit John Doe's display name to JD");
    }

    @Test
    void testShowSuccessAddDisplayNameMessage() {
        studentView.showSuccessAddDisplayNameMessage("John Doe", "Present");
        verify(out).println("You successfully added John Doe display name to this class Present");
    }

    @Test
    void testShowSuccessRemoveMessage() {
        studentView.showSuccessRemoveMessage("John Doe");
        verify(out).println("You successfully removed John Doe");
    }


    @Test
    void testReadStudentDisplayName() throws IOException {
        when(inputReader.readLine()).thenReturn("JD");
        assertEquals("JD", studentView.readStudentDisplayName());
        verify(out).println("Enter the display name of the student:");
    }

    @Test
    void testReadStudentEmail() throws IOException {
        when(inputReader.readLine()).thenReturn("john.doe@example.com");
        assertEquals("john.doe@example.com", studentView.readStudentEmail());
        verify(out).println("Enter the email of the student:");
    }
    @Test
    void testGetFileName() throws IOException {
        when(inputReader.readLine()).thenReturn("students.csv");
        assertEquals("students.csv", studentView.getFileName());
    }

      @Test
      void testReadStudentOption() throws IOException {
          when(inputReader.readLine()).thenReturn("1", "2", "3", "4", "5");
          assertEquals("add", studentView.readStudentOption());
          assertEquals("remove", studentView.readStudentOption());
          assertEquals("load", studentView.readStudentOption());
          assertEquals("edit", studentView.readStudentOption());
          assertEquals("back", studentView.readStudentOption());
      }
      @Test
      void testShowLoadSuccessMessage() {
          studentView.showLoadSuccessMessage();
          verify(out).println("You have successfully Load students from csv file");
          verify(out).println("Please check the information below");
      }
      @Test
      void testShowStudentNotFoundMessage() {
          studentView.showStudentNotFoundMessage("remove");
          verify(out).println("The student you want to remove was not found");
      }

      @Test
      void testDisplayStudentList() {
          List<Student> students = List.of(
              new Student("LegalName1", "DisplayName1", "Email1"),
              new Student("LegalName2", "DisplayName2", "Email2")
          );
          studentView.displayStudentList(students);
          
          verify(out).println("Legal Name   Display Name   Email");
          verify(out).println("=====================================");
          
          // Adjusted verification to match actual invocation pattern
          // Note: This assumes your students are printed with a single string argument per student.
          verify(out).printf("LegalName1, DisplayName1, Email1\n");
          verify(out).printf("LegalName2, DisplayName2, Email2\n");
      }



}
