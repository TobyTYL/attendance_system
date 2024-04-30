package edu.duke.ece651.team1.enrollmentApp.view;
import edu.duke.ece651.team1.shared.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Predicate;

/**
* Provides the user interface for managing courses within the application.
* This includes creating, updating, removing courses, and navigating back to the main menu.
*/
public class CourseView {
   private BufferedReader inputReader;
   private final PrintStream out;
   /**
    * Constructor for CourseView initializing I/O resources.
    *
    * @param inputReader A BufferedReader to read input from the user.
    * @param out A PrintStream to write output to the user.
    */
   public CourseView(BufferedReader inputReader, PrintStream out){
       this.inputReader = inputReader;
       this.out = out;
   }
   /**
    * Displays options for managing courses.
    */
   public void showClassManageOption(){
       out.println("Please select an option to manage the classes");
       out.println("1. Create a New Class");
       out.println("2. Update Existing Class");
       out.println("3. Remove Existing Class");
       out.println("4. Back to Main Menu");
   }
   /**
    * Prompts and retrieves user choice for course management.
    *
    * @return An integer representing the user's menu choice.
    * @throws IOException if an I/O error occurs.
    */
   public int getStudentManageChoice() throws IOException{
       return ViewUtils.getUserOption(inputReader, out, 4);
   }
    /**
    * Prompts the user for the name of a class to create.
    *
    * @return A string representing the class name.
    * @throws IOException if an I/O error occurs.
    */
   public String getClassNameToCreate() throws IOException {
       return ViewUtils.getUserInput(
               "\nEnter the name of the class to create: ",
               "Class name cannot be empty. Please enter a valid name: ",
               inputReader,
               out,
               input -> !input.trim().isEmpty()
       );
   }
   /**
    * Gets the user choice for updating courses within the manage courses menu.
    *
    * @return An integer representing the user's choice.
    * @throws IOException if an I/O error occurs.
    */
   public int getStudentUpdateCourseChoice() throws IOException{
       return ViewUtils.getUserOption(inputReader, out, 5);
   }
   /**
    * Displays a success message for course creation.
    *
    * @param course The name of the course that was created.
    */
   public void showCreateNewClassSuccessMessage(String course){
       out.println("You successfully created " + course + " class");
   }
   /**
    * Prompts the user for a class name to update or remove.
    *
    * @param action A string indicating the action (update or remove).
    * @return A string representing the class name.
    * @throws IOException if an I/O error occurs.
    */
   public String getClassNameToUpdateOrRemove(String action) throws IOException {

       return ViewUtils.getUserInput(
               "\nEnter the name of the class you wish to " + action + ": ",
               "Please enter a valid name: ",
               inputReader,
               out,
               input -> true // Accept all non-empty input
       );
   }
   /**
    * Confirms the user's intention to perform a specified action on a class.
    *
    * @param action The action to be confirmed (e.g., "remove").
    * @param className The name of the class affected by the action.
    * @return true if the user confirms the action, false otherwise.
    * @throws IOException if an I/O error occurs.
    */
   public boolean confirmAction(String action, String className) throws IOException {
       String userInput = ViewUtils.getUserInput(
               "\nAre you sure you want to " + action + " " + className + "? This action cannot be undone. (yes/no): ",
               "Invalid response. Please answer yes or no: ",
               inputReader,
               out,
               input -> input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")
       );
       return "yes".equalsIgnoreCase(userInput) || "Y".equalsIgnoreCase(userInput);
   }
   /**
    * Displays a confirmation message for an action taken on a class.
    *
    * @param action The action performed (e.g., "removed").
    * @param className The name of the class affected by the action.
    */
   public void showActionConfirmation(String action, String className) {
       out.println("\nClass " + className + " " + action + " successfully.");
   }
   /**
    * Displays class section management options related to a specific class.
    *
    * @param className The name of the class being managed.
    */
   public void showClassSectionOptions(String className) {
       out.println("\nManage " + className);
       out.println("1. Add a Section");
       out.println("2. Remove a Section");
       out.println("3. Update a Section");
       out.println("4. Update class Name");
       out.println("5. Return to Previous Menu");
       out.print("Enter your choice: ");
   }
   /**
    * Prompts for and retrieves a new class name from the user.
    *
    * @return A string representing the new class name.
    * @throws IOException if an I/O error occurs.
    */
   public String getNewClassName() throws IOException {
       return ViewUtils.getUserInput(
               "Enter the new name for the class: ",
               "Class name cannot be empty. Please enter a valid name: ",
               inputReader,
               out,
               input -> !input.trim().isEmpty()
       );
   }
   /**
    * Displays a message confirming the successful update of a class name.
    *
    * @param oldClassName The original class name.
    * @param newClassName The new class name that replaces the old one.
    */
   public void showUpdateClassNameConfirmation(String oldClassName, String newClassName) {
       out.println("\nSuccessfully updated the class name from \"" + oldClassName + "\" to \"" + newClassName + "\".");
   }
}
