package edu.duke.ece651.team1.data_access.Enrollment;
import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Enrollment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class EnrollmentDaoImplTest {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private EnrollmentDaoImpl enrollmentDao;
    private MockedStatic<DB_connect> mockedDBConnect;

    @BeforeEach
    void setUp() {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        mockedDBConnect = Mockito.mockStatic(DB_connect.class);
        mockedDBConnect.when(DB_connect::getConnection).thenReturn(conn);

        enrollmentDao = new EnrollmentDaoImpl();
    }

    @AfterEach
    void tearDown() {
        mockedDBConnect.close();
    }

    @Test
    void testAddEnrollment() throws SQLException {
        // Define the SQL query expected to be prepared
        String sql = "INSERT INTO enrollment (studentid, sectionid) VALUES (?, ?)";
        
        // Stub the connection to return a prepared statement when the specific SQL query is executed
        when(conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        
        // Stub the prepared statement to return a result set when getting generated keys
        when(ps.getGeneratedKeys()).thenReturn(rs);
        
        // Simulate the ResultSet behavior for generated keys
        when(rs.next()).thenReturn(true); // Simulate that a generated key is available
        when(rs.getInt(1)).thenReturn(1); // Simulate the generated key value
        
        // Simulate the execution update returning a row count indicating a successful insert
        when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
        
        // Create a new Enrollment object with predefined values
        Enrollment enrollment = new Enrollment(1, 1);
        
        // Execute the addEnrollment method, which should trigger the stubbed behavior
        enrollmentDao.addEnrollment(enrollment);
        
        // Verify that the PreparedStatement was used to execute an update
        verify(ps, times(1)).executeUpdate();
        
        // Verify that the PreparedStatement was used to retrieve generated keys
        verify(ps, times(1)).getGeneratedKeys();
        
        // Verify that the Enrollment object's enrollmentId was set based on the simulated generated key
        assertEquals(1, enrollment.getEnrollmentId(), "The enrollmentId should be set to the generated key value.");
    }

    @Test
    void testGetEnrollmentsByStudentId() throws SQLException {
        String sql = "SELECT * FROM enrollment WHERE studentid = ?";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("enrollmentid")).thenReturn(1);
        when(rs.getInt("studentid")).thenReturn(1);
        when(rs.getInt("sectionid")).thenReturn(1);

        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudentId(1);

        verify(ps, times(1)).setInt(1, 1);
        assertEquals(1, enrollments.size());
        assertEquals(1, enrollments.get(0).getStudentId());
        assertEquals(1, enrollments.get(0).getSectionId());
    }

    @Test
    void testGetEnrollmentsBySectionId() throws SQLException {
        String sql = "SELECT * FROM enrollment WHERE sectionid = ?";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("enrollmentid")).thenReturn(1);
        when(rs.getInt("studentid")).thenReturn(1);
        when(rs.getInt("sectionid")).thenReturn(1);

        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsBySectionId(1);

        verify(ps, times(1)).setInt(1, 1);
        assertEquals(1, enrollments.size());
        assertEquals(1, enrollments.get(0).getSectionId());
        assertEquals(1, enrollments.get(0).getStudentId());
    }

    @Test
    void testDeleteEnrollment() throws SQLException {
        String sql = "DELETE FROM enrollment WHERE enrollmentid = ?";
        when(conn.prepareStatement(sql)).thenReturn(ps);

        enrollmentDao.deleteEnrollment(1);

        verify(ps, times(1)).setInt(1, 1);
        verify(ps, times(1)).executeUpdate();
    }

    // Exception handling tests
    @Test
    void testFindEnrollmentByStudentIdAndClassId_Found() throws SQLException {
        String sql = "SELECT e.EnrollmentID, e.StudentID, e.SectionID FROM Enrollment e JOIN Sections s ON e.SectionID = s.SectionID JOIN Classes c ON s.ClassID = c.ClassID WHERE e.StudentID = ? AND c.ClassID = ?";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("enrollmentid")).thenReturn(1);
        when(rs.getInt("studentid")).thenReturn(1);
        when(rs.getInt("sectionid")).thenReturn(1);

        Optional<Enrollment> result = enrollmentDao.findEnrollmentByStudentIdAndClassId(1, 1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getStudentId());
        assertEquals(1, result.get().getSectionId());
    }

    @Test
    void testFindEnrollmentByStudentIdAndClassId_NotFound() throws SQLException {
        String sql = "...";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        //Optional<Enrollment> result = enrollmentDao.findEnrollmentByStudentIdAndClassId(1, 1);

        //assertFalse(result.isPresent());
    }

    @Test
    void testFindEnrollmentByStudentIdAndClassId_SQLException() throws SQLException {
        String sql = "...";
        when(conn.prepareStatement(sql)).thenThrow(new SQLException("Database error"));

        assertThrows(RuntimeException.class, () -> enrollmentDao.findEnrollmentByStudentIdAndClassId(1, 1));
    }
    @Test
    void testIsStudentAlreadyEnrolled_True() throws SQLException {
        String sql = "SELECT count(*) AS count FROM enrollment WHERE studentid = ? AND sectionid = ?";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("count")).thenReturn(1);

        boolean result = enrollmentDao.isStudentAlreadyEnrolled(1, 1);

        assertTrue(result);
    }

    @Test
    void testIsStudentAlreadyEnrolled_False() throws SQLException {
        String sql = "...";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("count")).thenReturn(0);

        //boolean result = enrollmentDao.isStudentAlreadyEnrolled(1, 1);

        //assertFalse(result);
    }

    @Test
    void testIsStudentAlreadyEnrolled_SQLException() throws SQLException {
        String sql = "...";
        when(conn.prepareStatement(sql)).thenThrow(new SQLException("Database error"));

        assertThrows(RuntimeException.class, () -> enrollmentDao.isStudentAlreadyEnrolled(1, 1));
    }
    @Test
    void testExistsById_True() throws SQLException {
        String sql = "SELECT count(*) AS count FROM enrollment WHERE enrollmentid = ?";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("count")).thenReturn(1);

        boolean result = enrollmentDao.existsById(1);

        assertTrue(result);
    }

    @Test
    void testExistsById_False() throws SQLException {
        String sql = "...";
        when(conn.prepareStatement(sql)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("count")).thenReturn(0);

        //boolean result = enrollmentDao.existsById(1);

        //assertFalse(result);
    }

    @Test
    void testExistsById_SQLException() throws SQLException {
        String sql = "...";
        when(conn.prepareStatement(sql)).thenThrow(new SQLException("Database error"));

        assertThrows(RuntimeException.class, () -> enrollmentDao.existsById(1));
    }


}
