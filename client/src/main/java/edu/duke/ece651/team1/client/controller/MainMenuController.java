package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import edu.duke.ece651.team1.client.view.MainMenuView;

public class MainMenuController {
    BufferedReader inputReader;
    final PrintStream out;
    MainMenuView mainMenuView;
    AttendanceController attendanceController;
    StudentController studentController;
    public MainMenuController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuView = new MainMenuView(inputReader, out);
        this.attendanceController = new AttendanceController(inputReader, out);
        this.studentController = new StudentController(inputReader, out);
    }

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
                    break;
                }
            }catch(Exception e){
                out.println("Main Menu error because "+e.getMessage());
            }
           
    
        }
    }
    
}
