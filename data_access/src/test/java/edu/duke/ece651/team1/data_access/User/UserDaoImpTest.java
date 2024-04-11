package edu.duke.ece651.team1.data_access.User;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserDaoImpTest {
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement ps;
    private UserDaoImp userDao;
    @Mock
    private MockedStatic<DB_connect> mockedDBConnect;
    @Mock
    private ResultSet rs;
    private Statement statement;


    @BeforeEach
    void setUp() {
        conn = mock(Connection.class);
        rs = mock(ResultSet.class);
        ps = mock(PreparedStatement.class);
        mockedDBConnect = Mockito.mockStatic(DB_connect.class);
        mockedDBConnect.when(DB_connect::getConnection).thenReturn(conn);
        userDao = new UserDaoImp();
        statement = Mockito.mock(Statement.class); // 将创建的Statement对象赋值给类级的statement属性

    }

    @AfterEach
    void tearDown() {
        mockedDBConnect.close();
    }


    @Test
    public void testAddUser() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
        UserDao spyUserDao = Mockito.spy(new UserDaoImp());
        User user = new User("username", "password", "role");
        user.setUserId(1);
        doReturn(user).when(spyUserDao).findUserByUsername(anyString());
        // when(userDao.findUserByUsername(anyString())).thenReturn(new User("username", "password", "role"));
        // User user = new User("username", "password", "role");
//        UserDaoImp userDao = new UserDaoImp();
        spyUserDao.addUser(user);
    }

    @Test
    public void testRemoveUser() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        userDao.removeUser(1);
        verify(ps).executeUpdate();
    }

//    @Test
//    public void testUpdateUser() {
//        // Test updateUser() method
//    }

    @Test
    public void testGetUserById() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("UserID")).thenReturn(1);
        when(rs.getString("Username")).thenReturn("username");
        when(rs.getString("PasswordHash")).thenReturn("password");
//        when(rs.getString("Role")).thenReturn("role");

        User result = userDao.getUserById(1);

    }

    @Test
    public void testFindUserByUsername() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("UserID")).thenReturn(1);
        when(rs.getString("Username")).thenReturn("username");
        when(rs.getString("PasswordHash")).thenReturn("password");
        when(rs.getString("Role")).thenReturn("role");

        User result = userDao.findUserByUsername("username");
        assertEquals(1, result.getUserId());
        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPasswordHash());
        assertEquals("role", result.getRole());
    }


    @Test
    public void testGetAllUsers() throws SQLException {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1, "user1", "password1"));
        expectedUsers.add(new User(2, "user2", "password2"));
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("UserID")).thenReturn(1).thenReturn(2);
        when(rs.getString("Username")).thenReturn("user1").thenReturn("user2");
        when(rs.getString("PasswordHash")).thenReturn("password1").thenReturn("password2");
        userDao.getAllUsers();
//        assertEquals(expectedUsers, actualUsers);
    }
}
