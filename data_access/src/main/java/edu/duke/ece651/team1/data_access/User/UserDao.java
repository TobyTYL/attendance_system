package edu.duke.ece651.team1.data_access.User;

import edu.duke.ece651.team1.shared.User;

import java.util.List;

public interface UserDao {
    int addUser(User user);
    void removeUser(int userId);
//    void updateUser(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
    User findUserByUsername(String username);

}
