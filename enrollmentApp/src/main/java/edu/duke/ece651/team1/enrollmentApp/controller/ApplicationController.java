package edu.duke.ece651.team1.enrollmentApp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ApplicationController {
    private BufferedReader inputReader;
    private final PrintStream out;
    private MainMenuController MainMenuController;
    private CourseController courseController;
    //private EnrollmentController enrollmentController;

    public ApplicationController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.MainMenuController = new MainMenuController(inputReader, out); // Adjusted to pass ApplicationController itself
        this.courseController = new CourseController(inputReader, out);
        //this.enrollmentController = new EnrollmentController(inputReader, out);
    }

    public void startApplication() throws IOException {
        MainMenuController.startMainMenu();
    }

}
