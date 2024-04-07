package edu.duke.ece651.team1.client.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;
public class StudentReportView {
    private final BufferedReader inputReader;
    private final PrintStream out;
    public StudentReportView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
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

    public void showReport(String report, String className){
        out.printf("=============== Attendance Report for class: %s ===============%n", className);
        out.println(report);
        out.println("=============================================================================");
    }


}
