package edu.duke.ece651.team1.data_access.Student;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.shared.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;

public class StudentDaoImpTest {
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement ps;
    private StudentDaoImp studentDao;
    @Mock
    private MockedStatic<DB_connect> mockedDBConnect;
    @Mock
    private ResultSet rs;

    @BeforeEach
    void setUp() {
        conn = mock(Connection.class);
        rs = mock(ResultSet.class);
        ps = mock(PreparedStatement.class);
        mockedDBConnect = Mockito.mockStatic(DB_connect.class);
        mockedDBConnect.when(DB_connect::getConnection).thenReturn(conn);
        studentDao = new StudentDaoImp();
    }

    @AfterEach
    void tearDown() {
        mockedDBConnect.close();
    }

    @Test
    public void testAddStudent() throws SQLException {
        StudentDaoImp spyStudentDao = Mockito.spy(new StudentDaoImp());
        doReturn(true).when(spyStudentDao).userExists(any(Connection.class), anyInt());
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
        Student student = new Student(1, "Toby", "tt", "9999@gmail.com", 1001);
        spyStudentDao.addStudent(student);
        verify(ps).executeUpdate();
    }

    @Test
    public void testUserExists() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);
        StudentDaoImp studentDao = new StudentDaoImp();
        assertTrue(studentDao.userExists(conn, 1001));
    }

    @Test
    public void testUserExists_UserNotExists() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        StudentDaoImp studentDao = new StudentDaoImp();
        assertFalse(studentDao.userExists(conn, 1001));
    }

    @Test
    public void testRemoveStudent() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        Student student = new Student(1, "Toby", "tt", "9999@gmail.com", 1001);
        studentDao.removeStudent(student);
        verify(ps).setInt(1, student.getStudentId());
        verify(ps).executeUpdate();
    }

    @Test
    public void testFindStudentByStudentID() throws SQLException {
        when(conn.prepareStatement("SELECT * FROM Students WHERE StudentID = ?")).thenReturn(ps);
        Integer studentID = 1;
        Student student = new Student("Toby", "Toby");
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString("LegalName")).thenReturn(student.getLegalName());
//        when(rs.getInt("UserID")).thenReturn(student.getUserId());
        when(rs.getString("DisplayName")).thenReturn(student.getDisPlayName());
//        when(rs.getString("Email")).thenReturn(student.getEmail());
        Optional<Student> result = studentDao.findStudentByStudentID(studentID);
//        assertEquals(Optional.of(student), result);
    }


    @Test
    public void testGetAllStudents() throws SQLException {
        when(conn.prepareStatement("SELECT * FROM Students")).thenReturn(ps);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "Toby", "tt", "9999@gmail.com", 1001));
        expectedStudents.add(new Student(2, "Alice", "aa", "alice@gmail.com", 1002));
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("StudentID")).thenReturn(1, 2);
        when(rs.getInt("UserID")).thenReturn(1001, 1002);
        when(rs.getString("LegalName")).thenReturn("Toby", "Alice");
        when(rs.getString("DisplayName")).thenReturn("tt", "aa");
        when(rs.getString("Email")).thenReturn("9999@gmail.com", "alice@gmail.com");
        List<Student> result = studentDao.getAllStudents();
        assertEquals(expectedStudents, result);
    }

    @Test
    public void testFindStudentByUserID() throws SQLException {
        when(conn.prepareStatement("SELECT * FROM Students WHERE UserID = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("StudentID")).thenReturn(1);
        when(rs.getString("LegalName")).thenReturn("Toby");
        when(rs.getString("DisplayName")).thenReturn("tt");
        when(rs.getString("Email")).thenReturn("9999@gmail.com");
        Optional<Student> result = studentDao.findStudentByUserID(1001);
        assertEquals(Optional.of(new Student(1, "Toby", "tt", "9999@gmail.com", 1001)), result);
    }

    @Test
    public void testCheckStudentExists() throws SQLException {
        when(conn.prepareStatement("SELECT * FROM Students WHERE LegalName = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        boolean result = studentDao.checkStudentExists("Toby");
        assertTrue(result);
    }
    @Test
    public void testFindStudentByName() throws SQLException {

        when(conn.prepareStatement("SELECT * FROM Students WHERE LegalName = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("UserID")).thenReturn(1001);
        when(rs.getInt("StudentID")).thenReturn(1);
        when(rs.getString("LegalName")).thenReturn("Toby");
        when(rs.getString("DisplayName")).thenReturn("tt");
        when(rs.getString("Email")).thenReturn("9999@gmail.com");

        Optional<Student> result = studentDao.findStudentByName("Toby");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getStudentId());
        assertEquals("Toby", result.get().getLegalName());
        assertEquals("tt", result.get().getDisPlayName());
        assertEquals("9999@gmail.com", result.get().getEmail());
        assertEquals(1001, result.get().getUserId());
    }
    @Test
    public void testUpdateStudent() throws SQLException {
        Student student = new Student(1, "Toby", "tt", "9999@gmail.com", 1001);
        when(conn.prepareStatement("UPDATE Students SET DisplayName = ?, Email = ? WHERE StudentID = ?")).thenReturn(ps);
        studentDao.updateStudent(student);
        verify(ps).setString(1, student.getDisPlayName());
        verify(ps).setString(2, student.getEmail());
        verify(ps).setInt(3, student.getStudentId());
        verify(ps).executeUpdate(); // 验证执行更新方法被调用
    }
}
