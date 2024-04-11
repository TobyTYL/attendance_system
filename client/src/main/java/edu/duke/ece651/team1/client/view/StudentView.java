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
   /**
     * Reads the user's choice from the student menu.
     *
     * @return A String representing the user's choice: "notification", "report", or "back".
     * @throws IOException If an I/O error occurs while reading user input.
     */
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

  
   /**
     * Displays the menu for selecting the type of attendance report to view.
     *
     * @param className The name of the class for which the report is being viewed.
     */
    public void showReportMenue(String className) {
        out.println("You are viewing reports for: " + className + ".");
        out.println("Please choose the type of report:");
        out.println("1. Summary Report");
        out.println("2. Detailed Report");
        out.println("3. Go back");
    }
    /**
     * Reads the user's choice for the type of attendance report to view.
     *
     * @return A String representing the user's choice: "summary", "detail", or "back".
     * @throws IOException If an I/O error occurs while reading user input.
     */
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
