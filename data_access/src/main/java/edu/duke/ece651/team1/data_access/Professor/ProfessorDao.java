package edu.duke.ece651.team1.data_access.Professor;
import java.util.List;
import edu.duke.ece651.team1.shared.Professor;

public interface ProfessorDao {
    void addProfessor(Professor professor);
    void removeProfessor(int professorId);
    Professor getProfessorById(int professorId);
    List<Professor> findAllProfessors();
    Professor findProfessorByUsrID(int userID);
    Professor findProfessorByName(String name);
    boolean checkProfessorExists(String name);

}

