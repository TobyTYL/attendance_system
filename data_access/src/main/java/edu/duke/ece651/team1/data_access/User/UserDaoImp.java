package edu.duke.ece651.team1.data_access.User;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The UserDaoImp class provides implementation for accessing user data in the database.
 */
public class UserDaoImp implements UserDao {
    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added.
     * @return The ID of the added user.
     */
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
    /**
     * Removes a user from the database.
     *
     * @param userId The ID of the user to be removed.
     */
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

//    @Override
//    public void updateUser(User user) {
//    //  need update user's info later
//    }

    /**
     * Retrieves a user from the database by ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user retrieved from the database.
     */
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
                //edit here slash role
                user = new User(userIdResult, username, passwordHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users retrieved from the database.
     */
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
                String email = rs.getString("Email");
                //String role = rs.getString("Role");
                User user = new User(userId, username, passwordHash);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    /**
     * Finds a user in the database by username.
     *
     * @param username The username of the user to find.
     * @return The user retrieved from the database.
     */
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
                String role = rs.getString("Role");
                user = new User(userId, username, passwordHash, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
