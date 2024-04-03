package edu.duke.ece651.team1.data_access.user;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.duke.ece651.team1.shared.User;
import org.junit.jupiter.api.Test;

class UserDaoImpTest {
    @Test
    void testAddUser() {
        UserDaoImp userDaoImp = new UserDaoImp();
        userDaoImp.addUser(new User(1, "Toby", "Password Hash", "jane.doe@example.org", "Student"));
    }
    @Test
    void testRemoveUser() {
        (new UserDaoImp()).removeUser(1);
    }

    @Test
    void testUpdateUser() {
        UserDaoImp userDaoImp = new UserDaoImp();
        userDaoImp.updateUser(new User(1, "Test", "Password Hash", "jane.doe@example.org", "Professor"));
    }

    @Test
    void testGetUserById() {
        assertNull((new UserDaoImp()).getUserById(1));
    }

    @Test
    void testGetAllUsers() {
        assertTrue((new UserDaoImp()).getAllUsers().isEmpty());
    }
}
