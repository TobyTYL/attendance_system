package edu.duke.ece651.team1.data_access.Enrollment;
import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Enrollment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * The EnrollmentDaoImpl class provides implementation for accessing enrollment data in the database.
 */
public class EnrollmentDaoImpl implements EnrollmentDao {
    /**
     * Adds a new enrollment to the database.
     *
     * @param enrollment The enrollment to add.
     */
    @Override
    public void addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (studentid, sectionid) VALUES (?, ?)";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getSectionId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        enrollment.setEnrollmentId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            // Log and handle exception
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a list of enrollments associated with a student ID from the database.
     *
     * @param studentId The ID of the student.
     * @return A list of enrollments associated with the student.
     */
    @Override
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE studentid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Enrollment enrollment = new Enrollment(rs.getInt("studentid"), rs.getInt("sectionid"));
                    enrollment.setEnrollmentId(rs.getInt("enrollmentid"));
                    enrollments.add(enrollment);
                }
            }
        } catch (SQLException e) {
            // Log and handle exception
            e.printStackTrace();
        }
        return enrollments;
    }
    /**
     * Retrieves a list of enrollments associated with a section ID from the database.
     *
     * @param sectionId The ID of the section.
     * @return A list of enrollments associated with the section.
     */
    @Override
    public List<Enrollment> getEnrollmentsBySectionId(int sectionId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE sectionid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Enrollment enrollment = new Enrollment(rs.getInt("studentid"), rs.getInt("sectionid"));
                    enrollment.setEnrollmentId(rs.getInt("enrollmentid"));
                    enrollments.add(enrollment);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting enrollments by section ID: " + e.getMessage());
        }
        return enrollments;
    }
    /**
     * Deletes an enrollment from the database.
     *
     * @param enrollmentId The ID of the enrollment to delete.
     */
    @Override
    public void deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollment WHERE enrollmentid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log and handle exception
            e.printStackTrace();
        }
    }
    /**
     * Finds an enrollment by student ID and class ID in the database.
     *
     * @param studentId The ID of the student.
     * @param classId   The ID of the class.
     * @return An optional containing the enrollment if found, otherwise empty.
     */
    @Override
    public Optional<Enrollment> findEnrollmentByStudentIdAndClassId(int studentId, int classId) {
        // TODO Auto-generated method stub
        String sql = "SELECT e.EnrollmentID, e.StudentID, e.SectionID " +
                 "FROM Enrollment e " +
                 "JOIN Sections s ON e.SectionID = s.SectionID " +
                 "JOIN Classes c ON s.ClassID = c.ClassID " +
                 "WHERE e.StudentID = ? AND c.ClassID = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, classId);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                Enrollment enrollment = new Enrollment(rs.getInt("enrollmentid"),rs.getInt("studentid"), rs.getInt("sectionid"));
                return Optional.of(enrollment);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    /**
     * Checks if a student is already enrolled in a section.
     *
     * @param studentId The ID of the student.
     * @param sectionId The ID of the section.
     * @return True if the student is already enrolled in the section, otherwise false.
     */
    public boolean isStudentAlreadyEnrolled(int studentId, int sectionId) {
        String sql = "SELECT count(*) AS count FROM enrollment WHERE studentid = ? AND sectionid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, sectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true; // Enrollment exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No enrollment found
    }
    /**
     * Checks if an enrollment exists in the database by its ID.
     *
     * @param enrollmentId The ID of the enrollment.
     * @return True if the enrollment exists, otherwise false.
     */
    public boolean existsById(int enrollmentId) {
        String sql = "SELECT count(*) AS count FROM enrollment WHERE enrollmentid = ?";
        try (PreparedStatement ps = DB_connect.getConnection().prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true; // Enrollment exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No enrollment found
    }
    
}
