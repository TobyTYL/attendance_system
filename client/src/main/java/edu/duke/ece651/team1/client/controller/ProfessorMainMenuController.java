package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import edu.duke.ece651.team1.client.view.MainMenuView;
/**
 * The MainMenuController class manages the main menu of the application.
 * It directs the user to different sections of the application based on their choice.
 */
public class ProfessorMainMenuController {
    private final int sectionId;
    private final int classId;
    private final String className;
    BufferedReader inputReader;
    final PrintStream out;
    MainMenuView mainMenuView;
    AttendanceController attendanceController;

    /**
     * Constructor initializing the controller with input/output facilities and controllers for other parts of the application.
     * @param inputReader A BufferedReader for user input
     * @param out A PrintStream for output
     */
    public ProfessorMainMenuController(int sectionId,int classId,String className,BufferedReader inputReader, PrintStream out) {
        this.sectionId = sectionId;
        this.classId = classId;
        this.className = className;
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuView = new MainMenuView(inputReader, out);
        this.attendanceController = new AttendanceController(inputReader, out, sectionId);
    }
    /**
     * Displays the main menu and handles user navigation to different functionalities of the application.
     */
    public void startMainMenu() {
        while (true) {
            try{
                mainMenuView.showMainMenu();
                String option =mainMenuView.readMainOption();
                if(option.equals("attendance")){
                  attendanceController.startAttendanceMenue();
                    
                }
                else{
                    return;
                    
                }
            }catch(Exception e){
                out.println("Main Menu error because "+e.getMessage());
            }
           
    
        }
    }
    
}
