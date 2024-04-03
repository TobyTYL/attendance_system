package edu.duke.ece651.team1.dao.user;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {
    private List<User> userList;
    public UserDaoImp() {
        this.userList = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        // When the user is professor
        if ("PROFESSOR".equals(user.getRole())) {
            userList.add(user);
        }
    }

    @Override
    public void removeUser(int userId) {
        // When the user is professor
        userList.removeIf(user -> user.getUserId() == userId && "PROFESSOR".equals(user.getRole()));
    }

    @Override
    public void updateUser(User user) {
        // Student can update their display name
    }

    @Override
    public User getUserById(int userId) {
        for (User user : userList) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        // 实现获取所有用户信息的逻辑
        return null;
    }
}
