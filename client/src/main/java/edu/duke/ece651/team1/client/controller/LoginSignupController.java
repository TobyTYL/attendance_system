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
/**
 * The LoginSignupController class is responsible for managing the login and signup processes.
 * It interacts with the LoginSignUpView to prompt the user for input and uses a RestTemplate to
 * communicate with the backend server for authentication and registration.
 */
public class LoginSignupController {
    BufferedReader inputReader;
    final PrintStream out;
    protected LoginSignUpView loginSignUpView;
    RestTemplate restTemplate;
    
    /**
     * Constructor that initializes the controller with input reader and output stream.
     * @param inputReader The BufferedReader for reading user input.
     * @param out The PrintStream for outputting text to the console.
     */
    public LoginSignupController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.loginSignUpView = new LoginSignUpView(inputReader, out);
        this.restTemplate = new RestTemplate();

    }
     /**
     * Main method to start the authentication or registration process based on user choice.
     * Prompts the user for login or signup and proceeds with the selected option.
     * @return boolean indicating if the user was successfully authenticated or registered.
     */
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
    /**
     * Constructs the request body for POST requests during login and signup processes.
     * @param username The user's username.
     * @param password The user's password.
     * @return A MultiValueMap containing the username and password.
     */
    private MultiValueMap<String, String> getRequestBody(String username, String passwrod) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", passwrod);
        return requestBody;
    }
    /**
     * Handles the login process by sending a POST request to the login API endpoint.
     * @param username The user's username.
     * @param password The user's password.
     * @return boolean indicating if the login was successful.
     * @throws IOException If an I/O error occurs during the process.
     */
    protected boolean handleLogin(String username, String passwrod) throws IOException {
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
    /**
     * Attempts to sign up the user and then log them in upon successful registration.
     * @param username The desired username for the new account.
     * @param password The password for the new account.
     * @return boolean indicating if the signup and subsequent login were successful.
     * @throws IOException If an I/O error occurs during the process.
     */
    protected boolean handleSignUpLogin(String username, String passwrod) throws IOException{
        if(handleSignUp(username, passwrod)){
            return handleLogin(username, passwrod);
        }
        return false;
        
    }
     /**
     * Handles the signup process by sending a POST request to the signup API endpoint.
     * @param username The desired username for the new account.
     * @param password The password for the new account.
     * @return boolean indicating if the signup was successful.
     * @throws IOException If an I/O error occurs during the process.
     */
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
     /**
     * Sends a POST request with form data to the specified API URL.
     * @param formData The MultiValueMap containing the form data to be sent.
     * @param apiUrl The URL of the API endpoint.
     * @return ResponseEntity<String> containing the response from the server.
     */
    public ResponseEntity<String> sendPostRequestWithFormData(MultiValueMap<String, String> formData, String apiUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
    }

}
