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

import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.containsString;

import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.sql.*;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;
    int studentId = 1;
    Integer professorId = 1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @WithMockUser
    public void getAllclassesInfoForProfessor_Success() throws Exception {  
        List<String> expectedClassesInfo = List.of("ECE651", "ECE650");
        when(courseService.getTaughtCoursesInfoForProfessor(professorId)).thenReturn(expectedClassesInfo);
        mockMvc.perform(get("/api/class/professor/allclasses/")
                        .param("professorId", String.valueOf(professorId)))
               .andExpect(status().isOk())
               .andExpect(content().json("[\"ECE651\",\"ECE650\"]"));
    }

    @Test
    @WithMockUser
    public void getAllclassesInfoForProfessor_InternalServerError() throws Exception {
        when(courseService.getTaughtCoursesInfoForProfessor(professorId)).thenThrow(new RuntimeException("Service exception"));
        mockMvc.perform(get("/api/class/professor/allclasses/")
                        .param("professorId", String.valueOf(professorId)))
               .andExpect(status().isInternalServerError())
               .andExpect(content().string("[]"));
    }

    // Tests for the Student endpoint
    @Test
    @WithMockUser
    public void getAllclassesInfoForStudent_Success() throws Exception {
        List<String> expectedClassesInfo = List.of("ECE651", "ECE650");
        when(courseService.getRegisteredCoursesInfoForStudent(studentId)).thenReturn(expectedClassesInfo);
        mockMvc.perform(get("/api/class/student/allclasses/")
                        .param("studentId", String.valueOf(studentId)))
               .andExpect(status().isOk())
               .andExpect(content().json("[\"ECE651\",\"ECE650\"]"));
    }

    @Test
    @WithMockUser
    public void getAllclassesInfoForStudent_InternalServerError() throws Exception {
        when(courseService.getRegisteredCoursesInfoForStudent(studentId)).thenThrow(new RuntimeException("Service exception"));
        mockMvc.perform(get("/api/class/student/allclasses/")
                        .param("studentId", String.valueOf(studentId)))
               .andExpect(status().isInternalServerError())
               .andExpect(content().string("[]"));
    }
}

   

