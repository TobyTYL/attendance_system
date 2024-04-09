package edu.duke.ece651.team1.client.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.duke.ece651.team1.client.model.UserSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import org.json.*;;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @Mock
    private BufferedReader inputReader;

    @Mock
    private PrintStream out;

    @Mock
    private LoginSignupController loginSignupController;

    @Mock
    private CourseController controller;
   

    private ApplicationController applicationController;

    @BeforeEach
    void setUp() {
        applicationController = new ApplicationController(inputReader, out);
        applicationController.loginSignupController = loginSignupController;
        // ApplicationController appController = new ApplicationController(inputReader, out) {
        //     @Override
        //     CourseController getCourseController(String message) {
        //         return mockedCourseController;
        //     }
        // };
    }

   

    // @Test
    // void testStartApplicationLoginFailureAndException() throws IOException {
    //     BufferedReader mockedInputReader = mock(BufferedReader.class);
    //     PrintStream mockedOut = mock(PrintStream.class);
    //     LoginSignupController mockedLoginSignupController = mock(LoginSignupController.class);

    //     // Simulate a login failure and an exception in the login/signup process
    //     when(mockedLoginSignupController.authenticateOrRegister()).thenReturn("LoginFailed");
    //     doThrow(new RuntimeException("Login error")).when(mockedLoginSignupController).authenticateOrRegister();

    //     ApplicationController appController = new ApplicationController(mockedInputReader, mockedOut);
    //     appController.startApplication();

    //     // Verify that the error message is printed to the out stream
        
    // }

    @Test
    public void testStartApplicationWhenAuthenticatedThenMainMenuStarted() throws IOException {
        when(loginSignupController.authenticateOrRegister()).thenReturn("LoginSuccess");
        applicationController.startApplication();
        verify(loginSignupController, times(1)).authenticateOrRegister();
       
    }

    @Test
    public void testStartApplicationWhenNotAuthenticatedThenAuthenticated() throws IOException {
        when(loginSignupController.authenticateOrRegister())
                .thenReturn("LoginFailed")
                .thenReturn("LoginSuccess");
        applicationController.startApplication();
        verify(loginSignupController, times(2)).authenticateOrRegister();
    }

}
