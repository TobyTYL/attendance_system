package edu.duke.ece651.team1.data_access.User;


import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.shared.Student;
import edu.duke.ece651.team1.shared.User;
import org.junit.jupiter.api.*;
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

class UserDaoImpTest {
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement ps;
    private UserDaoImp userDao;
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
        userDao = new UserDaoImp();
//        userDao = mock(UserDaoImp.class);
    }

    @AfterEach
    void tearDown() {
        mockedDBConnect.close();
    }


    @Disabled
    @Test
    void testAddUser() throws SQLException {
        UserDaoImp spyUserDao = Mockito.spy(new UserDaoImp());
        doReturn(0).when(spyUserDao).addUser(any(User.class));

        when(conn.prepareStatement(anyString())).thenReturn(ps);
//        when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
        User user = new User(null, "user001", "hashedPwd", "admin");
        spyUserDao.addUser(user);
//        userDao.addUser(user);
//        verify(ps).executeUpdate();

    }


    @Disabled
    @Test
    void testAddUser2() throws SQLException {
        UserDaoImp spyUserDao = Mockito.spy(new UserDaoImp());
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("UserID")).thenReturn(10); // Mocked user ID
        when(ps.getGeneratedKeys()).thenReturn(rs);

        User user = new User(null, "user001", "hashedPwd", "admin");
        int userId = spyUserDao.addUser(user);
        verify(ps).executeUpdate();
        assertEquals(10, userId); // Verify that the user ID returned is as expected
    }

    // @Test
    // void testAddUser3() throws SQLException {
    //     // Set up mock behavior
    //     when(conn.prepareStatement(anyString())).thenReturn(ps);
    //     when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
    //     when(rs.next()).thenReturn(true);
    //     when(rs.getInt("UserID")).thenReturn(10); // Mocked user ID
    //     when(ps.getGeneratedKeys()).thenReturn(rs);

    //     User user = new User(null, "user001", "hashedPwd", "admin");

    //     // Call the method under test
    //     int userId = userDao.addUser(user);

    //     // Verify the interactions
    //     verify(ps).executeUpdate(); // Verify that executeUpdate is called on PreparedStatement
    //     assertEquals(10, userId); // Verify that the user ID returned is as expected
    // }

    @Test
    void testAddUser4() throws SQLException {
        // Set up mock behavior for addUser
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1); // Indicate that one row was affected
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true); // Mock for both addUser and findUserByUsername
        when(rs.getInt("UserID")).thenReturn(10); // Mocked user ID for addUser

        // Additional setup for findUserByUsername
        PreparedStatement psFind = mock(PreparedStatement.class);
        ResultSet rsFind = mock(ResultSet.class);
        when(conn.prepareStatement(startsWith("SELECT"))).thenReturn(psFind);
        when(psFind.executeQuery()).thenReturn(rsFind);
        when(rsFind.next()).thenReturn(true); // Simulate that findUserByUsername finds a user
        when(rsFind.getInt("UserID")).thenReturn(10); // Mocked user ID for findUserByUsername

        User user = new User(null, "user001", "hashedPwd", "admin");

        // Call the method under test
        int userId = userDao.addUser(user);

        // Verify the interactions
        verify(ps).executeUpdate(); // Verify that executeUpdate is called on PreparedStatement
        assertEquals(10, userId); // Verify that the user ID returned is as expected
    }












}

// import edu.duke.ece651.team1.shared.User;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.sql.*;

// class UserDaoImpTest {
//     private Connection connection;
//     private Statement statement;
//     private UserDaoImp userDao;

//     private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
//     private static final String USER = "ece651";
//     private static final String PASSWORD = "passw0rd";
//     @BeforeEach
//     public void setUp() throws SQLException {
//         connection = DriverManager.getConnection(URL, USER, PASSWORD);
//         statement = connection.createStatement();
//         userDao = new UserDaoImp(connection);
//     }
//     @AfterEach
//     public void tearDown() throws SQLException {
//         statement.close();
//         connection.close();
//     }

//     @Test
//     public void testAddUser() throws SQLException {
//         User user = new User(1, "username", "passwordHash", "email@example.com", "role");
//         userDao.addUser(user);

//     }
// //
// //    @Test
// //    void testAddUser() {
// //        UserDaoImp userDaoImp = new UserDaoImp();
// //        userDaoImp.addUser(new User(1, "Toby", "Password Hash", "jane.doe@example.org", "Student"));
// //    }
// //    @Test
// //    void testRemoveUser() {
// //        (new UserDaoImp()).removeUser(1);
// //    }
// //
// //    @Test
// //    void testUpdateUser() {
// //        UserDaoImp userDaoImp = new UserDaoImp();
// //        userDaoImp.updateUser(new User(1, "Test", "Password Hash", "jane.doe@example.org", "Professor"));
// //    }
// //
// //    @Test
// //    void testGetUserById() {
// //        assertNull((new UserDaoImp()).getUserById(1));
// //    }
// //
// //    @Test
// //    void testGetAllUsers() {
// //        assertTrue((new UserDaoImp()).getAllUsers().isEmpty());
// //    }
// //

// }
