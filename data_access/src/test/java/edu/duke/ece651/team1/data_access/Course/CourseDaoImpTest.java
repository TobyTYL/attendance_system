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
        courseDao.addCourse(newCourse);
        
        //verify(ps).setLong(1, 1);
        verify(ps).setString(1, "New Course");
        verify(ps).executeUpdate();
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

}
