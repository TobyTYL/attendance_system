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

@Service
public class CookieService {
    private final ConcurrentHashMap<String, UserActivity> activityLog = new ConcurrentHashMap<>();

    

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

    @Scheduled(fixedDelay = 3600000)
    public void cleanupActivities() {
        LocalDateTime now = LocalDateTime.now();
        activityLog.keySet()
                .removeIf(key -> Duration.between(activityLog.get(key).getLastActivity(), now).toHours() >= 1);
    }

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
