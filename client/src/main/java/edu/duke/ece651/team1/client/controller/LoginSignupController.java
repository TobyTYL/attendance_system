package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.PublicKey;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.LoginSignUpView;

public class LoginSignupController {
    BufferedReader inputReader;
    final PrintStream out;
    private LoginSignUpView loginSignUpView;
    RestTemplate restTemplate;

    public LoginSignupController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.loginSignUpView = new LoginSignUpView(inputReader, out);
        this.restTemplate = new RestTemplate();

    }

    public boolean authenticateOrRegister() {
        try {
            loginSignUpView.showLoginOrRegistrationMenu();
            String option = loginSignUpView.readLoginOrSignUp();
            if (option.equals("login")) {
                Map<String, String> userdata = loginSignUpView.promptForLogin();
                return handleLogin(userdata.get("username"), userdata.get("password"));
            } else {
                Map<String, String> userdata = loginSignUpView.promptsignUp();
                return handleSignUpLogin(userdata.get("username"), userdata.get("password"));
            }
        
        }catch(Exception e){
            out.println("Exception happen in login singup page " + e.getMessage());
        }
        return false;

    }

    private MultiValueMap<String, String> getRequestBody(String username, String passwrod) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", passwrod);
        return requestBody;
    }

    private boolean handleLogin(String username, String passwrod) throws IOException {
        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() + "/api/login";
        try {
            ResponseEntity<String> response = sendPostRequestWithFormData(getRequestBody(username, passwrod), url);
            loginSignUpView.showLoginSuccessMessage();
            UserSession.getInstance().setUsername(username);
            UserSession.getInstance().setSessionToken(response.getHeaders().getFirst("Set-Cookie"));
            return true;
        } catch (HttpClientErrorException.Unauthorized e) {
            loginSignUpView.showLoginFailureMessage();
            return false;
        } catch (RestClientException e) {
            throw new RuntimeException("Unexpected error during login: " + e.getMessage());
        }
    }

    private boolean handleSignUpLogin(String username, String passwrod) throws IOException{
        if(handleSignUp(username, passwrod)){
            return handleLogin(username, passwrod);
        }
        return false;
        
    }

    private boolean handleSignUp(String username, String passwrod) throws IOException {
        String url ="http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort() +"/api/signup";
        try {
            ResponseEntity<String> response = sendPostRequestWithFormData(getRequestBody(username, passwrod), url);
            loginSignUpView.showRegistrationSuccessMessage();
            return true;
        } catch (HttpClientErrorException.BadRequest e) {
            loginSignUpView.showRegistrationFailureMessage(e.getMessage());
            return false;
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

}
