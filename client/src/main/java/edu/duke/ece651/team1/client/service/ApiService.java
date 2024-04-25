package edu.duke.ece651.team1.client.service;
import org.springframework.http.*;
import edu.duke.ece651.team1.client.model.UserSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.*;

public class ApiService {
    static RestTemplate restTemplate = new RestTemplate();
    /**
    * Retrieves HTTP headers with the session token for the current user session.
    *
    * @return The HttpHeaders object containing the session token.
    */
   public static HttpHeaders getSessionTokenHeaders() {
       HttpHeaders headers = new HttpHeaders();
       headers.add("Cookie", UserSession.getInstance().getSessionToken());
       return headers;
   }
   /**
    * Executes a GET request to the specified URL with the provided response type.
    *
    * @param url          The URL to send the GET request to.
    * @param responseType The parameterized type reference for the response body.
    * @return The response body of the HTTP GET request.
    * @throws IllegalArgumentException If the requested resource is not found.
    * @throws RuntimeException         If a server error occurs.
    */
   public static <T> T executeGetRequest(String url, ParameterizedTypeReference<T> responseType) {
       try {
           HttpEntity<Object> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
           ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
           return responseEntity.getBody();
       } catch (HttpClientErrorException ex) {
           throw new IllegalArgumentException("Resource not found: " + ex.getMessage());
       } catch (HttpServerErrorException ex) {
           throw new RuntimeException("Server error occurred: " + ex.getMessage());
       }
   }
    /**
    * Executes a POST or PUT request to the specified URL with the provided request body and response type.
    *
    * @param url          The URL to send the POST or PUT request to.
    * @param requestBody  The request body object to be sent in the request.
    * @param responseType The parameterized type reference for the response body.
    * @param post         A boolean value indicating whether the request is a POST or PUT request.
    * @throws RuntimeException If a server error occurs.
    */
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
