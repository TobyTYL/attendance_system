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
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {
    @Mock
    private BufferedReader inputReader;

    @Mock
    private PrintStream out;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private AttendanceView attendanceView;

    @InjectMocks
    private AttendanceController controller;
    private ByteArrayOutputStream outContent;
    //private AttendanceView attendanceView;
    //private AttendanceController controller;

    @BeforeEach
    void setUp() {
        // inputReader = mock(BufferedReader.class);
        // out = new PrintStream(new ByteArrayOutputStream());
        // restTemplate = mock(RestTemplate.class);
        // attendanceView = mock(AttendanceView.class);

        // controller = new AttendanceController(inputReader, out);
        // controller.setAttendanceView(attendanceView);
        // controller.setRestTemplate(restTemplate);
        outContent = new ByteArrayOutputStream();
        out = new PrintStream(outContent);
        UserSession.getInstance().setHost("localhost");
        UserSession.getInstance().setPort("8080"); // Use string if your port is managed as such
    }
   

}
