package edu.duke.ece651.team1.enrollmentApp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import edu.duke.ece651.team1.enrollmentApp.view.MainMenuView;
import edu.duke.ece651.team1.enrollmentApp.view.*;

public class MainMenuController {
     private BufferedReader inputReader;
    private PrintStream out;
    private MainMenuView mainMenuView;
    private CourseController courseController;
    //private EnrollmentController enrollmentController;

    public MainMenuController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuView = new MainMenuView(inputReader, out);
        this.courseController = new CourseController(inputReader, out);
        //this.enrollmentController = new EnrollmentController(inputReader, out);
    }

    public void startMainMenu() throws IOException {
        boolean exit = false;
        while (!exit) {
            mainMenuView.displayMenu();
            int choice = mainMenuView.getMenuChoice();
            switch (choice) {
                case 1:
                    courseController.startCourseManagement();
                    break;
                case 2:
                    //enrollmentController.startEnrollment();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
