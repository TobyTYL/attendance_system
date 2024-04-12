package edu.duke.ece651.team1.client.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import java.util.*;
import org.mockito.Mockito;

import org.mockito.MockedStatic;
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
        

    }


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
    @Test
    void testHandleLoginSunceessAndContinue() throws IOException {
        MockedStatic<ControllerUtils> utils = Mockito.mockStatic(ControllerUtils.class);
        String jsonString = "{\"role\": \"Student\", \"id\": 123}";
        when(loginSignupController.authenticateOrRegister())
        .thenReturn(jsonString);
        
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {
        };
        when(ControllerUtils.executeGetRequest(anyString(), eq(typeRef)))
                .thenReturn(new ArrayList<>());
        assertDoesNotThrow(()->applicationController.startApplication());
        verify(out).println("See you next time.");
        utils.close();

    }

}
