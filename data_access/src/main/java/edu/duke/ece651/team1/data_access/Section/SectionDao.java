package edu.duke.ece651.team1.data_access.Section;
import edu.duke.ece651.team1.shared.Section;
import java.util.Optional;
import java.util.List;
import java.sql.*;
public interface SectionDao {
    List<Section> getAllSections();
    boolean checkSectionExists(int sectionId);
    Section getSectionById(int sectionId);
    void addSection(Section section);
    void updateSection(Section section);
    void deleteSection(int sectionId);
    List<Section> getSectionsByProfessorId(int professorId); 
    Optional<Section> findSectionByProfessorIdAndClassID(int professorId, int classId);
    List<Section> getSectionsByClassId(int classId);
//    void updateSectionProfessor(String className, int sectionID, String professorName) throws SQLException;
    void updateSectionProfessor(String className, int sectionID, Integer professorID) throws SQLException;
    int getClassIdBySectionId(int sectionId);
    boolean existsSectionWithProfessor(int classId, int professorId);
}
