package edu.duke.ece651.team1.server.controller;

import edu.duke.ece651.team1.server.service.StudentService;
import edu.duke.ece651.team1.shared.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("username", "password"));
    }

    @Test
    public void testGetAllStudent_Success() {
        List<Student> students = new ArrayList<>();
        ResponseEntity<List<Student>> expectedResponse = new ResponseEntity<>(students, HttpStatus.OK);
        ResponseEntity<List<Student>> responseEntity = studentController.getAllStudent();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }
    @Test
    public void testGetAllStudent_FileNotFoundException() throws IOException {
        when(studentService.getAllStudent(anyString())).thenThrow(new FileNotFoundException());
        ResponseEntity<List<Student>> responseEntity = studentController.getAllStudent();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }
    @Test
    public void testGetAllStudent_InternalServerError() throws IOException {
        when(studentService.getAllStudent(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<List<Student>> responseEntity = studentController.getAllStudent();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }
    @Test
    public void testPostMethodName_Success() throws IOException {
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "John Doe", "john@example.com"));

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("received", HttpStatus.OK);

        ResponseEntity<String> responseEntity = studentController.postMethodName(students);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("received", responseEntity.getBody());
    }
//    @Test
//    public void testPostMethodName_InternalServerError() throws IOException {
//        List<Student> students = new ArrayList<>();
//        students.add(new Student("John", "John Doe", "john@example.com"));
//
//        when(studentService.saveRoster(students, "username")).thenThrow(new IOException("Test exception"));
//
//        ResponseEntity<String> responseEntity = studentController.postMethodName(students);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("error happen in save roster because "));
//    }


    @Test
    public void testCheckStudentExists() {
        String studentName = "John Doe";
        when(studentService.checkStudentExists("username", studentName)).thenReturn(true);

        ResponseEntity<Boolean> responseEntity = studentController.checkStudentExists(studentName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody());
    }


    @Test
    public void testAddStudent_Success() throws IOException {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doNothing().when(studentService).addStudent(any(Student.class), anyString());
        Student student = new Student("John", "John Doe", "john@example.com");
        ResponseEntity<String> responseEntity = studentController.addStudent(student);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Student added successfully", responseEntity.getBody());
    }
    @Test
    public void testAddStudent_InternalServerError() throws IOException {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new IOException("Test exception")).when(studentService).addStudent(any(Student.class), anyString());
        Student student = new Student("John", "John Doe", "john@example.com");
        ResponseEntity<String> responseEntity = studentController.addStudent(student);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Failed to add student"));
    }
    @Test
    public void testRemoveStudent_Success() throws IOException {
        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doNothing().when(studentService).removeStudent(anyString(), anyString());
        ResponseEntity<String> responseEntity = studentController.removeStudent("John");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Student removed successfully", responseEntity.getBody());
    }
    @Test
    public void testRemoveStudent_InternalServerError() throws IOException {
        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new RuntimeException("Test exception")).when(studentService).removeStudent(anyString(), anyString());
        ResponseEntity<String> responseEntity = studentController.removeStudent("John");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Failed to remove student"));
    }
    @Test
    @WithMockUser()
    public void editStudentDisplayName_Success() throws Exception {
        String studentName = "JohnDoe";
        String newDisplayName = "Johnny";
        doNothing().when(studentService).editStudentDisplayName(anyString(), anyString(), anyString());
      

        MockHttpServletRequestBuilder requestBuilder = put("/api/students/student/displayname/{studentName}/{newDisplayName}", studentName, newDisplayName)
                        .contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
                            .build()
                            .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Student display name updated successfully"));
    }

    @Test
    @WithMockUser()
    public void editStudentDisplayName_NotFound() throws Exception {
        String studentName = "JohnDoe";
        String newDisplayName = "Johnny";
        doThrow(new IllegalArgumentException("Student not found")).when(studentService).editStudentDisplayName(anyString(), anyString(), anyString());
         MockHttpServletRequestBuilder requestBuilder = put("/api/students/student/displayname/{studentName}/{newDisplayName}", studentName, newDisplayName)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
                    .build()
                    .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                     .andExpect(MockMvcResultMatchers.content().string("Failed to edit student display name: Student not found"));
    }

}
