package edu.duke.ece651.team1.client.service;
import edu.duke.ece651.team1.client.model.*;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;





@Service
public class UserService {
    RestTemplate restTemplate = new RestTemplate();

    public String authenticate(String username, String passwrod)  {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() + "/api/login";
        try {
            ResponseEntity<String> response = sendPostRequestWithFormData(getRequestBody(username, passwrod), url);
            UserSession.getInstance().setUsername(username);
            UserSession.getInstance().setSessionToken(response.getHeaders().getFirst("Set-Cookie"));
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Caught HttpClientErrorException: " + e.getClass() + " with status " + e.getStatusCode());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return "loginFailed";
            }
            throw new RuntimeException("Unexpected error during login: " + e.getMessage(), e);
        } catch (RestClientException e) {
            throw new RuntimeException("Unexpected error during login: " + e.getMessage());
        }
    }

    public ResponseEntity<String> sendPostRequestWithFormData(MultiValueMap<String, String> formData, String apiUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
    }

    private MultiValueMap<String, String> getRequestBody(String username, String passwrod) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", passwrod);
        return requestBody;
    }
}
