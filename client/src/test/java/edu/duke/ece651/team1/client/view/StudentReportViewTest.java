package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentReportViewTest {
    private BufferedReader mockedInputReader;
    private PrintStream mockedOut;
    private StudentReportView studentReportView;

    @BeforeEach
    void setup() {
        mockedInputReader = mock(BufferedReader.class);
        mockedOut = mock(PrintStream.class);
        studentReportView = new StudentReportView(mockedInputReader, mockedOut);
    }

    @Test
    public void testShowReportMenu() {
        studentReportView.showReportMenue("ECE651");
        verify(mockedOut).println("You are viewing reports for: ECE651.");
        verify(mockedOut).println("Please choose the type of report:");
        verify(mockedOut).println("1. Summary Report");
        verify(mockedOut).println("2. Detailed Report");
        verify(mockedOut).println("3. Go back");
    }

    @Test
    public void testReadReportOptionSummary() throws IOException {
        when(mockedInputReader.readLine()).thenReturn("1","2","3","4","3");
        assertEquals("summary", studentReportView.readReportOpetion());
        assertEquals("detail", studentReportView.readReportOpetion());
        assertEquals("back", studentReportView.readReportOpetion());
        assertEquals("back", studentReportView.readReportOpetion());
        verify(mockedOut).println("Invalid Option: Please try agin");
    }

  

    @Test
    public void testShowReport() {
        String className = "ECE651";
        String reportContent = "Attendance: 95%";
        studentReportView.showReport(reportContent, className);
        verify(mockedOut).printf("=============== Attendance Report for class: %s ===============%n", className);
        verify(mockedOut).println(reportContent);
        verify(mockedOut).println("=============================================================================");
    }

}
