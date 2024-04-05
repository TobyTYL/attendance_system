package edu.duke.ece651.team1.server.model;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import edu.duke.ece651.team1.shared.*;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private String role;
    private long userId;

    public CustomUserDetails(String username, String password, String role, long userId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    public CustomUserDetails(User user){
        this(user.getUsername(), user.getPasswordHash(), user.getRole(), user.getUserId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Simple authority wrapping the role
        return Collections.singletonList(() -> "ROLE_" + this.role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // You might not need these methods initially
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Custom getters
    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
