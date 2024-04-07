package edu.duke.ece651.team1.data_access.Section;
import edu.duke.ece651.team1.shared.Section;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.duke.ece651.team1.data_access.DB_connect;
import java.util.Optional;

public class SectionDaoImpl implements SectionDao{
    // private Connection connection;
    
    // public SectionDaoImpl(Connection connection) {
    //     this.connection = connection;
    // }
    @Override
    public List<Section> getAllSections() {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT sectionid, classid, professorid FROM sections";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Section section = new Section(rs.getInt("sectionid"), rs.getInt("classid"), rs.getInt("professorid"));
                sections.add(section);
            }
        } catch (SQLException e) {
            System.out.println("Error getting sections: " + e.getMessage());
        }
        return sections;
    }

    @Override
    public Section getSectionById(int sectionId) {
        String sql = "SELECT sectionid, classid, professorid FROM sections WHERE sectionid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Section(rs.getInt("sectionid"), rs.getInt("classid"), rs.getInt("professorid"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting section by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void addSection(Section section) {
        String sql = "INSERT INTO sections (sectionid, classid, professorid) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, section.getSectionId());
            ps.setInt(2, section.getClassId());
            ps.setInt(3, section.getProfessorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding section: " + e.getMessage());
        }
    }

    @Override
    public void updateSection(Section section) {
        String sql = "UPDATE sections SET classid = ?, professorid = ? WHERE sectionid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, section.getClassId());
            ps.setInt(2, section.getProfessorId());
            ps.setInt(3, section.getSectionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating section: " + e.getMessage());
        }
    }

    @Override
    public void deleteSection(int sectionId) {
        String sql = "DELETE FROM sections WHERE sectionid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting section: " + e.getMessage());
        }
    }
    @Override
    public List<Section> getSectionsByProfessorId(int professorId) {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT sectionid, classid, professorid FROM sections WHERE professorid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, professorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section(rs.getInt("sectionid"), rs.getInt("classid"), rs.getInt("professorid"));
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting sections by professor ID: " + e.getMessage());
        }
        return sections;
    }

    @Override
    public Optional<Section> findSectionByProfessorIdAndClassID(int professorId, int classId) {
        String sql = "SELECT sectionid, classid, professorid FROM sections WHERE professorid = ? AND ClassID = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, professorId);
            ps.setInt(2, classId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Section section = new Section(rs.getInt("sectionid"), rs.getInt("classid"), rs.getInt("professorid"));
                    return Optional.of(section);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting sections by professor ID: " + e.getMessage());
        }
        return Optional.empty();
    }
}
