package edu.duke.ece651.team1.data_access.User;

import edu.duke.ece651.team1.shared.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

class UserDaoImpTest {
    private Connection connection;
    private Statement statement;
    private UserDaoImp userDao;

    private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
    private static final String USER = "ece651";
    private static final String PASSWORD = "passw0rd";
    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        statement = connection.createStatement();
        userDao = new UserDaoImp(connection);
    }
    @AfterEach
    public void tearDown() throws SQLException {
        statement.close();
        connection.close();
    }

    @Test
    public void testAddUser() throws SQLException {
        User user = new User(1, "username", "passwordHash", "email@example.com", "role");
        userDao.addUser(user);

    }
//
//    @Test
//    void testAddUser() {
//        UserDaoImp userDaoImp = new UserDaoImp();
//        userDaoImp.addUser(new User(1, "Toby", "Password Hash", "jane.doe@example.org", "Student"));
//    }
//    @Test
//    void testRemoveUser() {
//        (new UserDaoImp()).removeUser(1);
//    }
//
//    @Test
//    void testUpdateUser() {
//        UserDaoImp userDaoImp = new UserDaoImp();
//        userDaoImp.updateUser(new User(1, "Test", "Password Hash", "jane.doe@example.org", "Professor"));
//    }
//
//    @Test
//    void testGetUserById() {
//        assertNull((new UserDaoImp()).getUserById(1));
//    }
//
//    @Test
//    void testGetAllUsers() {
//        assertTrue((new UserDaoImp()).getAllUsers().isEmpty());
//    }
//

}
