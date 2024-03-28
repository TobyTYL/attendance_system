package edu.duke.ece651.team1.server.model;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
/**
 * Provides a centralized notification service that manages a list of {@link Notification} observers.
 * This service allows for adding and deleting notification observers, which can be
 * any implementation of the {@link Notification} interface. It supports the broadcasting of messages
 * to all observers
 *
 */
public class NotificationService {
    /**
     * Stores the list of registered observers.
     */
    private final List<Notification> observers;
   /**
     * Constructs a new {@code NotificationService} instance. Initializes an empty list of observers.
     */
    public NotificationService() {
        this.observers = new ArrayList<>();
    }
    /**
     * add a {@link Notification} observer with the service.
     * 
     * @param notification The {@link Notification} instance to be added.
     */
    public void addNotification(Notification notification){
        observers.add(notification);
    }
    /**
     * delete a {@link Notification} observer from the service.
     * 
     * @param notification The {@link Notification} instance to be removed 
     */
    public void deleteNotification(Notification notification){
        observers.remove(notification);
    }
    /**
     * Broadcasts a message to all observers. Each observer's {@code notify} method is called,
     * passing the provided message and recipient.
     * 
     * @param message  The message to be sent.
     * @param recipient The recipient's identifier (e.g., email address, phone number) 
     */
    public void notifyObserver(String message, String recipent){
        for(Notification notification:observers){
            notification.notify(message, recipent);
        }
    }

    

}
