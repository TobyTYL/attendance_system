package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.PrintStream;


public class ApplicationController {
    BufferedReader inputReader;
    final PrintStream out;
    MainMenuController mainMenuController;
    StudentController studentController;

    /**
     * The ApplicationController class represents the main controller of the application.
     * It manages the flow of the application and initializes other controllers.
     */
    public ApplicationController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuController = new MainMenuController(inputReader, out);
        this.studentController = new StudentController(inputReader, out);
    }
    /**
     * Starts the application by displaying the main menu.
     */
    public void startApplication(){
        mainMenuController.startMainMenu();
    }
}
