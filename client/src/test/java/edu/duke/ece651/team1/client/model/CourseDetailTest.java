package edu.duke.ece651.team1.client.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CourseDetailTest {

    @Test
    void testConstructorAndGetters() {
        int expectedId = 101;
        int expectedSectionId = 501;
        String expectedCourseName = "Introduction to Java";
        CourseDetail courseDetail = new CourseDetail(expectedId, expectedSectionId, expectedCourseName);
        assertEquals(expectedId, courseDetail.getId(), "Check that the ID is set correctly.");
        assertEquals(expectedSectionId, courseDetail.getSectionId(), "Check that the section ID is set correctly.");
        assertEquals(expectedCourseName, courseDetail.getCourseName(), "Check that the course name is set correctly.");
    }

}

