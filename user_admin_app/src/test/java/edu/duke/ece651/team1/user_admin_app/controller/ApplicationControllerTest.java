package edu.duke.ece651.team1.user_admin_app.controller;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

public class ApplicationControllerTest {
    private ApplicationController appController;
    private MainMenuController mockMainMenuController;
    private StudentController mockStudentController;
    private BufferedReader reader;
    private PrintStream out;

    @BeforeEach
    void setUp() {
        reader = new BufferedReader(new StringReader(""));
        out = mock(PrintStream.class);
        appController = new ApplicationController(reader, out);
        mockMainMenuController = mock(MainMenuController.class);
        mockStudentController = mock(StudentController.class);
        appController.mainMenuController = mockMainMenuController;
        appController.studentController = mockStudentController;
    }

    @Test
    void testStartApplication() throws IOException {
        appController.startApplication();
        verify(mockMainMenuController, times(1)).startMainMenu();
    }

    @Test
    void testConstructor() {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        PrintStream out = System.out;  // Consider using a more test-friendly PrintStream

        ApplicationController appController = new ApplicationController(reader, out);

        assertNotNull(appController.mainMenuController, "MainMenuController should not be null");
        assertNotNull(appController.studentController, "StudentController should not be null");
    }
}
