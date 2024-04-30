package edu.duke.ece651.team1.client.model;
/**
 * Represents the result of an authentication attempt, encapsulating the success status and an accompanying message.
 */
public class AuthenticationResult {
    private boolean successful;
    private String message;
    /**
     * Constructs an AuthenticationResult with a success flag and a message.
     * 
     * @param successful a boolean indicating whether the authentication was successful
     * @param message a String providing additional details about the result of the authentication attempt
     */
    public AuthenticationResult(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }
    /**
     * Returns the success status of the authentication.
     * 
     * @return true if the authentication was successful, false otherwise
     */
    public boolean isSuccessful() {
        return successful;
    }
     /**
     * Returns the message associated with the authentication result.
     * 
     * @return a String containing additional information or details about the authentication result
     */
    public String getMessage() {
        return message;
    }
}
