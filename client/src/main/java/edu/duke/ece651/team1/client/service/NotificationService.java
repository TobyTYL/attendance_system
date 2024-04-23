package edu.duke.ece651.team1.client.service;
import edu.duke.ece651.team1.client.model.*;
import org.springframework.stereotype.Service;
import org.springframework.core.ParameterizedTypeReference;
import org.json.*;

@Service
public class NotificationService {
      /**
     * Retrieves the current notification preference for the student in the class.
     *
     * @return The current notification preference (true for receiving notifications, false otherwise).
     */
    public boolean getReceiveNotification(int classID) {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/students/notification/" + UserSession.getInstance().getUid() + "/" + classID;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        String responseBody = ApiService.executeGetRequest(url, responseType);
        JSONObject jsonObject = new JSONObject(responseBody);
        boolean receiveNotifications = jsonObject.getBoolean("ReceiveNotifications");
        return receiveNotifications;
    }
    /**
     * Updates the notification preference for the student in the class.
     *
     * @param receiveNotifications The new notification preference (true for receiving notifications, false otherwise).
     */
    public void updateNotification(boolean receiveNotifications, int classID) {
        String url = String.format("http://%s:%s/api/students/notification/%d/%d/?preference=%s",
        UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), UserSession.getInstance().getUid(), classID,receiveNotifications );
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        ApiService.executePostPutRequest(url, null, responseType, true);
    }
}
