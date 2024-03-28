package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import edu.duke.ece651.team1.client.view.MainMenuView;
/**
 * The MainMenuController class manages the main menu of the application.
 * It directs the user to different sections of the application based on their choice.
 */
public class MainMenuController {
    BufferedReader inputReader;
    final PrintStream out;
    MainMenuView mainMenuView;
    AttendanceController attendanceController;
    StudentController studentController;
    /**
     * Constructor initializing the controller with input/output facilities and controllers for other parts of the application.
     * @param inputReader A BufferedReader for user input
     * @param out A PrintStream for output
     */
    public MainMenuController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuView = new MainMenuView(inputReader, out);
        this.attendanceController = new AttendanceController(inputReader, out);
        this.studentController = new StudentController(inputReader, out);
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
                else if (option.equals("students")) {
//                    attendanceController.startStudentManager();
                    studentController.startStudentMenu();
                }
                else{
                    out.println("GoodBye!");
                    break;
                }
            }catch(Exception e){
                out.println("Main Menu error because "+e.getMessage());
            }
           
    
        }
    }
    
}
