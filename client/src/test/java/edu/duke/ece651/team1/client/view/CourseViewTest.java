package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import org.json.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseViewTest {
    private BufferedReader mockedInputReader;
    private PrintStream mockedOut;
    private CourseView courseView;

    @BeforeEach
    void setup() {
        mockedInputReader = mock(BufferedReader.class);
        mockedOut = mock(PrintStream.class);
        courseView = new CourseView(mockedInputReader, mockedOut);
    }

    @Test
    public void testShowWelcomeMessage() {
        courseView.showWelcomeMessage("Professor");
        verify(mockedOut).println("Welcome, Professor! Ready to manage your courses?");
        verify(mockedOut).println("Choose a course to begin!");
    }

    @Test
    public void testShowNoCourseMessageForProfessor() {
        courseView.showNoCourseMessge("Professor");
        verify(mockedOut).println("No courses available for you to manage at this time.");
    }

    @Test
    public void testShowNoCourseMessageForStudent() {
        courseView.showNoCourseMessge("Student");
        verify(mockedOut).println("You are not currently enrolled in any courses.");
    }

    @Test
    public void testDisplayCoursesAndPrompt() throws IOException {
        when(mockedInputReader.readLine()).thenReturn("1","3","2"); // Simulate user selecting the first course
        List<String> courses = List.of(
                "{\"courseId\":101,\"courseName\":\"Intro to Testing\",\"sectionId\":1}");
        JSONObject selectedCourse = courseView.displayCoursesAndPrompt(courses);
        assertNotNull(selectedCourse);
        assertEquals("Intro to Testing", selectedCourse.getString("courseName"));
        verify(mockedOut).println("Available Courses:");
        verify(mockedOut, times(1)).printf("%d: Course Name: %s, Section ID: %s%n", 1,101, "Intro to Testing", 1);
        verify(mockedOut).println("2: Exit");
        verify(mockedOut).println("Enter your choice: ");
        JSONObject selectedCourse2 = courseView.displayCoursesAndPrompt(courses);
        assertNull(selectedCourse2);
        verify(mockedOut).println("Invalid option for cousr choose");

    }

}
