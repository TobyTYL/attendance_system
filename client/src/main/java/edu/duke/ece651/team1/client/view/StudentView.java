package edu.duke.ece651.team1.client.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;

import edu.duke.ece651.team1.shared.Student;
import java.util.List;
/**
 * The StudentView class manages the presentation of student-related functionalities to the user.
 * It provides methods to display menus, prompts, messages, and handle user inputs related to student management.
 */
public class StudentView {
    public BufferedReader getInputReader() {
        return inputReader;
    }

    public PrintStream getOut() {
        return out;
    }

    private final BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a StudentView object with the specified input reader and output stream.
     * @param inputReader The BufferedReader to read user input from.
     * @param out The PrintStream to print output to.
     */
    public StudentView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    /**
     * Displays the student menu options to the user.
     */
    public void showStudentMenu() {
        out.println("Please select an option:");
        out.println("1. Manage Notification Preferences");
        out.println("2. View Attendance Reports");
        out.println("3. Go back");
    }
   
    public String readStudentOption() throws IOException {
        int optionNum;
        while (true) {
            try{
                optionNum  = ViewUtils.getUserOption(inputReader, out, 3);
                break;
            }catch(IllegalArgumentException e){
                out.println("Invalid Option: Please try agin");
            }
        } 
        if (optionNum == 1) {
            return "notification";
        } else if (optionNum == 2) {
            return "report";
        } else  {
            return "back";
        } 
    }

    public boolean displayNotificationAndPrompt(boolean notify,String className) throws IOException{
        out.println("You are currently managing notifications for: " + className + ".");
        String notiInfo = notify?"Enabled":"Disabled";
        String action = notify?"Disable":"Enable";
        out.println("Current notification setting: "+notify);
        out.println("Would you like to "+action+" notifications for this course? (yes/no):");
        String usrinput = ViewUtils.getUserInput(
            "Please type 'yes' or 'no': ",
            "Invalid input. Please type 'yes' or 'no': ",
            inputReader,
            out,
            s -> s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("no")
         ).toLowerCase();
        return usrinput.equals("yes")? true : false;
    }

    public void showReportMenue(String className) {
        out.println("You are viewing reports for: " + className + ".");
        out.println("Please choose the type of report:");
        out.println("1. Summary Report");
        out.println("2. Detailed Report");
        out.println("3. Go back");
    }

    public String readReportOpetion() throws IOException{
        int optionNum;
        while (true) {
            try{
                optionNum  = ViewUtils.getUserOption(inputReader, out, 3);
                break;
            }catch(IllegalArgumentException e){
                out.println("Invalid Option: Please try agin");
            }
        } 
        if(optionNum == 1){
            return "summary";
        }else if(optionNum == 2){
            return "detail";
        }else{
            return "back";
        }
    }
    


}
