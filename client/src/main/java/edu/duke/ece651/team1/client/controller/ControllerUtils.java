package edu.duke.ece651.team1.client.controller;
import org.springframework.http.*;
import edu.duke.ece651.team1.client.model.UserSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
public class ControllerUtils {
    private static RestTemplate restTemplate = new RestTemplate();
    public static HttpHeaders getSessionTokenHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", UserSession.getInstance().getSessionToken());
        return headers;
    }
    public static <T> T executeGetRequest(String url, ParameterizedTypeReference<T> responseType) {
        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
            return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Resource not found: " + ex.getMessage());
        } catch (HttpServerErrorException ex) {
            throw new RuntimeException("Server error occurred: " + ex.getMessage());
        }
    }

    public static void executePostPutRequest(String url, Object requestBody, ParameterizedTypeReference<?> responseType,boolean post) {
        HttpEntity<Object> requestEntity = (requestBody!=null)?new HttpEntity<>(requestBody, getSessionTokenHeaders()):new HttpEntity<>(getSessionTokenHeaders());
        try {
            HttpMethod method = post?HttpMethod.POST:HttpMethod.PUT;
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);    
        } catch (HttpServerErrorException ex) {
            throw new RuntimeException("Server error occurred: " + ex.getMessage());
        }
    }
    
}
