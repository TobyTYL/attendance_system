package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    @Test
    void testConstructorWithId() {
        Professor professor = new Professor(1, 2);
        assertEquals(Integer.valueOf(1), professor.getProfessorId());
        assertEquals(2, professor.getUserId());
    }

    @Test
    void testConstructorWithIdAndName() {
        Professor professor = new Professor(1, 2, "John Doe");
        assertEquals(Integer.valueOf(1), professor.getProfessorId());
        assertEquals(2, professor.getUserId());
        assertEquals("John Doe", professor.getDisplayName());
    }

    @Test
    void testConstructorWithUserIdOnly() {
        Professor professor = new Professor(2);
        assertNull(professor.getProfessorId());
        assertEquals(2, professor.getUserId());
    }

    @Test
    void testConstructorWithDisplayNameOnly() {
        Professor professor = new Professor("John Doe");
        assertNull(professor.getProfessorId());
        assertEquals(0, professor.getUserId()); // Assuming default value for int is 0
        assertEquals("John Doe", professor.getDisplayName());
    }

    @Test
    void testConstructorWithUserIdAndDisplayName() {
        Professor professor = new Professor(2, "John Doe");
        assertNull(professor.getProfessorId());
        assertEquals(2, professor.getUserId());
        assertEquals("John Doe", professor.getDisplayName());
    }

    @Test
    void testToString() {
        Professor professor = new Professor(1, 2, "John Doe");
        assertEquals("Professor [professorId=1, userId=2]", professor.toString());
    }

    @Test
    void testEquals() {
        Professor professor1 = new Professor(1, 2);
        Professor professor2 = new Professor(1, 2);
        Student student = new Student();
        assertFalse(professor1.equals(student));
        assertTrue(professor1.equals(professor2));
    }

    @Test
    void testHashCode() {
        Professor professor = new Professor(1, 2);
        assertEquals(professor.toString().hashCode(), professor.hashCode());
    }
}
