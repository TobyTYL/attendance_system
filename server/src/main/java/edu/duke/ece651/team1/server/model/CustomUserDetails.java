package edu.duke.ece651.team1.server.model;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import edu.duke.ece651.team1.shared.*;
/**
 * CustomUserDetails provides a custom implementation of the UserDetails interface for Spring Security.
 * This model class encapsulates all necessary user information required by Spring Security to perform
 * authentication and authorization.
 */
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private String role;
    private int userId;
    /**
     * Constructs a CustomUserDetails with specified username, password, role, and userId.
     * @param username
     * @param password
     * @param role
     * @param userId
     */
    public CustomUserDetails(String username, String password, String role, int userId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }
    /**
     * Constructs a CustomUserDetails from a User object.
     * 
     * @param user the User object containing user details
     */
    public CustomUserDetails(User user){
        this(user.getUsername(), user.getPasswordHash(), user.getRole(), user.getUserId());
    }
    /**
     * Returns the authorities granted to the user. 
     * Simplified authority management by encapsulating the user's role into a single authority.
     * 
     * @return a collection of GrantedAuthority containing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Simple authority wrapping the role
        return Collections.singletonList(() -> "ROLE_" + this.role);
    }
    /**
     * Returns the password used to authenticate the user.
     * 
     * @return a String representing the user's password
     */
    @Override
    public String getPassword() {
        return this.password;
    }
    /**
     * Returns the username used to authenticate the user. It's unique.
     * 
     * @return a String representing the user's username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    // You might not need these methods initially
    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     * 
     * @return true if the user's account is valid (non-expired), false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
     /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     * 
     * @return true if the user is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
     /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     * 
     * @return true if the user's credentials are valid (non-expired), false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     * 
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    /**
     * Gets the unique identifier for this user.
     * 
     * @return an integer representing the unique identifier of the user
     */
    // Custom getters
    public int getUserId() {
        return userId;
    }
     /**
     * Gets the role of this user.
     * 
     * @return a String representing the role of the user
     */
    public String getRole() {
        return role;
    }
}
