 package edu.duke.ece651.team1.server.repository;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.userdetails.User;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.provisioning.InMemoryUserDetailsManager;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.provisioning.UserDetailsManager;
 import org.springframework.stereotype.Repository;
 import edu.duke.ece651.team1.server.service.*;
 import java.util.Collections;
 import java.util.Iterator;
 import java.util.List;
 import java.util.ArrayList;
 import java.io.*;
 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.JsonNode;
 import com.fasterxml.jackson.databind.ObjectMapper;

 import java.util.HashMap;
 import java.util.Map;

 import javax.annotation.PostConstruct;
 import javax.annotation.PreDestroy;
 import org.slf4j.LoggerFactory;
 import org.slf4j.Logger;
 /*
  * This class represents a repository for managing user details stored in memory.
  */
 @Repository
 public class InMemoryUserRepository extends InMemoryUserDetailsManager {
     @Autowired
     private PasswordEncoder passwordEncoder;
     @Value("${userDetails.path}")
     private String userDetailsPath;
     private Map<String, UserDetails> users = new HashMap<>();
     private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepository.class);
     /**
      * Initializes the repository by creating default admin user and loading users from file.
      */
     @PostConstruct
     public void init() {
         UserDetails admin = User.withUsername("admin")
                 .password(passwordEncoder.encode("admin"))
                 .roles("ADMIN")
                 .build();
         this.createUser(admin);
         logger.info("hi restore user begin");
         try {
             Map<String, UserDetails> initialUsers = loadUserDetailsFromFile();
             for (Map.Entry<String, UserDetails> entry : initialUsers.entrySet()) {
                 createUser(entry.getValue());
                 logger.info("hi restore user: "+entry.getKey());
                
             }
         } catch (IOException e) {
             System.err.println("initializing users from file Error because: " + e.getMessage());
         }

     }

     /**
      * Reads JSON data from a file and returns its root node.
      *
      * @param filePath The path of the JSON file to read.
      * @return The root node of the JSON data.
      * @throws IOException If an I/O error occurs while reading the file.
      */
     public JsonNode readJsonFromFile(String filePath) throws IOException {
         File file = new File(filePath);
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode rootNode = objectMapper.readTree(file);
         return rootNode;
     }
     /**
      * Loads user details from a JSON file and returns them as a map.
      *
      * @return A map of username to UserDetails.
      * @throws IOException If an I/O error occurs while reading the file.
      */
     public Map<String, UserDetails>  loadUserDetailsFromFile() throws IOException{
         JsonNode rootNode = readJsonFromFile(userDetailsPath);
         return parseUserJson(rootNode);
     }

     /**
      * Parses user details from a JSON node and returns them as a map.
      *
      * @param rootNode The root node of the JSON data containing user details.
      * @return A map of username to UserDetails.
      */
     public Map<String, UserDetails> parseUserJson(JsonNode rootNode)  {
         Map<String, UserDetails> previousUsers = new HashMap<>();
         Iterator<String> fieldNames = rootNode.fieldNames();
         while (fieldNames.hasNext()) {
             String username = fieldNames.next();
             JsonNode userDetail = rootNode.get(username);
             String encryptedPassword = userDetail.get("password").asText();
             JsonNode authorityNodes = userDetail.get("authorities");
             List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
             if (authorityNodes.isArray()) {
                 for (JsonNode authorityNode : authorityNodes) {
                     String authority = authorityNode.get("authority").asText();
                     grantedAuthorities.add(new SimpleGrantedAuthority(authority));
                 }
             }
             UserDetails user = User.withUsername(username)
                     .password(encryptedPassword)
                     .authorities(grantedAuthorities)
                     .build();
             previousUsers.put(username, user);
         }
         return previousUsers;
     }

     @Override
     public void createUser(UserDetails user) {
         if (userExists(user.getUsername())) {
             throw new IllegalStateException("User: " + user.getUsername() + " already exists");
         } else {
             super.createUser(user);
             GrantedAuthority firstAuthority = new ArrayList<>(user.getAuthorities()).get(0);
             if(firstAuthority.getAuthority().equals("ROLE_USER")){
                 addUserToMap(user.getUsername(), user);
             }
         }
     }
     /**
      * Adds a user to the internal user map.
      *
      * @param userName The username of the user to add.
      * @param user The UserDetails object representing the user.
      */
     public void addUserToMap(String userName, UserDetails user) {
         users.put(userName, user);
     }
     /**
      * Exports user details to a JSON file before destroying the bean.
      *
      * @throws IOException If an I/O error occurs while writing to the file.
      */
     @PreDestroy
     private void exportUserDetailsToFile() throws IOException {
         Map<String, UserDetails> users = getUsers();
         ObjectMapper objectMapper = new ObjectMapper();
         logger.info("user count:"+users.size());
         objectMapper.writeValue(new File(userDetailsPath), users);
     }
     /**
      * Retrieves the current user map.
      *
      * @return An unmodifiable map of username to UserDetails.
      */
     public Map<String, UserDetails> getUsers() {
         return Collections.unmodifiableMap(users);
     }
     /**
      * Retrieves the usernames of all users in the repository.
      *
      * @return A list of usernames.
      */
     public List<String> getUserNames(){
         return new ArrayList<>(users.keySet());
     }
 }
