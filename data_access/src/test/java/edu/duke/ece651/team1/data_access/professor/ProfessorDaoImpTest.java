package edu.duke.ece651.team1.data_access.Professor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.shared.Professor;

class ProfessorDaoImpTest {
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement ps;
    private ProfessorDaoImp professorDao;
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
        professorDao = new ProfessorDaoImp();
    }
    @AfterEach
    void tearDown() {
        mockedDBConnect.close();
    }

    @Test
    void testAddProfessor() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        Professor professor = new Professor(1, 1001);
        professorDao.addProfessor(professor);
        verify(conn).prepareStatement("INSERT INTO Professors (UserID) VALUES (?)");
        verify(ps).setInt(1, professor.getUserId());
        verify(ps).executeUpdate();
    }
    @Test
    void testRemoveProfessor() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        professorDao.removeProfessor(1);
        verify(conn).prepareStatement("DELETE FROM Professors WHERE ProfessorID = ?");
        verify(ps).setInt(1, 1);
        verify(ps).executeUpdate();
    }
    @Test
    void testFindAllProfessors() throws SQLException {
        List<Professor> expectedProfessorList = new ArrayList<>();
        expectedProfessorList.add(new Professor(1, 1001));
        expectedProfessorList.add(new Professor(2, 1002));
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("ProfessorID")).thenReturn(1, 2);
        when(rs.getInt("UserID")).thenReturn(1001, 1002);
        List<Professor> actualProfessorList = professorDao.findAllProfessors();
        verify(conn).prepareStatement("SELECT * FROM Professors");
        verify(ps).executeQuery();
        assertEquals(expectedProfessorList.size(), actualProfessorList.size());
        for (int i = 0; i < expectedProfessorList.size(); i++) {
            assertEquals(expectedProfessorList.get(i).getProfessorId(), actualProfessorList.get(i).getProfessorId());
            assertEquals(expectedProfessorList.get(i).getUserId(), actualProfessorList.get(i).getUserId());
        }
    }

    @Test
    void testGetProfessorById() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("UserID")).thenReturn(1001);
        Professor professor = professorDao.getProfessorById(1);
        verify(conn).prepareStatement("SELECT * FROM Professors WHERE ProfessorID = ?");
        verify(ps).setInt(1, 1);
        verify(ps).executeQuery();
        assertEquals(1, professor.getProfessorId());
        assertEquals(1001, professor.getUserId());
    }
    @Test
    void testFindProfessorByUsrID() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("ProfessorID")).thenReturn(1);
        Professor professor = professorDao.findProfessorByUsrID(1001);
        verify(conn).prepareStatement("SELECT * FROM Professors WHERE UserID = ?");
        verify(ps).setInt(1, 1001);
        verify(ps).executeQuery();
        assertEquals(1, professor.getProfessorId());
        assertEquals(1001, professor.getUserId());
    }
// need to retest testCheckProfessorExists
//    @Test
//    void testCheckProfessorExists() throws SQLException {
//        // Mocking necessary objects
//        Connection conn = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        ResultSet rs = mock(ResultSet.class);
//
//        // Mocking behavior for the ResultSet
//        when(rs.next()).thenReturn(true); // Simulate that a professor exists
//
//        // Mocking behavior for the PreparedStatement
//        when(conn.prepareStatement(anyString())).thenReturn(ps); // Mock PreparedStatement creation
//        when(ps.executeQuery()).thenReturn(rs); // Mock execution of the PreparedStatement
//
//        // Creating an instance of ProfessorDaoImp to test the method
//        ProfessorDaoImp professorDao = new ProfessorDaoImp();
//
//        // Mocking the behavior of getUserIDByProfessorName method
//        doReturn(123).when(professorDao).getUserIDByProfessorName(any(Connection.class), eq("ProfessorName"));
//
//        // Invoking the method to test
//        boolean exists = professorDao.checkProfessorExists("ProfessorName");
//
//        // Verifying interactions and assertions
//        verify(conn).prepareStatement("SELECT * FROM Professors WHERE UserID = ?");
//        verify(ps).setInt(1, 123); // Assuming getUserIDByProfessorName returns 123
//        verify(ps).executeQuery();
//        assertTrue(exists); // Asserting that the professor exists
//    }

    @Test
    void testCheckProfessorExists() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true); // Simulate that a user is found
        when(rs.getInt("UserID")).thenReturn(123); // Simulate that the UserID is retrieved
        when(conn.prepareStatement(anyString())).thenReturn(ps); // Mock PreparedStatement creation
        when(ps.executeQuery()).thenReturn(rs); // Mock execution of the PreparedStatement
        ProfessorDaoImp professorDao = new ProfessorDaoImp();

