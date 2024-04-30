package edu.duke.ece651.team1.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import edu.duke.ece651.team1.client.controller.CourseController;
import edu.duke.ece651.team1.client.model.CourseDetail;
import edu.duke.ece651.team1.client.model.UserSession;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserSession userSession;
   
    private MockedStatic<ApiService> mockedApiService;

    @BeforeEach
    void setUp() {
        // Inject mocks by constructor since there is no @InjectMocks due to manual
        // instantiation of view and restTemplate

        userSession = UserSession.getInstance();
        userSession.setHost("localhost");
        userSession.setPort("8080");
        mockedApiService = Mockito.mockStatic(ApiService.class);
        // mockStatic(ControllerUtils.class);

    }

    @AfterEach
    public void tearDown() {
        // Close resources and mocks after each test
        mockedApiService.close();
        // Reset other shared resources if necessary
    }

    @Test
    public void testGetCourses() {
        String role = "Professor";
        int userId = 1;
        List<String> mockJsonResponses = Arrays.asList(
                "{\"courseId\": 101, \"sectionId\": 201, \"courseName\": \"Introduction to Testing\"}",
                "{\"courseId\": 102, \"sectionId\": 202, \"courseName\": \"Advanced Mockito\"}");
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {
        };
        when(ApiService.executeGetRequest(anyString(), eq(typeRef)))
                .thenReturn(mockJsonResponses);
        List<CourseDetail> courseDetails = courseService.getCourses(role, userId);
        assertEquals(2, courseDetails.size());
        assertEquals(101, courseDetails.get(0).getId());
        assertEquals("Introduction to Testing", courseDetails.get(0).getCourseName());

    }
}
