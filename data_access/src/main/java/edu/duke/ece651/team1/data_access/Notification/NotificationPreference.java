package edu.duke.ece651.team1.data_access.Notification;

public class NotificationPreference {
    private int preferenceId;
    private int studentId;
    private int classId;
    private boolean receiveNotifications;

    public NotificationPreference(int preferenceId, int studentId, int classId, boolean receiveNotifications) {
        this.preferenceId = preferenceId;
        this.studentId = studentId;
        this.classId = classId;
        this.receiveNotifications = receiveNotifications;
    }

   

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public boolean isReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }

    @Override
    public String toString() {
        return "NotificationPreference{" +
                "preferenceId=" + preferenceId +
                ", studentId=" + studentId +
                ", classId=" + classId +
                ", receiveNotifications=" + receiveNotifications +
                '}';
    }
}
