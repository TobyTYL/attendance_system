package edu.duke.ece651.team1.data_access.Notification;

import java.util.List;
public interface NotificationPreferenceDao {
    void updateNotificationPreference(int studentId, int classId, boolean receiveNotifications);
    NotificationPreference findNotificationPreferenceByStudentIdAndClassId(int studentId, int classId);
    void addNotificationPreference(int studentId, int classID, boolean receiveNotifications);
}
