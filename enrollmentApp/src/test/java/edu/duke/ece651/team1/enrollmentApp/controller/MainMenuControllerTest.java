//package edu.duke.ece651.team1.enrollmentApp.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import edu.duke.ece651.team1.enrollmentApp.view.MainMenuView;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.*;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.sql.SQLException;
//
//public class MainMenuControllerTest {
//  private BufferedReader inputReader;
//    private PrintStream out;
//    private MainMenuView mainMenuView;
//    private CourseController courseController;
//    private EnrollmentController enrollmentController;
//    private MainMenuController mainMenuController;
//
//    @BeforeEach
//    void setUp() {
//        inputReader = mock(BufferedReader.class);
//        out = mock(PrintStream.class);
//        mainMenuView = mock(MainMenuView.class);
//        courseController = mock(CourseController.class);
//        enrollmentController = mock(EnrollmentController.class);
//
//        mainMenuController = new MainMenuController(inputReader, out);
//        // Inject mocks
//        mainMenuController.mainMenuView = this.mainMenuView;
//        mainMenuController.courseController = this.courseController;
//        mainMenuController.enrollmentController = this.enrollmentController;
//    }
//
//    @Test
//    void testStartMainMenu_CourseManagement() throws IOException, SQLException {
//        when(mainMenuView.getMenuChoice()).thenReturn(1, 3); // First choose course management, then exit
//
//        mainMenuController.startMainMenu();
//
//        verify(courseController, times(1)).startCourseManagement();
//        verify(enrollmentController, never()).startEnrollment();
//    }
//    @Test
//    void testStartMainMenu_Enrollment() throws IOException, SQLException {
//        when(mainMenuView.getMenuChoice()).thenReturn(2, 3); // First choose enrollment, then exit
//
//        mainMenuController.startMainMenu();
//
//        verify(enrollmentController, times(1)).startEnrollment();
//        verify(courseController, never()).startCourseManagement();
//    }
//    @Test
//    void testStartMainMenu_InvalidOptionAndExit() throws IOException, SQLException {
//        when(mainMenuView.getMenuChoice()).thenReturn(99, 3); // First an invalid option, then exit
//
//        mainMenuController.startMainMenu();
//
//        verify(out).println("Invalid option. Please try again.");
//        verify(courseController, never()).startCourseManagement();
//        verify(enrollmentController, never()).startEnrollment();
//    }
//
//}
