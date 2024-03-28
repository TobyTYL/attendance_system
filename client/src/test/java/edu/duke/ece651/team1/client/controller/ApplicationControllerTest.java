package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class ApplicationControllerTest {
    @Test
    void testStartApplication() throws Exception {
        BufferedReader readerMock = mock(BufferedReader.class);
        PrintStream outMock = new PrintStream(new ByteArrayOutputStream());

        // Mocking LoginSignupController and MainMenuController might be tricky without direct support for dependency injection.
        // Instead, we simulate the user input and output for authentication success on first attempt
        when(readerMock.readLine())
            .thenReturn("userInput1") // Simulate user inputs
            .thenReturn("userInput2");

        // Assume these methods are public and can be mocked (you might need to adjust visibility or refactor for testing)
        LoginSignupController loginSignupControllerMock = mock(LoginSignupController.class);
        MainMenuController mainMenuControllerMock = mock(MainMenuController.class);

        // Simulate successful authentication
        when(loginSignupControllerMock.authenticateOrRegister()).thenReturn(true);

        ApplicationController appController = new ApplicationController(readerMock, outMock);
        // Need to set the mocked controllers after instantiation due to the current design
        // This is a limitation of the direct instantiation approach in the constructor
        appController.loginSignupController = loginSignupControllerMock;
        appController.mainMenuController = mainMenuControllerMock;

        appController.startApplication();

        verify(loginSignupControllerMock, times(1)).authenticateOrRegister();
        verify(mainMenuControllerMock, times(1)).startMainMenu();
    }
}
