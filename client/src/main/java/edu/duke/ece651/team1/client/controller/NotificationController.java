package edu.duke.ece651.team1.client.controller;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The NotificationController class manages the notification preferences for a student in a specific class.
 * It handles operations such as retrieving the current notification preference and updating it.
 * This controller interacts with the NotificarionView for user input and utilizes a RestTemplate
 * for HTTP requests to the backend service.
 */
import org.springframework.web.bind.annotation.*;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    /**
     * Displays the current notification setting for a student regarding a specific
     * class and section.
     *
     * @param sectionId the ID of the section
     * @param classId   the ID of the class
     * @param model     the Spring Model object to pass data to the view
     * @return the name of the view to render
     */
    @GetMapping("/notification/{classId}/{sectionId}")
    public String viewNotification(@PathVariable int sectionId, @PathVariable int classId, Model model) {
        boolean notification = notificationService.getReceiveNotification(classId);
        model.addAttribute("notification", notification);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("classId", classId);
        model.addAttribute("sectionId", sectionId);
        return "notification";
    }

    /**
     * Updates the user's notification preferences for a specific class and section.
     *
     * @param sectionId            the ID of the section
     * @param classId              the ID of the class
     * @param receiveNotifications the new notification preference to set
     * @param redirectAttributes   attributes for a redirect scenario, used to show
     *                             success messages
     * @return the redirect path after updating notification preferences
     */
    @PostMapping("/notification/{classId}/{sectionId}")
    public String updateNotification(@PathVariable int sectionId, @PathVariable int classId,
            @RequestParam(value = "receiveNotifications") boolean receiveNotifications,
            RedirectAttributes redirectAttributes) {
        notificationService.updateNotification(receiveNotifications, classId);
        redirectAttributes.addFlashAttribute("successMessage",
                "You successfully update your Notification");
        return "redirect:/notification/" + classId + "/" + sectionId;

    }

}
