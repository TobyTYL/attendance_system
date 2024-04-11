package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getPasswordHash());
        assertNull(user.getRole());
    }

    @Test
    void testConstructorWithUserId() {
        int userId = 1;
        String username = "testUser";
        String passwordHash = "hash";
        User user = new User(userId, username, passwordHash);
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(passwordHash, user.getPasswordHash());
        assertNull(user.getRole());
    }

    @Test
    void testConstructorWithAllFields() {
        int userId = 2;
        String username = "testUser2";
        String passwordHash = "hash2";
        String role = "admin";
        User user = new User(userId, username, passwordHash, role);
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(role, user.getRole());
    }

    @Test
    void testConstructorWithoutUserId() {
        String username = "testUser3";
        String passwordHash = "hash3";
        String role = "user";
        User user = new User(username, passwordHash, role);
        assertEquals(username, user.getUsername());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(role, user.getRole());
    }

    @Test
    void testSetUserId() {
        User user = new User();
        int userId = 3;
        user.setUserId(userId);
        assertEquals(userId, user.getUserId());
    }

    @Test
    void testSetUsername() {
        User user = new User();
        String username = "newUser";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    void testSetPasswordHash() {
        User user = new User();
        String passwordHash = "newHash";
        user.setPasswordHash(passwordHash);
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void testSetEmail() {
        User user = new User();
        String email = "user@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSetRole() {
        User user = new User();
        String role = "newRole";
        user.setRole(role);
        assertEquals(role, user.getRole());
    }
}
