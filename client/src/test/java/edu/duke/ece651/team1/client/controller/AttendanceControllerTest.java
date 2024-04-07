package edu.duke.ece651.team1.client.controller; 

import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.AttendanceView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {
    @Mock
    private BufferedReader mockBufferedReader;
    @Mock
    private RestTemplate mockRestTemplate;
    @InjectMocks
    private AttendanceController controller;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() throws IOException {
        System.setOut(new PrintStream(outContent)); // Capture System.out prints
        mockBufferedReader = mock(BufferedReader.class);

        // Use Mockito.when to simulate sequential inputs.
        // Chain thenReturn calls for each expected input in sequence.
        when(mockBufferedReader.readLine())
            .thenReturn("take")  // First input
            .thenReturn("option1") // Possible next input based on logic
            .thenReturn("option2") // Another possible input
            .thenReturn("exit");   // Final input to exit or complete the operation

        controller = new AttendanceController(mockBufferedReader, System.out); // Inject mocked BufferedReader
    }
    @Disabled
    @Test
    void testStartAttendanceMenu() throws IOException {
        controller.startAttendanceMenue(); 

        String output = outContent.toString();
        assertTrue(output.contains("Taking attendance...")); // Assuming "Taking attendance..." is part of the expected output
    }
    @Disabled
    @Test
    void testGetRoaster() {
        // Create a dummy list of students as expected response
        List<Student> expectedStudents = List.of(new Student("John Doe"), new Student("Jane Doe"));
        ResponseEntity<List<Student>> mockedResponse = new ResponseEntity<>(expectedStudents, HttpStatus.OK);

        // Configure the mock to return the mockedResponse when exchange method is called
        when(mockRestTemplate.exchange(
                eq("http://testHost:8080/api/students/allStudents"),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Student>>() {})
        )).thenReturn(mockedResponse);

        // Execute the method under test
        Iterable<Student> result = controller.getRoaster();

        // Assertions to verify the expected behavior
        assertNotNull(result);
        assertEquals(expectedStudents.size(), ((List<Student>) result).size());
        assertEquals(expectedStudents.get(0).getLegalName(), ((List<Student>) result).get(0).getLegalName());
    }

}
