package edu.duke.ece651.team1.client.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;
/**
 * The NotificationView class provides methods for displaying notification-related information to the user.
 */
public class NotificarionView {
    private final BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a NotificationView object with the given input reader and output stream.
     *
     * @param inputReader The BufferedReader to read user input.
     * @param out         The PrintStream to display output to the user.
     */
    public NotificarionView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays information about the current notification settings for a course.
     *
     * @param notify    A boolean indicating whether notifications are currently enabled for the course.
     * @param className The name of the course.
     */
    public void displayNotification(boolean notify,String className) {
        out.println("You are currently managing notifications for: " + className + ".");
        String notiInfo = notify?"Enabled":"Disabled";
        String action = notify?"Disable":"Enable";
        out.println("Current notification setting: "+notify);
        out.println("Would you like to "+action+" notifications for this course? (yes/no):");
        
    }
    /**
     * Reads the user's choice for updating notification preferences.
     *
     * @return A boolean indicating whether the user wants to enable notifications (true) or disable them (false).
     * @throws IOException If an I/O error occurs while reading user input.
     */
    public boolean readNotificationOption() throws IOException{
        String usrinput = ViewUtils.getUserInput(
            "Please type 'yes' or 'no': ",
            "Invalid input. Please type 'yes' or 'no': ",
            inputReader,
            out,
            s -> s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("no")
         ).toLowerCase();
        return usrinput.equals("yes")? true : false;
    }
     /**
     * Displays a success message after updating notification preferences for a course.
     *
     * @param className             The name of the course.
     * @param receiveNotifications A boolean indicating whether notifications are now enabled (true) or disabled (false).
     */
    public void showSuccessUpdateMessage(String className, boolean receiveNotifications){
        String notiInfo = receiveNotifications?"Enabled":"Disabled";
        out.println("Congrat! You successfully update your "+className+"'s Notification preference to "+notiInfo);
    }
}
