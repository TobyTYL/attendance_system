package edu.duke.ece651.team1.data_access.Section;
import edu.duke.ece651.team1.shared.Section;
import java.util.List;
public interface SectionDao {
    List<Section> getAllSections();
    Section getSectionById(int sectionId);
    void addSection(Section section);
    void updateSection(Section section);
    void deleteSection(int sectionId);
    List<Section> getSectionsByProfessorId(int professorId); 
}
