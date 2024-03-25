package edu.duke.ece651.team1.server.model;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
// @Component
public class NotificationService {
    private final List<Notification> observers;
    // private final EmailNotification emailNotification;
    // @Autowired
    public NotificationService() {
        this.observers = new ArrayList<>();
    }

    public void addNotification(Notification notification){
        observers.add(notification);
    }
    public void deleteNotification(Notification notification){
        observers.remove(notification);
    }

    public void notifyObserver(String message, String recipent){
        for(Notification notification:observers){
            notification.notify(message, recipent);
        }
    }

    

}
