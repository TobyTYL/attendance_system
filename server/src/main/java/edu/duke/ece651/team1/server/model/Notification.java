package edu.duke.ece651.team1.server.model;
/**
 * The {@code edu.duke.ece651.team1.server.model} package handles data model on the server.
 *
 * <h2>Notification Interface</h2>
 * The {@code Notification} interface allows sending messages to specific student.
 */
public interface Notification {
    public void notify(String message,String recipient);
}

