package edu.duke.ece651.team1.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.userdetails.User;
import java.util.Collections;
import edu.duke.ece651.team1.server.repository.InMemoryUserRepository;
/*
 * This class provides services for user-related operations, such as creating user accounts.
 */
@Service
// Repository for managing user details
public class UserService {
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /*
     * Creates a new user account with the specified username and password.
     *
     * @param username  the username of the new user
     * @param password  the password of the new user
     */
    public void createUser(String username, String password) {
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        inMemoryUserRepository.createUser(user);
   }
}
