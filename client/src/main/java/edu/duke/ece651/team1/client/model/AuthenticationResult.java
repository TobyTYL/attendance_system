package edu.duke.ece651.team1.client.model;

public class AuthenticationResult {
    private boolean successful;
    private String message;

    public AuthenticationResult(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
