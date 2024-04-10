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

public class NotificationController {
    BufferedReader inputReader;
    final PrintStream out;
    private NotificarionView view;
    int classID;
    String className;
    int studentId;
    private RestTemplate restTemplate;

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

    public void updateNotification(boolean receiveNotifications) {
        String url = String.format("http://%s:%s/api/students/notification/%d/%d/?preference=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, classID,receiveNotifications );
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        ControllerUtils.executePostPutRequest(url, null, responseType, true);
        view.showSuccessUpdateMessage(className, receiveNotifications);
    }

}
