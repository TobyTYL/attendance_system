package edu.duke.ece651.team1.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class UserSessionTest {
    @Test
    void testSingletonUniqueInstance() {
        UserSession firstInstance = UserSession.getInstance();
        UserSession secondInstance = UserSession.getInstance();

        assertSame(firstInstance, secondInstance);
    }

    @Test
    void testUsernameAssignment() {
        UserSession instance = UserSession.getInstance();
        instance.setUsername("testUser");

        assertEquals("testUser", instance.getUsername());
    }

    @Test
    void testSessionTokenAssignment() {
        UserSession instance = UserSession.getInstance();
        instance.setSessionToken("token123");

        assertEquals("token123", instance.getSessionToken(), "Session token should be 'token123' after assignment.");
    }

    @Test
    void testHostAssignment() {
        UserSession instance = UserSession.getInstance();
        instance.setHost("localhost");

        assertEquals("localhost", instance.getHost(), "Host should be 'localhost' after assignment.");
    }

    @Test
    void testPortAssignment() {
        UserSession instance = UserSession.getInstance();
        instance.setPort("8080");

        assertEquals("8080", instance.getPort(), "Port should be '8080' after assignment.");
    }
}
