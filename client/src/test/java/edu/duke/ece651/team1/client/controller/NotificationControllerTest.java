package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ContextConfiguration(classes = { NotificationController.class })
@ExtendWith(SpringExtension.class)
public class NotificationControllerTest {
    @MockBean
    private NotificationService notificationService;
    @Autowired
    private NotificationController controller;
    private Model model;
    private RedirectAttributes redirectAttributes;
    int classId = 1;
    int sectionId = 1;

    @BeforeEach
    public void setUp() {
        model = new ExtendedModelMap();
        redirectAttributes = new RedirectAttributesModelMap();
    }

    @Test
    public void testViewNotification() {
        boolean mockNotificationPreference = true;
        when(notificationService.getReceiveNotification(classId)).thenReturn(mockNotificationPreference);
        String viewName = controller.viewNotification(sectionId, classId, model);
        assertEquals("notification", viewName);
        verify(notificationService).getReceiveNotification(classId);
        assertEquals(mockNotificationPreference, model.getAttribute("notification"));
        assertEquals(UserSession.getInstance().getUid(), model.getAttribute("uid"));
        assertEquals(classId, model.getAttribute("classId"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
    }

    @Test
    public void testUpdateNotification() {
        boolean receiveNotifications = false;
        String expectedRedirect = "redirect:/notification/" + classId + "/" + sectionId;
        String actualRedirect = controller.updateNotification(sectionId, classId, receiveNotifications,
                redirectAttributes);
        verify(notificationService).updateNotification(receiveNotifications, classId);
        assertEquals(expectedRedirect, actualRedirect);
        assertEquals("You successfully update your Notification",
                redirectAttributes.getFlashAttributes().get("successMessage"));
    }

}
