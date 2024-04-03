package edu.duke.ece651.team1.data_access.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
    private static final String USER = "ece651";
    private static final String PASSWORD = "passw0rd";

    @Override
    public void addUser(User user) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO Users (Username, PasswordHash, Email) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int userId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM Users WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        // 这里可以根据具体需求实现用户更新的逻辑
        // 例如：更新用户信息，修改用户名、密码、邮箱等
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM Users WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int userIdResult = rs.getInt("UserID");
                String username = rs.getString("Username");
                String passwordHash = rs.getString("PasswordHash");
                String email = rs.getString("Email");

                user = new User(userIdResult, username, passwordHash, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                int userId = rs.getInt("UserID");
                String username = rs.getString("Username");
                String passwordHash = rs.getString("PasswordHash");
                String email = rs.getString("Email");

                User user = new User(userId, username, passwordHash, email);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
