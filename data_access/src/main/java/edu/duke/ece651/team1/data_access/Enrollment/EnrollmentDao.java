package edu.duke.ece651.team1.data_access.Enrollment;
import edu.duke.ece651.team1.shared.Enrollment;
import java.util.List;
public interface EnrollmentDao {
    void addEnrollment(Enrollment enrollment);
    List<Enrollment> getEnrollmentsByStudentId(int studentId);
    List<Enrollment> getEnrollmentsBySectionId(int sectionId);
    void deleteEnrollment(int enrollmentId);
}
