package edu.duke.ece651.team1.client.controller;
import org.springframework.http.*;
import edu.duke.ece651.team1.client.model.UserSession;
public class ControllerUtils {
    public static HttpHeaders getSessionTokenHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        return headers;
    }
}
