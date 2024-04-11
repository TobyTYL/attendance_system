package edu.duke.ece651.team1.server.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import edu.duke.ece651.team1.shared.User;

public class CustomUserDetailsTest {

    @Test
    public void testCustomUserDetailsConstructorAndGetterMethods() {
        String username = "user";
        String password = "password";
        String role = "USER";
        int userId = 1;

        CustomUserDetails userDetails = new CustomUserDetails(username, password, role, userId);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals("ROLE_" + role, userDetails.getAuthorities().iterator().next().getAuthority());
        assertEquals(userId, userDetails.getUserId());
    }

    @Test
    public void testCustomUserDetailsFromUser() {
        User user = new User("user", "password", "USER");
        user.setUserId(1);
        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPasswordHash(), userDetails.getPassword());
        assertEquals("ROLE_" + user.getRole(), userDetails.getAuthorities().iterator().next().getAuthority());
        assertEquals(user.getUserId(), userDetails.getUserId());
    }

    @Test
    public void testAccountNonExpired() {
        CustomUserDetails userDetails = new CustomUserDetails("user", "password", "USER", 1);
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        CustomUserDetails userDetails = new CustomUserDetails("user", "password", "USER", 1);
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        CustomUserDetails userDetails = new CustomUserDetails("user", "password", "USER", 1);
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testEnabled() {
        CustomUserDetails userDetails = new CustomUserDetails("user", "password", "USER", 1);
        assertTrue(userDetails.isEnabled());
    }
}
