package edu.duke.ece651.team1.client.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import edu.duke.ece651.team1.client.model.*;
import edu.duke.ece651.team1.client.service.*;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ContextConfiguration(classes = {CourseController.class})
@ExtendWith(SpringExtension.class)
public class CourseControllerTest {
     @MockBean
    private CourseService courseService;
    @Autowired
    private CourseController controller;
    private Model model;

    @BeforeEach
    public void setUp() {
      
        model = new ExtendedModelMap(); 
    }

    @Test
    public void testGetCourses() {
        String role = "Professor";
        int id = 1;
        List<CourseDetail> mockCourses = Arrays.asList(new CourseDetail(1, 1, "Software Engineering"));
        when(courseService.getCourses(role, id)).thenReturn(mockCourses);
        String viewName = controller.getCourses(role, id, model);
        verify(courseService).getCourses(role, id);  
        assertEquals("course", viewName); 
        assertSame(mockCourses, model.getAttribute("courses")); 
        assertEquals(role, model.getAttribute("role"));
        assertEquals(id, model.getAttribute("id"));
    }
}
