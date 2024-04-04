package edu.duke.ece651.team1.data_access.Notification;

import java.util.List;
public interface NotificationPreferenceDao {
    void updateNotificationPreference(int studentId, int classId, boolean receiveNotifications);
}
