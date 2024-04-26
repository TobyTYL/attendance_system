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

    public String createToken(double latitude, double longitude) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, new TokenInfo(LocalDateTime.now(),latitude,longitude));
        return token;
    }

    public boolean validateToken(String token) {
        TokenInfo info = tokenStore.get(token);
        if (info != null &&
                Duration.between(info.getTimestamp(), LocalDateTime.now()).toSeconds() < 30) {
            return true;
        }
        return false;
    }
    public boolean validateLocation(String token, double latitude, double longitude, double threshold) {
        TokenInfo info = tokenStore.get(token);
        if (info != null) {
            double storedLatitude = info.getLatitude();
            double storedLongitude = info.getLongitude();
            return GeoLocationUtil.areLocationsClose(latitude, longitude, storedLatitude, storedLongitude, threshold);
        }
        return false;
    }

    @Scheduled(fixedDelay = 60000)
    public void cleanupTokens() {
        tokenStore.entrySet().removeIf(
                entry -> Duration.between(entry.getValue().getTimestamp(), LocalDateTime.now()).toMinutes() >= 5);
    }

    static class TokenInfo {
        private LocalDateTime timestamp;
        private double latitude;
        private double longitude;

        public TokenInfo(LocalDateTime timestamp, double latitude, double longitude) {
            this.timestamp = timestamp;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        public double getLatitude() {
            return latitude;
        }
    
        public double getLongitude() {
            return longitude;
        }
    }
}
