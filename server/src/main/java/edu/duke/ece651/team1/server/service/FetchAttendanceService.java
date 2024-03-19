package edu.duke.ece651.team1.shared;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import org.json.JSONObject;


import edu.duke.ece651.team1.shared.AttendanceRecord;

public class FetchAttendanceService{

    public void displayJsonFile(String filePath){
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
            String content = contentBuilder.toString();
            
            JSONObject jsonObject = new JSONObject(content);
            System.out.println(jsonObject.toString(4)); // Print with indentation for readability
        } catch (IOException e) {
          //e.printStackTrace();
        }
        
    }
    public void promptDateAndDisplayAttendance(BufferedReader in, PrintStream out) {
        out.println("Enter the date (YYYY-MM-DD):");
        try {
            String dateInput = in.readLine();
            String filePath = "server/src/data/Attendance-" + dateInput + ".json";
            displayJsonFile(filePath);
        } catch (IOException e) {
            out.println("Error reading input: " + e.getMessage());
        }
    }
    
}
    
    /**
     * 
     * @param record
     */
    //public modifyAttendance(AttendanceRecord record){

    //}

