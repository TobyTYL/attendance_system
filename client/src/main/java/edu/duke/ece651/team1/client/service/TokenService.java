package edu.duke.ece651.team1.client.service;

import org.springframework.stereotype.Service;

import edu.duke.ece651.team1.client.model.GeoLocationUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.Duration;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class TokenService {
    private final ConcurrentHashMap<String, TokenInfo> tokenStore = new ConcurrentHashMap<>();

    public String createToken() {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, new TokenInfo(LocalDateTime.now()));
        return token;
    }
    public int getTokenStoreSize(){
        return tokenStore.size();
    }

    public boolean validateToken(String token) {
        TokenInfo info = tokenStore.get(token);
        if (info != null &&
                Duration.between(info.getTimestamp(), LocalDateTime.now()).toSeconds() < 30) {
            return true;
        }
        return false;
    }
  

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
