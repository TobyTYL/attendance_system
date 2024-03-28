package edu.duke.ece651.team1.client.model;
/**
 * Singleton class for maintaining a user session.
 * This class stores user-specific details such as username, session token, host, and port.
 */
public class UserSession {
    private static UserSession instance = new UserSession();
    private String username;
    private String sessionToken;
    private String host;
    private String port;
    private UserSession() {
    }
    /**
     * Retrieves the single instance of UserSession.
     * 
     * @return The single, static instance of the UserSession.
     */
    public static UserSession getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

   
}
