package edu.duke.ece651.team1.server.model;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class NotificationServiceTest {

    private NotificationService notificationService;
    private Notification mockNotification1;
    private Notification mockNotification2;
    String testMessage = "Test message";
    String testRecipient = "test@example.com";

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService();
        mockNotification1 = mock(Notification.class);
        mockNotification2 = mock(Notification.class);
    }

    @Test
    void testAddAndNotifyObserver() {
       
        notificationService.addNotification(mockNotification1);
        notificationService.addNotification(mockNotification2);
        notificationService.notifyObserver(testMessage, testRecipient);
        verify(mockNotification1, times(1)).notify(testMessage, testRecipient);
        verify(mockNotification2, times(1)).notify(testMessage, testRecipient);
    }

    @Test
    void testDeleteObserver() {
        notificationService.addNotification(mockNotification1);
        notificationService.addNotification(mockNotification2);
        notificationService.deleteNotification(mockNotification1);
        notificationService.notifyObserver(testMessage, testRecipient);
        verify(mockNotification1, never()).notify(testMessage, testRecipient);
        verify(mockNotification2, times(1)).notify(testMessage, testRecipient);
    }
}
