package edu.duke.ece651.team1.client.controller; 

import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.client.view.AttendanceView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceControllerTest {

    private BufferedReader inputReader;
    private PrintStream out;
    private AttendanceController controller;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);
        controller = new AttendanceController(inputReader, out);
    }

    @Test
    void testConstructorInitializesFields() {
        assertNotNull(controller.attendanceView, "attendanceView should be initialized");
        assertNotNull(controller.restTemplate, "restTemplate should be initialized");
    }

    @Test
    void testStartAttendanceMenuOptionTake() throws IOException {
        // Assuming readAttendanceOption() is the method that ultimately calls inputReader.readLine()
        AttendanceView mockView = mock(AttendanceView.class);
        when(mockView.readAttendanceOption()).thenReturn("take"); // Mock the higher-level method instead
        controller.attendanceView = mockView;
    
        controller.startAttendanceMenue();
    
        verify(mockView, times(1)).showAttendanceManageOption();
    
        // Add more verifications for method calls inside startAttendance
        // For example, verifying that startAttendance() was called
    }
    
}
