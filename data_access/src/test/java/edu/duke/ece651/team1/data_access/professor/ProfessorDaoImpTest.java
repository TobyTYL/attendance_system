package edu.duke.ece651.team1.data_access.professor;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.duke.ece651.team1.shared.Professor;
import org.junit.jupiter.api.Test;

class ProfessorDaoImpTest {

    @Test
    void testAddProfessor() {
        ProfessorDaoImp professorDaoImp = new ProfessorDaoImp();
        professorDaoImp.addProfessor(new Professor("GG Bond"));
    }

    @Test
    void testRemoveProfessor() {
        (new ProfessorDaoImp()).removeProfessor(1);
    }

    @Test
    void testGetAllProfessors() {
        assertTrue((new ProfessorDaoImp()).getAllProfessors().isEmpty());
    }

    @Test
    void testGetProfessorById() {
        assertNull((new ProfessorDaoImp()).getProfessorById(1));
    }
}
