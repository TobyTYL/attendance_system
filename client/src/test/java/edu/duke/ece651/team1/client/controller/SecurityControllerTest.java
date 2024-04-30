package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.duke.ece651.team1.client.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ContextConfiguration(classes = { SecurityController.class })
@ExtendWith(SpringExtension.class)
public class SecurityControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private SecurityController controller;
    private RedirectAttributes redirectAttributes;
    String username = "user";
    String password = "password";
    String role = "Student";
    int id = 1;

    @BeforeEach
    public void setUp() {
        redirectAttributes = new RedirectAttributesModelMap();
    }

    @Test
    public void testSuccessfulLogin() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("role", role);
        jsonResponse.put("id", id);
        when(userService.authenticate(username, password)).thenReturn(jsonResponse.toString());

        String viewName = controller.login(username, password, redirectAttributes);

        assertEquals("redirect:/course/allcourses/" + role + "/" + id, viewName);
        verify(userService).authenticate(username, password);
    }

    @Test
    public void testFailedLogin() {
        String username = "user";
        String password = "wrongpassword";
        when(userService.authenticate(username, password)).thenReturn("loginFailed");
        String viewName = controller.login(username, password, redirectAttributes);
        assertEquals("redirect:/login", viewName);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("loginError"));
        assertEquals("Login failed", redirectAttributes.getFlashAttributes().get("loginError"));
    }

}