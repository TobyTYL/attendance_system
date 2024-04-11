package edu.duke.ece651.team1.data_access.Section;
import edu.duke.ece651.team1.data_access.DB_connect;
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
// edit this method
    @Override
    public void addSection(Section section) {
        // edit here
//        String sql = "INSERT INTO sections (sectionid, classid, professorid) VALUES (?, ?, ?)";
        String sql = "INSERT INTO sections (classid, professorid) VALUES (?, ?)";

        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, section.getClassId());
            ps.setInt(2, section.getProfessorId());
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
                if (rs.next()) {
                    Section section = new Section(rs.getInt("sectionid"), rs.getInt("classid"), rs.getInt("professorid"));
                    return Optional.of(section);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting sections by professor ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Section> getSectionsByClassId(int classId) {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT sectionid, classid, professorid FROM sections WHERE classid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section(rs.getInt("sectionid"), rs.getInt("classid"), rs.getInt("professorid"));
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting sections by class ID: " + e.getMessage());
        }
        return sections;
    }

    public void updateSectionProfessor(String className, int sectionID, Integer newProfessorID) throws SQLException {
        //根据用户输入的classname先找到classid定位
        String getClassIdSql = "SELECT classid FROM classes WHERE classname = ?";
        int classId = 0;
        try (PreparedStatement psGetClassId = DB_connect.getConnection().prepareStatement(getClassIdSql)) {
            psGetClassId.setString(1, className);
            try (ResultSet rs = psGetClassId.executeQuery()) {
                if (rs.next()) {
                    classId = rs.getInt("classid");
                }
            }
        }
        //根据用户输入的sectionid定位到正确的section，然后对这个section的professor进行更改
        String updateSectionSql = "UPDATE sections SET professorid = ? WHERE sectionid = ? AND classid = ?";
        try (PreparedStatement psUpdateSection = DB_connect.getConnection().prepareStatement(updateSectionSql)) {
            psUpdateSection.setInt(1, newProfessorID);
            psUpdateSection.setInt(2, sectionID);
            psUpdateSection.setInt(3, classId);
            int rowsAffected = psUpdateSection.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Section updated successfully.");
            } else {
                throw new SQLException("Failed to update section.");
            }
        }
    }

    public boolean checkSectionExists(int sectionId) {
        String sql = "SELECT 1 FROM sections WHERE sectionid = ?";
        try (Connection conn = DB_connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getClassIdBySectionId(int sectionId) {
        String sql = "SELECT classid FROM sections WHERE sectionid = ?";
        try (Connection conn = DB_connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("classid");
            }
        } catch (SQLException e) {
            System.out.println("Error getting class ID by section ID: " + e.getMessage());
        }
        return -1; // Return an invalid value indicating not found
    }

}
