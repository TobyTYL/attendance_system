package edu.duke.ece651.team1.data_access.Notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import edu.duke.ece651.team1.data_access.DB_connect;
/**
 * The NotificationPreferenceDaoImp class provides implementation for accessing notification preference data in the database.
 */
public class NotificationPreferenceDaoImp implements NotificationPreferenceDao {
    /**
     * Updates the notification preference for a specific student and class.
     *
     * @param studentId           The ID of the student.
     * @param classId             The ID of the class.
     * @param receiveNotifications True if the student should receive notifications, false otherwise.
     */
    @Override
    public void updateNotificationPreference(int studentId, int classId, boolean receiveNotifications) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "UPDATE NotificationPreference SET ReceiveNotifications = ? WHERE StudentID = ? AND ClassID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBoolean(1, receiveNotifications);
            statement.setInt(2, studentId);
            statement.setInt(3, classId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Finds the notification preference for a specific student and class.
     *
     * @param studentId The ID of the student.
     * @param classId   The ID of the class.
     * @return The notification preference found in the database.
     */
    @Override
    public NotificationPreference findNotificationPreferenceByStudentIdAndClassId(int studentId, int classId) {
        // TODO Auto-generated method stub
        NotificationPreference preference = null;
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "SELECT * FROM NotificationPreference WHERE StudentID = ? AND ClassID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setInt(2, classId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                boolean receiveNotifications = rs.getBoolean("ReceiveNotifications");
                int PreferenceId = rs.getInt("PreferenceID");
                preference = new NotificationPreference(PreferenceId,studentId, classId, receiveNotifications);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preference;
    }
    /**
     * Adds a new notification preference for a student and class.
     *
     * @param studentId           The ID of the student.
     * @param classID             The ID of the class.
     * @param receiveNotifications True if the student should receive notifications, false otherwise.
     */
    @Override
    public void addNotificationPreference(int studentId, int classID, boolean receiveNotifications) {
        try (Connection conn = DB_connect.getConnection()) {
            String sql = "INSERT INTO NotificationPreference (StudentID, ClassID, ReceiveNotifications) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setInt(2, classID);
            statement.setBoolean(3, receiveNotifications);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
    }
}
