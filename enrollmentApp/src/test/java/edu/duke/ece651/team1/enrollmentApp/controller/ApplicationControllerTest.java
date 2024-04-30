//package edu.duke.ece651.team1.enrollmentApp.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.*;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.StringReader;
//import java.sql.SQLException;
//
//
//public class ApplicationControllerTest {
//  private ApplicationController appController;
//  private MainMenuController mockMainMenuController;
//  private BufferedReader reader;
//  private PrintStream out;
//
//  @BeforeEach
//  void setUp() {
//      reader = new BufferedReader(new StringReader(""));
//      out = mock(PrintStream.class);
//      appController = new ApplicationController(reader, out);
//
//      // Mock MainMenuController and replace the real one
//      mockMainMenuController = mock(MainMenuController.class);
//      appController.MainMenuController = mockMainMenuController;
//  }
//
//  @Test
//  void testStartApplication() throws IOException, SQLException {
//      appController.startApplication();
//      verify(mockMainMenuController, times(1)).startMainMenu();
//  }
//  @Test
//    void testConstructor() {
//        BufferedReader reader = new BufferedReader(new StringReader(""));
//        PrintStream out = System.out;  // Consider using a more test-friendly PrintStream
//
//        ApplicationController appController = new ApplicationController(reader, out);
//
//        assertNotNull(appController.MainMenuController, "MainMenuController should not be null");
//        assertNotNull(appController.courseController, "CourseController should not be null");
//        assertNotNull(appController.enrollmentController, "EnrollmentController should not be null");
//    }
//}
