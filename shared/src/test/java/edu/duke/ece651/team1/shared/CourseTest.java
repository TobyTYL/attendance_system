package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {

    @Test
    public void testGetID() {
        int expectedId = 1;
        Course course = new Course(expectedId);
        assertEquals(expectedId, course.getID(), "getID should return the correct id.");
    }

    @Test
    public void testGetAndSetName() {
        String expectedName = "Software Engineering";
        Course course = new Course(1); // The ID value here is arbitrary for this test
        course.setName(expectedName);
        assertEquals(expectedName, course.getName(), "getName should return the correct name.");
    }
    @Test
    public void testConstructor_name(){
        Course c = new Course("course");
        c.setID(1);
        assertEquals("course", c.getName());
        assertEquals(1, c.getID());
    }
    @Test
    public void testConstructor_id_name(){
        Course c = new Course(1,"course");
        assertEquals("course", c.getName());
        assertEquals(1, c.getID());
    }
}
