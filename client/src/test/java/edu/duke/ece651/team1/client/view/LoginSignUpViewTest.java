package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;

public class LoginSignUpViewTest {
    private BufferedReader inputReader;
    private PrintStream out;
    private LoginSignUpView loginSignUpView;

    @BeforeEach
    void setUp() {
        inputReader = mock(BufferedReader.class);
        out = mock(PrintStream.class);
        loginSignUpView = new LoginSignUpView(inputReader, out);
    }
    @Test
    void testShowLoginOrRegistrationMenu() {
        loginSignUpView.showLoginOrRegistrationMenu();
        verify(out).println("Welcome to the Attendance Management System!");
        verify(out).println("1. Login");
        verify(out).println("2. Sign Up and Login");
    }
    @Test
    void testReadLoginOrSignUp() throws IOException {
        when(inputReader.readLine()).thenReturn("1");
        assertEquals("login", loginSignUpView.readLoginOrSignUp());

        when(inputReader.readLine()).thenReturn("2");
        assertEquals("signUp", loginSignUpView.readLoginOrSignUp());

        when(inputReader.readLine()).thenReturn("invalid", "1");
        assertEquals("login", loginSignUpView.readLoginOrSignUp());
        verify(out).println("Invalid option. Please select 1 for Login or 2 for SignUp.");
    }
    @Test
    void testPromptForLogin() throws IOException {
        when(inputReader.readLine()).thenReturn("user", "pass");
        Map<String, String> loginInfo = loginSignUpView.promptForLogin();
        assertEquals("user", loginInfo.get("username"));
        assertEquals("pass", loginInfo.get("password"));
        verify(out).println("Login");
    }

    @Test
    void testPromptForSignUp() throws IOException {
        when(inputReader.readLine()).thenReturn("newuser", "newpass");
        Map<String, String> signUpInfo = loginSignUpView.promptsignUp();
        assertEquals("newuser", signUpInfo.get("username"));
        assertEquals("newpass", signUpInfo.get("password"));
        verify(out).println("Sign Up");
    }
    @Test
    void testReadInfo() throws IOException {
        when(inputReader.readLine()).thenReturn("info");
        assertEquals("info", loginSignUpView.readInfo("Prompt"));
        verify(out).println("Prompt");
    }
   
    @Test
    void testReadInfoEOFException() {
        assertThrows(EOFException.class, () -> {
            when(inputReader.readLine()).thenReturn(null);
            loginSignUpView.readInfo("Prompt");
        });
    }
    @Test
    void testShowLoginSuccessMessage() {
        loginSignUpView.showLoginSuccessMessage();
        verify(out).println("Login successful. Welcome!");
    }

    @Test
    void testShowLoginFailureMessage() {
        loginSignUpView.showLoginFailureMessage();
        verify(out).println("Login failed. Please check your username and password and try again.");
    }

    @Test
    void testShowRegistrationSuccessMessage() {
        loginSignUpView.showRegistrationSuccessMessage();
        verify(out).println("Registration successful.");
    }

    @Test
    void testShowRegistrationFailureMessage() {
        String errorMessage = "an error occurred";
        loginSignUpView.showRegistrationFailureMessage(errorMessage);
        verify(out).println("Registration failed because " + errorMessage + " Please try again.");
    }

}
