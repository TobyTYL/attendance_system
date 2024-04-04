package edu.duke.ece651.team1.data_access.Notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotificationPreferenceDaoImp implements NotificationPreferenceDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/schoolmanagement";
    private static final String USER = "ece651";
    private static final String PASSWORD = "passw0rd";

    @Override
    public void updateNotificationPreference(int studentId, int classId, boolean receiveNotifications) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
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
}
