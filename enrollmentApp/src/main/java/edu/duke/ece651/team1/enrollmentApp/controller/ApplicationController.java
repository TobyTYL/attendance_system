package edu.duke.ece651.team1.enrollmentApp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;

//import edu.duke.ece651.team1.client.controller.CourseController;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDao;
/**
* The ApplicationController is the central controller of the application, orchestrating the interaction between the user interface and data management.
*/
public class ApplicationController {
   private BufferedReader inputReader;
   private final PrintStream out;
   protected MainMenuController MainMenuController;
   protected CourseController courseController;
   protected EnrollmentController enrollmentController;
   //private EnrollmentController enrollmentController;
   /**
    * Constructs an ApplicationController which sets up controllers for different parts of the application.
    *
    * @param inputReader A BufferedReader to handle input from the user.
    * @param out A PrintStream to handle output to the user.
    */
   public ApplicationController(BufferedReader inputReader, PrintStream out) {
       this.inputReader = inputReader;
       this.out = out;
       this.MainMenuController = new MainMenuController(inputReader, out); // Adjusted to pass ApplicationController itself
       this.courseController = new CourseController(inputReader, out);
       this.enrollmentController = new EnrollmentController(inputReader, out);
   }
   /**
    * Starts the application by showing the main menu.
    *
    * @throws IOException If an input or output exception occurred.
    * @throws SQLException If a database access error occurs.
    */
   public void startApplication() throws IOException, SQLException {
       MainMenuController.startMainMenu();
   }

}
