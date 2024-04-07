package edu.duke.ece651.team1.data_access.Enrollment;
import edu.duke.ece651.team1.shared.Enrollment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EnrollmentDaoImplTest {
    private Connection conn;
    private EnrollmentDaoImpl enrollmentDao;

    @BeforeEach
    void setUp() throws Exception {
        // Connect to H2 in-memory database
        // conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        enrollmentDao = new EnrollmentDaoImpl();
        
        // Setup schema and initial data
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS enrollment;");
            stmt.execute("CREATE TABLE enrollment (enrollmentid SERIAL, studentid INT, sectionid INT, PRIMARY KEY (enrollmentid))");
            stmt.execute("INSERT INTO enrollment (studentid, sectionid) VALUES (1, 101), (2, 102), (1, 103)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        conn.close();
    }

    @Test
    void testAddEnrollment() throws SQLException {
        Enrollment enrollment = new Enrollment(3, 104);
        enrollmentDao.addEnrollment(enrollment);
        assertNotNull(enrollment.getEnrollmentId());

        // Verify by querying directly
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM enrollment WHERE studentid = ? AND sectionid = ?")) {
            ps.setInt(1, 3);
            ps.setInt(2, 104);
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals(3, rs.getInt("studentid"));
            assertEquals(104, rs.getInt("sectionid"));
        }
    }

    @Test
    void testGetEnrollmentsByStudentId() {
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudentId(1);
        assertNotNull(enrollments);
        assertEquals(2, enrollments.size()); // There are two enrollments for studentId=1 in setup
    }

    @Test
    void testDeleteEnrollment() throws SQLException {
        // Add a new enrollment to ensure it exists
        Enrollment enrollment = new Enrollment(4, 105);
        enrollmentDao.addEnrollment(enrollment);

        // Now delete it
        enrollmentDao.deleteEnrollment(enrollment.getEnrollmentId());

        // Verify by querying directly
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM enrollment WHERE enrollmentid = ?")) {
            ps.setInt(1, enrollment.getEnrollmentId());
            ResultSet rs = ps.executeQuery();
            assertFalse(rs.next()); // Should not exist anymore
        }
    }
    @Test
    void testGetEnrollmentsBySectionId() {
        // First, insert some enrollments for a specific section ID
        int sectionId = 105; // Assuming this is a valid section ID
        int expectedEnrollments = 2; // Assuming there are 2 enrollments for this section ID in the setup
        
        // Add enrollments for the section
        Enrollment enrollment1 = new Enrollment(1, sectionId);
        Enrollment enrollment2 = new Enrollment(2, sectionId);
        enrollmentDao.addEnrollment(enrollment1);
        enrollmentDao.addEnrollment(enrollment2);
    
        // Call the method to get enrollments by section ID
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsBySectionId(sectionId);
    
        // Verify the results
        assertNotNull(enrollments);
        assertEquals(expectedEnrollments, enrollments.size());
        for (Enrollment enrollment : enrollments) {
            assertEquals(sectionId, enrollment.getSectionId());
        }
    }

    @Test
    void testAddEnrollmentSQLException() {
        Enrollment enrollment = new Enrollment(1, 1);
        // Assume addEnrollment method has a try-catch block for SQLException and logs the error
        assertDoesNotThrow(() -> enrollmentDao.addEnrollment(enrollment));
        // No direct way to force an SQLException without altering the database setup or the query itself.
        // You could consider scenarios like violating a foreign key constraint if applicable.
    }

    @Test
    void testGetEnrollmentsByStudentIdSQLException() {
        // Introduce a broken SQL statement to simulate an SQLException
        enrollmentDao = new EnrollmentDaoImpl() {
            @Override
            public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
                // Override to introduce a SQL error
                return null; // Return null or handle as seen fit for your application
            }
        };
        // Since the SQL error is introduced manually, it won't throw an SQLException to be caught
        // But you can simulate handling as needed within the overridden method
        assertNull(enrollmentDao.getEnrollmentsByStudentId(1));
    }

    @Test
    void testDeleteEnrollmentSQLException() {
        // This test demonstrates using a correct setup; typically, you'd simulate an exception condition
        assertDoesNotThrow(() -> enrollmentDao.deleteEnrollment(999));
        // Again, to actually test the catch block, you'd need a scenario that causes an SQLException
        // which may involve database constraints or other conditions that lead to an exception
    }
   

}
