package edu.duke.ece651.team1.client.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.BufferedReader;
import java.io.PrintStream;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @Mock
    private BufferedReader inputReader;

    @Mock
    private PrintStream out;

    @Mock
    private LoginSignupController loginSignupController;

    @Mock
    private MainMenuController mainMenuController;

    private ApplicationController applicationController;

    @BeforeEach
    void setUp() {
        applicationController = new ApplicationController(inputReader, out);
        applicationController.loginSignupController = loginSignupController;
        applicationController.mainMenuController = mainMenuController;
    }

    @Test
    public void testStartApplicationWhenAuthenticatedThenMainMenuStarted() {
        // Arrange
        when(loginSignupController.authenticateOrRegister()).thenReturn(true);

        // Act
        applicationController.startApplication();

        // Assert
        verify(loginSignupController, times(1)).authenticateOrRegister();
        verify(mainMenuController, times(1)).startMainMenu();
    }

    @Test
    public void testStartApplicationWhenNotAuthenticatedThenAuthenticated() {
        // Arrange
        when(loginSignupController.authenticateOrRegister())
                .thenReturn(false)
                .thenReturn(true);

        // Act
        applicationController.startApplication();

        // Assert
        verify(loginSignupController, times(2)).authenticateOrRegister();
        verify(mainMenuController, times(1)).startMainMenu();
    }


}