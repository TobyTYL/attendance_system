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
import edu.duke.ece651.team1.server.service.StudentService;

import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.containsString;

import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.sql.*;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    int studentId = 1;
    int classId = 101;
    boolean preference = true;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void updateNotificationPreference_Success() throws Exception {
        doNothing().when(studentService).updateNotificationPreference(studentId, classId, preference);
        mockMvc.perform(post("/api/students/notification/{studentId}/{classId}", studentId, classId)
                .param("preference", String.valueOf(preference)))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucessfully update your notification preference"));
    }

    @Test
    @WithMockUser
    public void updateNotificationPreference_Error() throws Exception {
        String errorMessage = "Database error";
        doThrow(new RuntimeException(errorMessage)).when(studentService).updateNotificationPreference(studentId,
                classId, preference);
        mockMvc.perform(post("/api/students/notification/{studentId}/{classId}", studentId, classId)
                .param("preference", String.valueOf(preference)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Error update notification preference")));
    }

    @Test
    @WithMockUser
    public void getNotificationPreference_Success() throws Exception {
        String expectedInfo = "{\"ReciveNotification\":true}";
        when(studentService.getNotificationPreference(studentId, classId)).thenReturn(expectedInfo);
        mockMvc.perform(get("/api/students/notification/{studentId}/{classId}", studentId, classId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedInfo));
    }

    @Test
    @WithMockUser
    public void getNotificationPreference_Error() throws Exception {
        String errorMessage = "Database error";
        when(studentService.getNotificationPreference(studentId, classId))
                .thenThrow(new RuntimeException(errorMessage));
        mockMvc.perform(get("/api/students/notification/{studentId}/{classId}", studentId, classId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Error get notification preference")));
    }

}