package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {

    @Test
    public void testGetID() {
        long expectedId = 1L;
        Course course = new Course(expectedId);
        assertEquals(expectedId, course.getID(), "getID should return the correct id.");
    }

    @Test
    public void testGetAndSetName() {
        String expectedName = "Software Engineering";
        Course course = new Course(1L); // The ID value here is arbitrary for this test
        course.setName(expectedName);
        assertEquals(expectedName, course.getName(), "getName should return the correct name.");
    }
}
