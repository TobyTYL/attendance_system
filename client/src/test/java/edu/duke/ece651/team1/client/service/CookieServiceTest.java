package edu.duke.ece651.team1.client.service;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CookieServiceTest {

    private CookieService cookieService;
    String cookieName = "cookie";

    @BeforeEach
    public void setUp() {
        cookieService = new CookieService();
    }

    @Test
    public void testValidateActivity_NewCookie_ReturnsTrue() {
        String cookie =cookieName;
        int userId = 100;
        assertTrue(cookieService.validateActivity(cookie, userId));
    }

    @Test
    public void testValidateActivity_SameCookieDifferentUserWithin30Min_ReturnsFalse() {
        String cookie =cookieName;
        int userId1 = 100;
        int userId2 = 101;
        cookieService.validateActivity(cookie, userId1);
        assertFalse(cookieService.validateActivity(cookie, userId2));
    }

  
}

