package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
//import com.google.common.net.HttpHeaders;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.LoginSignUpView;
@ExtendWith(MockitoExtension.class)
public class LoginSignupControllerTest {
    @Mock
    private BufferedReader inputReader;

    @Mock
    private PrintStream out;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LoginSignupController controller;

    @Mock
    private LoginSignUpView loginSignUpView;

    @BeforeEach
    void setUp() {
        // UserSession.getInstance().setHost("localhost");
        // UserSession.getInstance().setPort("8080");
        //  MockitoAnnotations.openMocks(this);
        // loginSignUpView = mock(LoginSignUpView.class);
        // inputReader = mock(BufferedReader.class);
        // out = mock(PrintStream.class);
        // restTemplate = mock(RestTemplate.class);
        // controller = new LoginSignupController(inputReader, out);
        // controller.loginSignUpView = loginSignUpView; // Assuming you have a way to set this, like a setter or directly
        // controller.restTemplate = restTemplate; // Same as above
        MockitoAnnotations.openMocks(this);
        UserSession.getInstance().setHost("localhost");
        UserSession.getInstance().setPort("8080");
    }

    @Test
    void testSuccessfulLogin() throws IOException {
        when(loginSignUpView.readLoginOrSignUp()).thenReturn("login");
        when(loginSignUpView.promptForLogin()).thenReturn(Map.of("username", "testUser", "password", "testPass"));
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "sessionToken");
        ResponseEntity<String> response = new ResponseEntity<>(headers, HttpStatus.OK);
        
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(String.class)))
            .thenReturn(response);
        
        assertTrue(controller.authenticateOrRegister());
        verify(loginSignUpView).showLoginSuccessMessage();
    }

    @Disabled
    @Test
    void testUnsuccessfulLogin() throws IOException {
        when(loginSignUpView.readLoginOrSignUp()).thenReturn("login");
        when(loginSignUpView.promptForLogin()).thenReturn(Map.of("username", "testUser", "password", "wrongPass"));
        
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(String.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        
        assertFalse(controller.authenticateOrRegister());
        verify(loginSignUpView).showLoginFailureMessage();
    }
    @Disabled
    @Test
    void directTestUnsuccessfulLogin() throws IOException {
        // Assuming handleLogin can be tested directly; adjust visibility as necessary.
        // This direct approach helps isolate and troubleshoot the login logic itself.
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        assertFalse(controller.handleLogin("testUser", "wrongPass")); // Adjust if direct testing is viable.
        verify(loginSignUpView).showLoginFailureMessage();
    }
    @Test
    void testSuccessfulSignUp() throws IOException {
        when(loginSignUpView.readLoginOrSignUp()).thenReturn("signup");
        when(loginSignUpView.promptsignUp()).thenReturn(Map.of("username", "newUser", "password", "newPass"));
        
        ResponseEntity<String> signUpResponse = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<String> loginResponse = new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        
        when(restTemplate.exchange(contains("/api/signup"), any(HttpMethod.class), any(), eq(String.class)))
            .thenReturn(signUpResponse);
        when(restTemplate.exchange(contains("/api/login"), any(HttpMethod.class), any(), eq(String.class)))
            .thenReturn(loginResponse);
        
        assertTrue(controller.authenticateOrRegister());
        verify(loginSignUpView).showRegistrationSuccessMessage();
    }
    @Test
    void testAuthenticateOrRegisterWithException() throws IOException {
        when(loginSignUpView.readLoginOrSignUp()).thenThrow(new RuntimeException("Test Exception"));
        // String output = out.toString();
        // assertTrue(output.contains("Exception happen in login signup page Test Exception"));
        assertFalse(controller.authenticateOrRegister());
        //verify(out).println(contains("Exception happen in login signup page Test Exception"));
    }
    
    @Test
    void testHandleLoginWithRestClientException() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(String.class)))
                .thenThrow(new RestClientException("Connection error"));

        Exception exception = assertThrows(RuntimeException.class, () -> 
            controller.handleLogin("user", "password")
        );
        
        assertEquals("Unexpected error during login: Connection error", exception.getMessage());
    }
    @Test
    void testHandleSignUpLoginWithSignUpFailure() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request"));

        // Expect a RuntimeException due to the current implementation of handleSignUp
        Exception exception = assertThrows(RuntimeException.class, () -> 
            controller.handleSignUpLogin("user", "pass")
        );
        
        // Optionally, verify the exception message if relevant
        //assertTrue(exception.getMessage().contains("Unexpected error during signup"));
    }


}
