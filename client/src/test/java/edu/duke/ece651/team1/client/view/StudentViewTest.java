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
    private BufferedReader mockedInputReader;
    private PrintStream mockedOut;
    private StudentView studentView;

    @BeforeEach
    void setUp() {
        mockedInputReader = mock(BufferedReader.class);
        mockedOut = mock(PrintStream.class);
        studentView = new StudentView(mockedInputReader, mockedOut);
    }

    @Test
    public void testShowStudentMenu() {
        studentView.showStudentMenu();
        verify(mockedOut).println("Please select an option:");
        verify(mockedOut).println("1. Manage Notification Preferences");
        verify(mockedOut).println("2. View Attendance Reports");
        verify(mockedOut).println("3. Go back");
    }

    @Test
    public void testReadStudentOption() throws IOException {
        when(mockedInputReader.readLine()).thenReturn("1", "2", "3");
        assertEquals("notification", studentView.readStudentOption());
        assertEquals("report", studentView.readStudentOption());
        assertEquals("back", studentView.readStudentOption());
    }
    @Test 
    public void ttestReadStudentOptionInvalid() throws IOException{
        when(mockedInputReader.readLine())
            .thenReturn("invalid") // Simulate invalid input
            .thenReturn("1"); // Simulate valid input on retry
        String result = studentView.readStudentOption();
        assertEquals("notification", result);
        verify(mockedOut).println("Invalid Option: Please try agin");
    }



    

    @Test
    public void testShowReportMenu() {
        studentView.showReportMenue("Sample Course");
        verify(mockedOut).println("You are viewing reports for: Sample Course.");
        verify(mockedOut).println("Please choose the type of report:");
        verify(mockedOut).println("1. Summary Report");
        verify(mockedOut).println("2. Detailed Report");
        verify(mockedOut).println("3. Go back");
    }

    @Test
    public void testReadReportOption() throws IOException {
        BufferedReader mockedInputReader = mock(BufferedReader.class);
        PrintStream mockedOut = mock(PrintStream.class);
        StudentView studentView = new StudentView(mockedInputReader, mockedOut);
        when(mockedInputReader.readLine()).thenReturn("1", "2", "3");
        assertEquals("summary", studentView.readReportOpetion());
        assertEquals("detail", studentView.readReportOpetion());
        assertEquals("back", studentView.readReportOpetion());
    }

    @Test 
    public void testReadReportOptionInvalid() throws IOException{
        when(mockedInputReader.readLine())
            .thenReturn("invalid") // Simulate invalid input
            .thenReturn("1"); // Simulate valid input on retry
        String result = studentView.readReportOpetion();
        assertEquals("summary", result);
        verify(mockedOut).println("Invalid Option: Please try agin");
    }


}
