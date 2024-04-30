package edu.duke.ece651.team1.enrollmentApp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;

import edu.duke.ece651.team1.enrollmentApp.view.MainMenuView;
import edu.duke.ece651.team1.enrollmentApp.view.*;
/**
* Controls the main navigation for the application, allowing users to access different parts of the system.
*/
public class MainMenuController {
   private BufferedReader inputReader;
   private PrintStream out;
   MainMenuView mainMenuView;
   CourseController courseController;
   EnrollmentController enrollmentController;
   /**
    * Constructs a MainMenuController with dependencies needed for operation.
    *
    * @param inputReader A BufferedReader for reading user input.
    * @param out A PrintStream for outputting text to the user.
    */
   public MainMenuController(BufferedReader inputReader, PrintStream out) {
       this.inputReader = inputReader;
       this.out = out;
       this.mainMenuView = new MainMenuView(inputReader, out);
       this.courseController = new CourseController(inputReader, out);
       this.enrollmentController = new EnrollmentController(inputReader, out);
   }
   /**
    * Starts the main menu loop which allows users to select options to manage courses or enrollments,
    * or to exit the application.
    *
    * @throws IOException If an input or output exception occurred.
    * @throws SQLException If a database access error occurs.
    */
   public void startMainMenu() throws IOException, SQLException {
       boolean exit = false;
       while (!exit) {
           mainMenuView.displayMenu();
           int choice = mainMenuView.getMenuChoice();
           switch (choice) {
               case 1:
                   courseController.startCourseManagement();
                   break;
               case 2:
                   enrollmentController.startEnrollment();
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
