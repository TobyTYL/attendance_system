package edu.duke.ece651.team1.client.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.junit.jupiter.api.Disabled;

@ExtendWith(MockitoExtension.class)
@Disabled
public class AttendanceControllerTest {

    @Mock
    private BufferedReader inputReader;

    @Mock
    private RestTemplate restTemplate;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private AttendanceController controller;

    @BeforeEach
    public void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        // Assuming UserSession is appropriately initialized before tests
        // UserSession.getInstance().setHost("testHost");
        // UserSession.getInstance().setPort("8080");
        // UserSession.getInstance().setSessionToken("dummyToken");

        controller = new AttendanceController(inputReader, System.out);
    }

    @Test
    public void testFetchAttendance() throws Exception {
        when(inputReader.readLine()).thenReturn("2024-03-21");
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"attendance\":\"data\"}", HttpStatus.OK));

        controller.fetchAttendance();
         // Debugging: Print captured output
        System.err.println("Captured Output for Fetch Attendance: " + outContent.toString());
        //assertTrue(outContent.toString().contains("2024-03-21:"));
        assertTrue(outContent.toString().contains("Attendance records for 2024-03-21:"), "Expected output not found in fetchAttendance() method.");
      }

    @Test
    public void testModifyAttendance() throws Exception {
        when(inputReader.readLine()).thenReturn("John Doe", "Present");
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("Attendance updated successfully.", HttpStatus.OK));

        controller.modifyAttendance();

        // Debugging: Print captured output
        System.err.println("Captured Output for Modify Attendance: " + outContent.toString());
        assertTrue(outContent.toString().contains("Attendance updated successfully."), "Expected output not found in modifyAttendance() method.");
    }
}
