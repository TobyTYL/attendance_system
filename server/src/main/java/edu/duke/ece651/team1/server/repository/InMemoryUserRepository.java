package edu.duke.ece651.team1.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import edu.duke.ece651.team1.server.service.*;
import java.io.*;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository
public class InMemoryUserRepository extends InMemoryUserDetailsManager {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${userDetails.path}")
    private String userDetailsPath;

    @PostConstruct
    public void init()  {
        
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        this.createUser(admin);
        try{
            Map<String, User> initialUsers = loadUserDetailsFromFile();
            for(Map.Entry<String,User> entry:initialUsers.entrySet()){
                createUser(entry.getValue());
            }
        }catch(IOException e){
            System.err.println("initializing users from file Error because: " + e.getMessage());
        }
       
    }

    public Map<String, User> loadUserDetailsFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, User> users = objectMapper.readValue(new File(userDetailsPath),
                new TypeReference<HashMap<String, User>>() {
                });
        return users;
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new IllegalStateException("User[" + user.getUsername() + "] already exists");
        } else {
            super.createUser(user);
        }
    }
}
