package edu.duke.ece651.team1.dao.user;
import java.util.List;
public interface UserDao {
    void addUser(User user);
    void removeUser(int userId);
    void updateUser(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
}
