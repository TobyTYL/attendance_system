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
    @Test
    public void testCheckProfessorExists() throws SQLException {
        when(DB_connect.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        boolean result = professorDao.checkProfessorExists("ProfessorName");
        assertTrue(result);
    }


    @Test
    public void testFindProfessorByName() throws SQLException {
        when(DB_connect.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true); // 模拟结果集有下一行数据
        when(rs.getInt("ProfessorID")).thenReturn(1); // 设置模拟返回的教授 ID
        when(rs.getInt("UserID")).thenReturn(1001); // 设置模拟返回的用户 ID

        Professor result = professorDao.findProfessorByName("ProfessorName");

        // 验证
        assertNotNull(result); // 检查返回结果不为空
        assertEquals(1, result.getProfessorId()); // 检查返回的教授 ID 是否正确
        assertEquals(1001, result.getUserId()); // 检查返回的用户 ID 是否正确
    }
}
