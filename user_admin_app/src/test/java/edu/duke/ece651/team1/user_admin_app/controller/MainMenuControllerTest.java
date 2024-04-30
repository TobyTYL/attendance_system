package edu.duke.ece651.team1.user_admin_app.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.duke.ece651.team1.user_admin_app.view.MainMenuView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class MainMenuControllerTest {
    private BufferedReader inputReader;
    private PrintStream out;
    private MainMenuView mainMenuView;
    private StudentController studentController;
    private ProfessorController professorController;
    private MainMenuController mainMenuController;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);
        mainMenuView = mock(MainMenuView.class);
        studentController = mock(StudentController.class);
        professorController = mock(ProfessorController.class);

        mainMenuController = new MainMenuController(inputReader, out);
        // Inject mocks
        mainMenuController.mainMenuView = mainMenuView;
        mainMenuController.studentController = studentController;
        mainMenuController.professorController = professorController;
    }

    @Test
    void testStartMainMenu_ProfessorsOption() throws IOException {
        // Mock user input
        when(mainMenuView.readMainOption()).thenReturn("professors", "exit");

        mainMenuController.startMainMenu();

        // Verify that professorController's startProfessorMenu() method was called
        verify(professorController, times(1)).startProfessorMenu();
        // Verify that studentController's startStudentMenu() method was not called
        verify(studentController, never()).startStudentMenu();
        // Verify that "GoodBye!" message was printed
        verify(out).println("GoodBye!");
    }

    @Test
    void testStartMainMenu_StudentsOption() throws IOException {
        // Mock user input
        when(mainMenuView.readMainOption()).thenReturn("students", "exit");

        mainMenuController.startMainMenu();

        // Verify that studentController's startStudentMenu() method was called
        verify(studentController, times(1)).startStudentMenu();
        // Verify that professorController's startProfessorMenu() method was not called
        verify(professorController, never()).startProfessorMenu();
        // Verify that "GoodBye!" message was printed
        verify(out).println("GoodBye!");
    }

}