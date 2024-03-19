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

@Service
public class UserService {
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${userDetails.path}")
    private String userDetailsPath;
    private Map<String, UserDetails> users = new HashMap<>();

    public void createUser(String userName, String password) {
        UserDetails user = User.withUsername(userName)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        inMemoryUserRepository.createUser(user);
        addUserToMap(userName, user);
    }

    public void addUserToMap(String userName, UserDetails user){
        users.put(userName, user);
    }

    @PreDestroy
    private void exportUserDetailsToFile() throws IOException{
        Map<String, UserDetails> users =getUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(userDetailsPath), users);
    }

    public Map<String, UserDetails> getUsers() {
        return Collections.unmodifiableMap(users);
    }
   




}
