package edu.duke.ece651.team1.client.controller;

import edu.duke.ece651.team1.client.view.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import org.springframework.core.ParameterizedTypeReference;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import edu.duke.ece651.team1.client.model.*;

// import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @Mock
    private BufferedReader inputReader;

    @Mock
    private PrintStream out;

    private StudentController studentController;
    @Mock
    private UserSession userSession;
    private MockedStatic<ControllerUtils> utils;

    @BeforeEach
    void setUp() {
        // Assuming classId, sectionId, className, and studentId are initialized
        // correctly
        int sectionId = 1;
        int classId = 1;
        String className = "ECE101";
        int studentId = 123;
        userSession.setHost("localhost");
        userSession.setPort("8080");
        utils = Mockito.mockStatic(ControllerUtils.class);
        studentController = new StudentController(inputReader, out, sectionId, classId, className, studentId);
        String notificationUrl = String.format("http://%s:%s/api/students/notification/%d/%d",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), 123, 1);
        String coursesUrl = String.format("http://%s:%s/api/class/student/allclasses/?studentId=%d",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), 123);
        String notificationUrl_update = String.format("http://%s:%s/api/students/notification/%d/%d/?preference=%s",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, 123, false);
        String reportUrl_detail =String.format("http://%s:%s/api/attendance/report/student/%d/%d/?detail=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, sectionId,true);
        String reportUrl_summary =String.format("http://%s:%s/api/attendance/report/student/%d/%d/?detail=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, sectionId,false);


        // studentController.studentView = studentView;
        when(ControllerUtils.executeGetRequest(eq(notificationUrl), any(ParameterizedTypeReference.class)))
                .thenReturn("{\"ReceiveNotifications\": true}");
        when(ControllerUtils.executeGetRequest(eq(coursesUrl), any(ParameterizedTypeReference.class)))
                .thenReturn(List.of("{\"courseId\":101,\"courseName\":\"Intro to Testing\",\"sectionId\":1}"));
        when(ControllerUtils.executeGetRequest(eq(reportUrl_detail), any(ParameterizedTypeReference.class))).thenReturn("detail report");
        when(ControllerUtils.executeGetRequest(eq(reportUrl_summary), any(ParameterizedTypeReference.class))).thenReturn("summary report");
       
    }

    @AfterEach
    public void tearDown() {
        // Close resources and mocks after each test
        utils.close();
        // Reset other shared resources if necessary
    }

    @Test
    void testStartStudentMenu_NotificationOption() throws IOException {
        when(inputReader.readLine()).thenReturn("1", "no", "3", "2");
        studentController.startStudentMenu();
        verify(out).println("You are currently managing notifications for: ECE101.");
    }

    @Test
    void testStartStudentMenu_ReportOption() throws IOException {
        when(inputReader.readLine()).thenReturn("2", "3", "3", "2");
        studentController.startStudentMenu();
        verify(out).println("You are viewing reports for: ECE101.");
    }

    @Test
    void testStartStudentMenu_Exit() throws IOException {
        when(inputReader.readLine()).thenReturn("3", "2");
        studentController.startStudentMenu();
    }

    @Test
    void testInvalidOption() throws IOException {
        when(inputReader.readLine()).thenReturn("4", "3");
        studentController.startStudentMenu();
        verify(out).println("Invalid Option: Please try agin");
    }
    @Test 
    void testStartStudentMenu_NotificationOption_yes()  throws IOException{
        when(inputReader.readLine()).thenReturn("1", "yes", "3", "2");
        studentController.startStudentMenu();
        verify(out).println("Congrat! You successfully update your ECE101's Notification preference to Disabled");
    }
    @Test
    void testStartStudentMenu_Report_summary() throws IOException {
        when(inputReader.readLine()).thenReturn("2", "1", "3", "3","2");
        studentController.startStudentMenu();
        verify(out).println("summary report");
    }
    @Test
    void testStartStudentMenu_Report_detail() throws IOException {
        when(inputReader.readLine()).thenReturn("2", "2", "3", "3","2");
        studentController.startStudentMenu();
        verify(out).println("detail report");
    }
    
}