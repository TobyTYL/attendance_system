package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class MainMenuViewTest {
    private BufferedReader inputReader;
    private PrintStream out;
    private MainMenuView mainMenuView;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);
        mainMenuView = new MainMenuView(inputReader, out);
    }
    @Test
    void testShowMainMenu() {
        mainMenuView.showMainMenu();
        verify(out).println("Please select an option to begin:");
        verify(out).println("1. Manage your attendance");
        verify(out).println("2. Go back");
    }
    @Test
    void testReadMainOptionAttendance() throws IOException {
        when(inputReader.readLine()).thenReturn("1","2");
        assertEquals("attendance", mainMenuView.readMainOption());
        assertEquals("back", mainMenuView.readMainOption());
    }

    @Test
    void testReadMainOptionInvalidThenValid() throws IOException {
        when(inputReader.readLine())
            .thenReturn("invalid") // Simulate invalid input
            .thenReturn("1"); // Simulate valid input on retry
        String result = mainMenuView.readMainOption();
        assertEquals("attendance", result);
        verify(out).println("Invalid option. Please select 1 for Manage attendance or 2 for Go back.");
    }

}
