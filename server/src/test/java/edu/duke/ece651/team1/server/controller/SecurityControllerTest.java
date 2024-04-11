package edu.duke.ece651.team1.server.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import edu.duke.ece651.team1.shared.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import edu.duke.ece651.team1.server.service.AttendanceService;
import edu.duke.ece651.team1.server.service.CourseService;
import edu.duke.ece651.team1.server.service.StudentService;
import edu.duke.ece651.team1.server.service.UserService;

import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.containsString;

import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.sql.*;

@WebMvcTest(SecurityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    String userName = "huidan";
    String password = "password";
    String legalName = "huidan Tan";
    String displayName = "rachel";
    String email = "huidan@example.com";

   

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void ProfessorRegister_Success() throws Exception {
        doNothing().when(userService).createUserProfessor(userName, password, "Professor");
        mockMvc.perform(post("/api/signup/professor")
                        .param("username", userName)
                        .param("password", password))
               .andExpect(status().isCreated())
               .andExpect(content().string("Congrat! You have successfully signed up"));
    }

    @Test
    public void ProfessorRegister_UsernameExists() throws Exception {
        doThrow(new RuntimeException("username has already been registered")).when(userService).createUserProfessor(userName, password, "Professor");

        mockMvc.perform(post("/api/signup/professor")
                        .param("username", userName)
                        .param("password", password))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("username has already been registered"));
    }

    // Test for student signup
    @Test
    public void StudentRegister_Success() throws Exception {
        doNothing().when(userService).createUserStudent(userName, password, "Student", legalName, displayName, email);
        mockMvc.perform(post("/api/signup/student")
                        .param("username", userName)
                        .param("password", password)
                        .param("legalname", legalName)
                        .param("displayname", displayName)
                        .param("email", email))
               .andExpect(status().isCreated())
               .andExpect(content().string("Congrat! You have successfully signed up"));
    }

    @Test
    public void StudentRegister_UsernameExists() throws Exception {
        doThrow(new RuntimeException("username has already been registered")).when(userService).createUserStudent(userName, password, "Student", legalName, displayName, email);
        mockMvc.perform(post("/api/signup/student")
                        .param("username", userName)
                        .param("password", password)
                        .param("legalname", legalName)
                        .param("displayname", displayName)
                        .param("email", email))
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("username has already been registered")));
    }
}

   

