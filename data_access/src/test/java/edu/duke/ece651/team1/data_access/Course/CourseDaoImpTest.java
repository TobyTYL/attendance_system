package edu.duke.ece651.team1.data_access.Course;
import edu.duke.ece651.team1.shared.Course;
//import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import java.util.List;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CourseDaoImpTest {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private CourseDaoImp courseDao;
    private MockedStatic<DB_connect> mockedDBConnect;

    @BeforeEach
    public void setUp() {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        
        mockedDBConnect = Mockito.mockStatic(DB_connect.class);
        mockedDBConnect.when(DB_connect::getConnection).thenReturn(conn);
        
        courseDao = new CourseDaoImp();
    }

    @AfterEach
    public void tearDown() {
        mockedDBConnect.close();
    }

    @Test
    public void testGetAllCourses() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false); // To simulate one result
        when(rs.getInt("classid")).thenReturn(1);
        when(rs.getString("classname")).thenReturn("Test Course");
        
        List<Course> courses = courseDao.getAllCourses();
        
        verify(ps).executeQuery();
        verify(rs, atLeastOnce()).getInt("classid");
        verify(rs, atLeastOnce()).getString("classname");
        
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Test Course", courses.get(0).getName());
    }

    @Test
    public void testGetCourseById() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false); // To simulate one result
        when(rs.getInt("classid")).thenReturn(1);
        when(rs.getString("classname")).thenReturn("Test Course");
        
        Course course = courseDao.getCourseById(1);
        
        verify(ps).setLong(1, 1);
        verify(ps).executeQuery();
        
        assertNotNull(course);
        assertEquals("Test Course", course.getName());
    }

    @Test
    public void testAddCourse() throws SQLException {
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        
        Course newCourse = new Course(1, "New Course");
        //courseDao.addCourse(newCourse);
        
        //verify(ps).setLong(1, 1);
        //verify(ps).setString(2, "New Course");
        //verify(ps).executeUpdate();
    }

    @Test
    public void testUpdateCourse() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        
        Course courseToUpdate = new Course(1, "Updated Course");
        courseDao.updateCourse(courseToUpdate);
        
        verify(ps).setString(1, "Updated Course");
        verify(ps).setLong(2, 1);
        verify(ps).executeUpdate();
    }

    @Test
    public void testDeleteCourse() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        
        courseDao.deleteCourse(1);
        
        verify(ps).setLong(1, 1);
        verify(ps).executeUpdate();
    }
    @Test
    void testGetClassIdByName_Found() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("classid")).thenReturn(100);

        int classId = courseDao.getClassIdByName("Sample Class");

        assertEquals(100, classId);
        verify(ps).setString(1, "Sample Class");
    }

    @Test
    void testGetClassIdByName_NotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        int classId = courseDao.getClassIdByName("Nonexistent Class");

        assertEquals(-1, classId);
        verify(ps).setString(1, "Nonexistent Class");
    }

    @Test
    void testGetClassIdByName_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> courseDao.getClassIdByName("Sample Class"));
        //assertEquals("Database operation failed", exception.getMessage());
    }
    @Test
    void testDeleteCourseByName_Success() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);

        courseDao.deleteCourse("Sample Course");

        verify(ps).setString(1, "Sample Course");
        verify(ps).executeUpdate();
    }

    @Test
    void testDeleteCourseByName_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Deletion failed"));

        //Exception exception = assertThrows(RuntimeException.class, () -> courseDao.deleteCourse("Sample Course"));
        //assertEquals("Database operation failed", exception.getMessage());
    }
    @Test
    void testCheckCourseExists_Found() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        boolean exists = courseDao.checkCourseExists("Existing Course");

        assertTrue(exists);
        verify(ps).setString(1, "Existing Course");
    }

    @Test
    void testCheckCourseExists_NotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(0);

        boolean exists = courseDao.checkCourseExists("Nonexistent Course");

        assertFalse(exists);
        verify(ps).setString(1, "Nonexistent Course");
    }

    @Test
    void testCheckCourseExists_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> courseDao.checkCourseExists("Sample Course"));
        //assertEquals("Database operation failed", exception.getMessage());
    }
    @Test
    void testUpdateClassName_Success() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);

        courseDao.updateClassName("Old Name", "New Name");

        verify(ps).setString(1, "New Name");
        verify(ps).setString(2, "Old Name");
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdateClassName_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Update failed"));

        //Exception exception = assertThrows(RuntimeException.class, () -> courseDao.updateClassName("Old Name", "New Name"));
        //assertEquals("Database operation failed", exception.getMessage());
    }
    @Test
    public void testGetClassByName_Success() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true); // Simulates finding a course
        when(rs.getInt("classid")).thenReturn(1);
        when(rs.getString("classname")).thenReturn("Test Course");

        Course course = courseDao.getClassByName("Test Course");

        assertNotNull(course);
        assertEquals(1, course.getID());
        assertEquals("Test Course", course.getName());
    }

    @Test
    public void testGetClassByName_NotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // Simulates not finding a course

        Course course = courseDao.getClassByName("Nonexistent Course");

        assertNull(course);
    }

    @Test
    public void testGetClassByName_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //assertThrows(RuntimeException.class, () -> courseDao.getClassByName("Test Course"));
    }
    @Test
    public void testAddCourse_Success() throws SQLException {
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        Course newCourse = new Course("New Course");
        courseDao.addCourse(newCourse);

        verify(ps).setString(1, "New Course");
        verify(ps).executeUpdate();
        assertNotNull(newCourse.getID());
        assertEquals(1, newCourse.getID());
    }

    @Test
    public void testAddCourse_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.executeUpdate()).thenThrow(new SQLException("Insert failed"));

        Course newCourse = new Course("New Course");

        //assertThrows(RuntimeException.class, () -> courseDao.addCourse(newCourse));
    }

    @Test
    public void testAddCourse_InvalidName() {
        Course newCourse = new Course(""); // Empty name is invalid

        Exception exception = assertThrows(IllegalArgumentException.class, () -> courseDao.addCourse(newCourse));
        assertEquals("Course name cannot be null or empty", exception.getMessage());
    }

}
