package edu.duke.ece651.team1.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.TokenService;
import edu.duke.ece651.team1.client.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.slf4j.*;
import java.time.Instant;
import org.json.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The SecurityController class manages user registration for different roles
 * such as professors and students.
 * It provides endpoints for registering users in the system.
 */
@Controller
public class SecurityController {
    @Autowired
    UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {
        String response = userService.authenticate(username, password);
        if (!"loginFailed".equals(response)) {
            JSONObject jsonObject = new JSONObject(response);
            String role = jsonObject.getString("role");
            int id = jsonObject.getInt("id");
            logger.info("login success");
            UserSession.getInstance().setUid(id);
            return "redirect:/course/allcourses/" + role + "/" + id;
        }
        redirectAttributes.addFlashAttribute("loginError", "Login failed");
        return "redirect:/login";
    }

   

}
