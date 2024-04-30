package edu.duke.ece651.team1.client.service;

import org.springframework.stereotype.Service;

import edu.duke.ece651.team1.client.model.GeoLocationUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.Duration;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Service for managing user activities and cookie validation within the application.
 * This service maintains a log of user activities to manage session validity and auto-cleanup based on activity timestamps.
 */
@Service
public class CookieService {
    private final ConcurrentHashMap<String, UserActivity> activityLog = new ConcurrentHashMap<>();

    
     /**
     * Validates the activity tied to a specific cookie. If the cookie is new or not recently used by a different user, it is considered valid.
     *
     * @param cookie the cookie identifier associated with the user's session
     * @param userId the user ID to validate against the activity log
     * @return true if the activity is valid, false if it's a recent activity by a different user
     */
    public boolean validateActivity(String cookie, int userId) {
        UserActivity activity = activityLog.get(cookie);
        if (activity == null) {
            activityLog.put(cookie, new UserActivity(userId, LocalDateTime.now()));
            return true;
        }
        if (activity.getUserId()!=userId
                && Duration.between(activity.getLastActivity(), LocalDateTime.now()).toMinutes() < 30) {
            return false;
        }
        activityLog.put(cookie, new UserActivity(userId, LocalDateTime.now()));
        return true;
    }
    
    /**
     * Scheduled method to clean up user activities that have not been updated in the last hour.
     * This helps to manage memory and ensure that only active sessions are kept in the log.
     */
    @Scheduled(fixedDelay = 3600000)
    public void cleanupActivities() {
        LocalDateTime now = LocalDateTime.now();
        activityLog.keySet()
                .removeIf(key -> Duration.between(activityLog.get(key).getLastActivity(), now).toHours() >= 1);
    }
    
    /**
     * Inner class to represent the user activity, including user ID and the last active time.
     */
    static class UserActivity {
        private int userId;
        private LocalDateTime lastActivity;

        public UserActivity(int userId, LocalDateTime lastActivity) {
            this.userId = userId;
            this.lastActivity = lastActivity;
        }

        public int getUserId() {
            return userId;
        }

        public LocalDateTime getLastActivity() {
            return lastActivity;
        }
    }
}
