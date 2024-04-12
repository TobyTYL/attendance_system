package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    void testConstructorWithEnrollmentId() {
        Integer enrollmentId = 1;
        int studentId = 100;
        int sectionId = 200;
        Enrollment enrollment = new Enrollment(enrollmentId, studentId, sectionId);
        assertEquals(enrollmentId, enrollment.getEnrollmentId());
        assertEquals(studentId, enrollment.getStudentId());
        assertEquals(sectionId, enrollment.getSectionId());
    }

    @Test
    void testConstructorWithoutEnrollmentId() {
        int studentId = 100;
        int sectionId = 200;
        Enrollment enrollment = new Enrollment(studentId, sectionId);
        assertNull(enrollment.getEnrollmentId());
        assertEquals(studentId, enrollment.getStudentId());
        assertEquals(sectionId, enrollment.getSectionId());
    }

    @Test
    void testSetEnrollmentId() {
        int studentId = 100;
        int sectionId = 200;
        Enrollment enrollment = new Enrollment(studentId, sectionId);
        Integer newEnrollmentId = 300;
        enrollment.setEnrollmentId(newEnrollmentId);
        assertEquals(newEnrollmentId, enrollment.getEnrollmentId());
    }

    @Test
    void testSetStudentId() {
        Enrollment enrollment = new Enrollment(1, 100, 200);
        int newStudentId = 101;
        enrollment.setStudentId(newStudentId);
        assertEquals(newStudentId, enrollment.getStudentId());
    }

    @Test
    void testSetSectionId() {
        Enrollment enrollment = new Enrollment(1, 100, 200);
        int newSectionId = 201;
        enrollment.setSectionId(newSectionId);
        assertEquals(newSectionId, enrollment.getSectionId());
    }
}
