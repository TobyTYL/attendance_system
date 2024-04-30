package edu.duke.ece651.team1.client.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationResultTest {

    @Test
    void testIsSuccessful() {
        AuthenticationResult authResult = new AuthenticationResult(true, "Login successful");
        assertTrue(authResult.isSuccessful());
    }

    @Test
    void testGetMessage() {
        String message = "Login successful";
        AuthenticationResult authResult = new AuthenticationResult(true, message);
        assertEquals(message, authResult.getMessage());
    }

    @Test
    void testFailedAuthentication() {
        AuthenticationResult authResult = new AuthenticationResult(false, "Login failed");
        assertFalse(authResult.isSuccessful());
        assertEquals("Login failed", authResult.getMessage());
    }
}

