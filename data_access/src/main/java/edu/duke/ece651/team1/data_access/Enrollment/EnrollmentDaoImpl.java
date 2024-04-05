package edu.duke.ece651.team1.data_access.Enrollment;
import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Enrollment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {
    // private Connection connection;

    // public EnrollmentDaoImpl(Connection connection) {
    //     this.connection = connection;
    // }

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

}
