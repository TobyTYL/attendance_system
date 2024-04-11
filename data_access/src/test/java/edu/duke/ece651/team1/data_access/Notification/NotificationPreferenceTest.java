package edu.duke.ece651.team1.data_access.Notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationPreferenceTest {

    @Test
    public void testNotificationPreference() {
        int preferenceId = 1;
        int studentId = 123;
        int classId = 456;
        boolean receiveNotifications = true;

        NotificationPreference preference = new NotificationPreference(preferenceId, studentId, classId, receiveNotifications);

        assertEquals(preferenceId, preference.getPreferenceId());
        assertEquals(studentId, preference.getStudentId());
        assertEquals(classId, preference.getClassId());
        assertEquals(receiveNotifications, preference.isReceiveNotifications());
    }

    @Test
    public void testSettersAndGetters() {
        NotificationPreference preference = new NotificationPreference(1, 123, 456, true);

        int newPreferenceId = 2;
        int newStudentId = 789;
        int newClassId = 987;
        boolean newReceiveNotifications = false;

        preference.setPreferenceId(newPreferenceId);
        preference.setStudentId(newStudentId);
        preference.setClassId(newClassId);
        preference.setReceiveNotifications(newReceiveNotifications);

        assertEquals(newPreferenceId, preference.getPreferenceId());
        assertEquals(newStudentId, preference.getStudentId());
        assertEquals(newClassId, preference.getClassId());
        assertEquals(newReceiveNotifications, preference.isReceiveNotifications());
    }

    @Test
    public void testToString() {
        NotificationPreference preference = new NotificationPreference(1, 123, 456, true);
        String expectedString = "NotificationPreference{preferenceId=1, studentId=123, classId=456, receiveNotifications=true}";

        assertEquals(expectedString, preference.toString());
    }
}
