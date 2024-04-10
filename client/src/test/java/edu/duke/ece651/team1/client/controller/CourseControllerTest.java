package edu.duke.ece651.team1.client.controller;

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

import java.util.List;
import edu.duke.ece651.team1.client.view.*;
import edu.duke.ece651.team1.client.model.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private BufferedReader inputReader;
    @Mock
    private PrintStream out;
    @Mock
    private CourseView view;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserSession userSession;
    @Mock
    private CourseController courseController;
    private MockedStatic<ControllerUtils> utils;

    @BeforeEach
    void setUp() {
        // Inject mocks by constructor since there is no @InjectMocks due to manual
        // instantiation of view and restTemplate

        userSession = UserSession.getInstance();
        userSession.setHost("localhost");
        userSession.setPort("8080");
        utils = Mockito.mockStatic(ControllerUtils.class);
        // mockStatic(ControllerUtils.class);

    }


    @AfterEach
    public void tearDown() {
        // Close resources and mocks after each test
        utils.close();
        // Reset other shared resources if necessary
    }

    @Test
    void startCourseMainMenu_NoCoursesAvailable_professor() throws IOException {
        courseController = new CourseController("Professor", 1, inputReader, out);
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {
        };
        when(ControllerUtils.executeGetRequest(anyString(), eq(typeRef)))
                .thenReturn(new ArrayList<>());
        courseController.startCourseMainMenu();
        verify(out).println("Welcome, Professor! Ready to manage your courses?");
        verify(out).println("Choose a course to begin!");
        verify(out).println("See you next time.");
        verify(view, never()).displayCoursesAndPrompt(anyList());
    }

    @Test
    void startCourseMainMenu_NoCoursesAvailable_student() throws IOException {
        courseController = new CourseController("Student", 1, inputReader, out);
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {
        };
       when(ControllerUtils.executeGetRequest(anyString(), eq(typeRef)))
                .thenReturn(new ArrayList<>());
        courseController.startCourseMainMenu();
        verify(out).println("Welcome, Student! Ready to manage your courses?");
        verify(out).println("Choose a course to begin!");
        verify(out).println("See you next time.");
        verify(view, never()).displayCoursesAndPrompt(anyList());
    }

    @Test
    void startCourseMainMenu_CoursesAvailable_UserChoosesToExit() throws IOException {
        // Simulate the user choosing to exit when courses are available
        courseController = new CourseController("Professor", 1, inputReader, out);
        when(inputReader.readLine()).thenReturn("2");
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {
        };
       when(ControllerUtils.executeGetRequest(anyString(), eq(typeRef)))
                .thenReturn(List.of(
                        "{\"courseId\":101,\"courseName\":\"Intro to Testing\",\"sectionId\":1}"));
        courseController.startCourseMainMenu();
        verify(out).println("Available Courses:");
        verify(out, times(1)).printf("%d: Course Name: %s, Section ID: %s%n", 1, 101, "Intro to Testing", 1);
        verify(out).println("GoodBye!");
    }

    @Test
    void startCourseMainMenu_CoursesAvailable_UserChoosesContinue() throws IOException {
        courseController = new CourseController("Professor", 1, inputReader, out);
        when(inputReader.readLine()).thenReturn("1", "2", "2");
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {
        };
        when(ControllerUtils.executeGetRequest(anyString(), eq(typeRef)))
                .thenReturn(List.of(
                        "{\"courseId\":101,\"courseName\":\"Intro to Testing\",\"sectionId\":1}"));
        courseController.startCourseMainMenu();
        verify(out).println("1. Manage your attendance");
        verify(out).println("2. Go back");
    }

}
