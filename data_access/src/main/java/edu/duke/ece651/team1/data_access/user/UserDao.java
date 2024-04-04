package edu.duke.ece651.team1.data_access.user;

import edu.duke.ece651.team1.shared.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void removeUser(int userId);
    void updateUser(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
}