//        int userId = professorDao.getUserIDByProfessorName(conn, "professorName");
//        verify(conn).prepareStatement("SELECT UserID FROM Users WHERE Username = ?");
        verify(ps).setString(1, "professorName");
        verify(ps).executeQuery();
        // 执行测试方法
        boolean exists = professorDao.checkProfessorExists("professorName");
        verify(conn).prepareStatement("SELECT * FROM Professors WHERE UserID = ?");
        verify(ps).setString(1, "professorName");
        assertEquals(true, exists); // Asserting that the correct UserID is returned

//        // 验证方法调用
//        verify(conn).prepareStatement("SELECT * FROM Professors WHERE UserID = ?");
//        verify(ps).setInt(1, 123); // 验证参数设置为预期的UserID值
//        verify(ps).executeQuery();
//        assertTrue(exists); // 验证教授存在的情况
    }



//    @Test
//    void testGetUserIDByProfessorName() throws SQLException {
//        Connection conn = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        ResultSet rs = mock(ResultSet.class);
//        when(rs.next()).thenReturn(true); // Simulate that a user is found
//        when(rs.getInt("UserID")).thenReturn(123); // Simulate that the UserID is retrieved
//        when(conn.prepareStatement(anyString())).thenReturn(ps); // Mock PreparedStatement creation
//        when(ps.executeQuery()).thenReturn(rs); // Mock execution of the PreparedStatement
//        ProfessorDaoImp professorDao = new ProfessorDaoImp();
//        int userId = professorDao.getUserIDByProfessorName(conn, "ProfessorName");
//        verify(conn).prepareStatement("SELECT UserID FROM Users WHERE Username = ?");
//        verify(ps).setString(1, "ProfessorName");
//        verify(ps).executeQuery();
//        assertEquals(123, userId); // Asserting that the correct UserID is returned
//    }
    // bug here, need to retest
//    @Test
//    void testFindProfessorByName() throws SQLException {
//        Connection conn = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        ResultSet rs = mock(ResultSet.class);
//        when(rs.next()).thenReturn(true); // Simulate that a professor is found
//        when(rs.getInt("ProfessorID")).thenReturn(1);
//        when(rs.getInt("UserID")).thenReturn(1001); // Assuming professor's user ID is 1001
//        when(conn.prepareStatement(anyString())).thenReturn(ps); // Mock PreparedStatement creation
//        when(ps.executeQuery()).thenReturn(rs); // Mock execution of the PreparedStatement
//        ProfessorDaoImp professorDao = new ProfessorDaoImp();
//        Professor professor = professorDao.findProfessorByName("ProfessorName");
//        verify(conn).prepareStatement("SELECT p.ProfessorID, p.UserID FROM Professors p JOIN Users u ON p.UserID = u.UserID WHERE u.Username = ?");
//        verify(ps).setString(1, "ProfessorName");
//        verify(ps).executeQuery();
//        assertNotNull(professor); // Asserting that professor is not null
//        assertEquals(1, professor.getProfessorId());
//        assertEquals(1001, professor.getUserId());
//    }
}
