package edu.duke.ece651.team1.server.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;;
public class InMemoryUserRepositoryTest {
    InMemoryUserRepository userRepository = new InMemoryUserRepository();
    
     PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;
    @Test 
    public void testParseUserJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        
        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("password", "$2a$10$encryptedPassword");
        ArrayNode authoritiesNode = objectMapper.createArrayNode();
        ObjectNode authorityNode = objectMapper.createObjectNode();
        authorityNode.put("authority", "ROLE_USER");
        authoritiesNode.add(authorityNode);
        userNode.set("authorities", authoritiesNode);
        rootNode.set("user1", userNode);

        // Call the method under test
   
        Map<String, UserDetails> result = userRepository.parseUserJson(rootNode);

        // Assertions
        assertNotNull(result, "Resulting map should not be null.");
        assertEquals(1, result.size(), "Map should contain exactly one entry.");
        
        UserDetails userDetails = result.get("user1");
        assertNotNull(userDetails, "UserDetails for 'user1' should not be null.");
        assertEquals("$2a$10$encryptedPassword", userDetails.getPassword(), "Passwords should match.");
        
        List<GrantedAuthority> authorityList = new ArrayList<>(userDetails.getAuthorities());
        assertNotNull(authorityList, "Authorities should not be null.");
        assertFalse(authorityList.isEmpty(), "Authorities list should not be empty.");
        assertEquals("ROLE_USER", authorityList.get(0).getAuthority(), "Authority should be 'ROLE_USER'.");
    }
  @Disabled
  @Test
    public void testCreateAndParseUsers(){
        UserDetails user1 = User.withUsername("user1")
                                .password(passwordEncoder.encode("password1"))
                                .roles("USER")
                                .build();
        UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder.encode("admin"))
                                .roles("ADMIN")
                                .build();
        Map<String,UserDetails> map = new HashMap<>();
        userRepository.createUser(user1);
        userRepository.createUser(admin);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(map);
        Map<String, UserDetails> result = userRepository.parseUserJson(jsonNode);
        assertNotNull(result, "Resulting map should not be null.");
        assertEquals(2, result.size(), "Map should contain exactly two entry.");
        
        UserDetails userDetails = result.get("user1");
        assertNotNull(userDetails, "UserDetails for 'user1' should not be null.");
        assertEquals(passwordEncoder.encode("password1"), userDetails.getPassword(), "Passwords should match.");
        
        List<GrantedAuthority> authorityList = new ArrayList<>(userDetails.getAuthorities());
        assertNotNull(authorityList, "Authorities should not be null.");
        assertFalse(authorityList.isEmpty(), "Authorities list should not be empty.");
        assertEquals("ROLE_USER", authorityList.get(0).getAuthority(), "Authority should be 'ROLE_USER'.");




    }
}
