package edu.duke.ece651.team1.client.service;

import org.springframework.stereotype.Service;

import edu.duke.ece651.team1.client.model.GeoLocationUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.Duration;
import org.springframework.scheduling.annotation.Scheduled;
/**
 * Service for managing security tokens within the application.
 * Provides functionalities to create, validate, and automatically clean up expired tokens.
 */
@Service
public class TokenService {
    private final ConcurrentHashMap<String, TokenInfo> tokenStore = new ConcurrentHashMap<>();
    /**
     * Generates a new unique security token and stores it along with its creation timestamp.
     *
     * @return A unique security token as a String.
     */
    public String createToken() {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, new TokenInfo(LocalDateTime.now()));
        return token;
    }
     /**
     * Retrieves the current size of the token store.
     *
     * @return The number of tokens currently stored.
     */
    public int getTokenStoreSize(){
        return tokenStore.size();
    }
    
    /**
     * Validates a token by checking if it exists in the token store and if it has not expired.
     * A token is considered valid if it is less than 30 seconds old.
     *
     * @param token The token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        TokenInfo info = tokenStore.get(token);
        if (info != null &&
                Duration.between(info.getTimestamp(), LocalDateTime.now()).toSeconds() < 30) {
            return true;
        }
        return false;
    }
  
    /**
     * Scheduled task to clean up expired tokens. Runs every minute.
     * Tokens are removed if they are no longer valid.
     */
    @Scheduled(fixedDelay = 60000)
    public void cleanupTokens() {
        tokenStore.keySet().removeIf(token -> !validateToken(token));
    }

    static class TokenInfo {
        private LocalDateTime timestamp;
        
   

        public TokenInfo(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
       
    }
}
