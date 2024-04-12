package edu.duke.ece651.team1.server.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.duke.ece651.team1.data_access.Notification.*;
import java.sql.*;
import edu.duke.ece651.team1.shared.*;;;
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private NotificationPreferenceDao notificationDao;
    @InjectMocks
    private StudentService studentService;
    int studentId = 1;
    int courseId = 101;
    boolean preference = true;
    @Test
    public void updateNotificationPreference_Success() {
        doNothing().when(notificationDao).updateNotificationPreference(studentId, courseId, preference);
        studentService.updateNotificationPreference(studentId, courseId, preference);
        verify(notificationDao, times(1)).updateNotificationPreference(studentId, courseId, preference);
    }

    // @Test
    // public void updateNotificationPreference_Failure() throws SQLException {
    //     doThrow(new SQLException("Database error")).when(notificationDao).updateNotificationPreference(studentId, courseId, preference);
    //     assertThrows(SQLException.class, () -> {
    //         studentService.updateNotificationPreference(studentId, courseId, preference);
    //     });
    // }

    @Test
    public void getNotificationPreference_ReturnsCorrectInfo() {
        NotificationPreference mockPreference = new NotificationPreference(courseId, studentId, courseId, preference);
        when(notificationDao.findNotificationPreferenceByStudentIdAndClassId(studentId, courseId)).thenReturn(mockPreference);
        String result = studentService.getNotificationPreference(studentId, courseId);
        String expectedJson = "{\"ReceiveNotifications\":true}";
        assertEquals(expectedJson, result);
    }

    // @Test
    // public void getNotificationPreference_ReturnsNull() {
    //     when(notificationDao.findNotificationPreferenceByStudentIdAndClassId(studentId, courseId)).thenReturn(null);
    //     String result = studentService.getNotificationPreference(studentId, courseId);
    //     String expectedJson = "{}"; // Assuming the service handles null preference as an empty JSON object
    //     assertEquals(expectedJson, result);
    // }
}
