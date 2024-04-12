package edu.duke.ece651.team1.client.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;
/**
 * The StudentReportView class manages the presentation of student attendance reports to the user.
 * It provides methods to display report menus, prompts, and messages, and handle user inputs related to report viewing.
 */
public class StudentReportView {
    private final BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a StudentReportView object with the specified input reader and output stream.
     *
     * @param inputReader The BufferedReader to read user input from.
     * @param out         The PrintStream to print output to.
     */
    public StudentReportView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
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
    /**
     * Displays the attendance report for the specified class.
     *
     * @param report    The attendance report to display.
     * @param className The name of the class for which the report is being viewed.
     */
    public void showReport(String report, String className){
        out.printf("=============== Attendance Report for class: %s ===============%n", className);
        out.println(report);
        out.println("=============================================================================");
    }


}
