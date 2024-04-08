package edu.duke.ece651.team1.data_access.User;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {


    @Override
    public int addUser(User user) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "INSERT INTO Users (Username, PasswordHash, Role) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
            return findUserByUsername(user.getUsername()).getUserId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void removeUser(int userId) {
        try (Connection conn = DB_connect.getConnection()) {
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
    // 后续需要update用户信息
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Users WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int userIdResult = rs.getInt("UserID");
                String username = rs.getString("Username");
                String passwordHash = rs.getString("PasswordHash");
                // String email = rs.getString("Email");
                String role = rs.getString("Role");

                user = new User(userIdResult, username, passwordHash, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection conn = DB_connect.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                int userId = rs.getInt("UserID");
                String username = rs.getString("Username");
                String passwordHash = rs.getString("PasswordHash");
                // String email = rs.getString("Email");
                String role = rs.getString("Role");

                User user = new User(userId, username, passwordHash, role);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM Users WHERE Username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                String passwordHash = rs.getString("PasswordHash");
                // String email = rs.getString("Email");
                String role = rs.getString("Role");

                user = new User(userId, username, passwordHash, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
