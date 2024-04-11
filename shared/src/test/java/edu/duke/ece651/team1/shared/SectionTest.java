package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    @Test
    void testConstructorWithSectionId() {
        int sectionId = 10;
        int classId = 20;
        int professorId = 30;
        Section section = new Section(sectionId, classId, professorId);
        assertEquals(sectionId, section.getSectionId());
        assertEquals(classId, section.getClassId());
        assertEquals(professorId, section.getProfessorId());
    }

    @Test
    void testConstructorWithoutSectionId() {
        int classId = 20;
        int professorId = 30;
        Section section = new Section(classId, professorId);
        assertEquals(0, section.getSectionId()); 
        assertEquals(classId, section.getClassId());
        assertEquals(professorId, section.getProfessorId());
    }

    @Test
    void testSetSectionId() {
        Section section = new Section(20, 30);
        int sectionId = 10;
        section.setSectionId(sectionId);
        assertEquals(sectionId, section.getSectionId());
    }

    @Test
    void testSetClassId() {
        Section section = new Section(10, 30);
        int classId = 20;
        section.setClassId(classId);   
        assertEquals(classId, section.getClassId());
    }

    @Test
    void testSetProfessorId() {
        Section section = new Section(10, 20);
        int professorId = 30;
        section.setProfessorId(professorId);
        assertEquals(professorId, section.getProfessorId());
    }
}
