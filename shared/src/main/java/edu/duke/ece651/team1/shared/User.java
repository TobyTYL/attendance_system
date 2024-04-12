package edu.duke.ece651.team1.shared;
/**
 * Represents a user with a unique user id, username, password hash, email, and role.
 */
public class User {
    private Integer userId;
    private String username;
    private String passwordHash;
    private String email;
    private String role;

    public User() {
    }
    /**
     * Constructs a User with specified user id, username, password hash, and role.
     * @param userId
     * @param username
     * @param passwordHash
     * @param role
     */
    public User(Integer userId, String username, String passwordHash, String role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    public User(String username, String passwordHash, String role){
        this(null,username,passwordHash,role);
    }
    public User(Integer userId, String username, String passwordHash){
        this(userId,username,passwordHash,null);
    }
    // Getters
    public int getUserId() {
        return userId;
    }
    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }
    // Getters
    public String getUsername() {
        return username;
    }
    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
    // Getters
    public String getPasswordHash() {
        return passwordHash;
    }
    // Setters
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    // Getters
    public String getEmail() {
        return email;
    }
    // Setters
    public void setEmail(String email) {
        this.email = email;
    }
    // Getters
    public String getRole() {
        return role;
    }
    // Setters
    public void setRole(String role) {
        this.role = role;
    }
}
