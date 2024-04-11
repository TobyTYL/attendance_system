package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import edu.duke.ece651.team1.user_admin_app.view.MainMenuView;

public class MainMenuController {
    BufferedReader inputReader;
    final PrintStream out;
    MainMenuView mainMenuView;
    StudentController studentController;
    ProfessorController professorController;
    /**
     * The MainMenuController class manages the main menu of the application.
     * It handles user input and directs the flow of the application based on the selected option.
     */
    public MainMenuController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuView = new MainMenuView(inputReader, out);
        this.professorController = new ProfessorController(inputReader, out);
        this.studentController = new StudentController(inputReader, out);
    }
    /**
     * Starts the main menu, allowing users to navigate through different options.
     * Displays the main menu view and handles user input to direct the flow of the application.
     */
    public void startMainMenu() {
        while (true) {
            try{
                // Display the main menu view and read the user's choice
                mainMenuView.showMainMenu();
                String option = mainMenuView.readMainOption();
                // Direct the flow of the application based on the selected option
                if (option.equals("professors")) {
                    professorController.startProfessorMenu();
                }
                else if (option.equals("students")) {
//                    attendanceController.startStudentManager();
                    studentController.startStudentMenu();
                }
                else {
                    out.println("GoodBye!");
                    break;
                }
            } catch (Exception e) {
                // Handle any IOException that may occur during menu interaction
                out.println("Main Menu error because "+e.getMessage());
            }
        }
    }

}