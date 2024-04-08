package edu.duke.ece651.team1.client.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;
public class NotificarionView {
    private final BufferedReader inputReader;
    private final PrintStream out;
    public NotificarionView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void displayNotification(boolean notify,String className) {
        out.println("You are currently managing notifications for: " + className + ".");
        String notiInfo = notify?"Enabled":"Disabled";
        String action = notify?"Disable":"Enable";
        out.println("Current notification setting: "+notify);
        out.println("Would you like to "+action+" notifications for this course? (yes/no):");
        
    }
    
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

    public void showSuccessUpdateMessage(String className, boolean receiveNotifications){
        String notiInfo = receiveNotifications?"Enabled":"Disabled";
        out.println("Congrat! You successfully update your "+className+"'s Notification preference to "+notiInfo);
    }
}
