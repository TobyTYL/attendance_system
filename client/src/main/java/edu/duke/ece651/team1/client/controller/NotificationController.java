package edu.duke.ece651.team1.client.controller;

import edu.duke.ece651.team1.client.view.NotificarionView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.core.ParameterizedTypeReference;
import edu.duke.ece651.team1.client.model.UserSession;
import java.util.List;
import org.springframework.http.*;
import org.json.JSONObject;
/**
 * The NotificationController class manages the notification preferences for a student in a specific class.
 * It handles operations such as retrieving the current notification preference and updating it.
 * This controller interacts with the NotificarionView for user input and utilizes a RestTemplate
 * for HTTP requests to the backend service.
 */
public class NotificationController {
    BufferedReader inputReader;
    final PrintStream out;
    private NotificarionView view;
    int classID;
    String className;
    int studentId;
    private RestTemplate restTemplate;
    /**
     * Constructs a NotificationController with the specified input reader, output stream, class ID, class name, and student ID.
     *
     * @param inputReader The BufferedReader to read user input.
     * @param out         The PrintStream to output data to the user.
     * @param classID     The ID of the class.
     * @param className   The name of the class.
     * @param studentId   The ID of the student.
     */
    public NotificationController(BufferedReader inputReader, PrintStream out, int classID, String className,
            int studentId) {
        this.inputReader = inputReader;
        this.out = out;
        this.classID = classID;
        this.className = className;
        this.studentId = studentId;
        this.view = new NotificarionView(inputReader, out);
        this.restTemplate = new RestTemplate();
    }
    /**
     * Starts the notification menu for managing notification preferences.
     * It retrieves the current notification preference, displays it to the user, and prompts for changes.
     */
    public void startNotificationMenue() {
        try {
            // todo : getNotification
            boolean currentPreference = getReceiveNotification();
            view.displayNotification(currentPreference, className);
            boolean change = view.readNotificationOption();
            // todo : updateNotification
            if (change) {
                boolean nowPreference = !currentPreference;
                updateNotification(nowPreference);
            }
        } catch (Exception e) {
            out.println("Exception happened during Notification controller because " + e.getMessage());
        }
    }
    /**
     * Retrieves the current notification preference for the student in the class.
     *
     * @return The current notification preference (true for receiving notifications, false otherwise).
     */
    public boolean getReceiveNotification() {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/notification/" + studentId + "/" + classID;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        String responseBody = ControllerUtils.executeGetRequest(url, responseType);
        JSONObject jsonObject = new JSONObject(responseBody);
        boolean receiveNotifications = jsonObject.getBoolean("ReceiveNotifications");
        return receiveNotifications;
    }
    /**
     * Updates the notification preference for the student in the class.
     *
     * @param receiveNotifications The new notification preference (true for receiving notifications, false otherwise).
     */
    public void updateNotification(boolean receiveNotifications) {
        String url = String.format("http://%s:%s/api/students/notification/%d/%d/?preference=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, classID,receiveNotifications );
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        ControllerUtils.executePostPutRequest(url, null, responseType, true);
        view.showSuccessUpdateMessage(className, receiveNotifications);
    }

}
