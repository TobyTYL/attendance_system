package edu.duke.ece651.team1.enrollmentApp.view;
import edu.duke.ece651.team1.client.view.ViewUtils;
import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.shared.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Predicate;


public class CourseView {
    private BufferedReader inputReader;
    private final PrintStream out;

    public CourseView(BufferedReader inputReader, PrintStream out){
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showClassManageOption(){
        out.println("Please select an option to manage the classes");
        out.println("1. Create a New Class");
        out.println("2. Update Existing Class");
        out.println("3. Remove Existing Class");
        out.println("4. Back to Main Menu");
    }
    public int getStudentManageChoice() throws IOException{
        return ViewUtils.getUserOption(inputReader, out, 4);
    }
     
    public String getClassNameToCreate() throws IOException {
        return ViewUtils.getUserInput(
                "\nEnter the name of the class to create: ",
                "Class name cannot be empty. Please enter a valid name: ",
                inputReader,
                out,
                input -> !input.trim().isEmpty()
        );
    }
    public void showCreateNewClassSuccessMessage(String course){
        out.println("You successfully created " + course + "class");
    }
    public String getClassNameToUpdateOrRemove(String action) throws IOException {
        
        return ViewUtils.getUserInput(
                "\nEnter the name of the class you wish to " + action + ": ",
                "Please enter a valid name: ",
                inputReader,
                out,
                input -> true // Accept all non-empty input
        );
    }
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

    public void showActionConfirmation(String action, String className) {
        out.println("\nClass " + className + " " + action + " successfully.");
    }
    public void showClassSectionOptions(String className) {
        out.println("\nManage " + className);
        out.println("1. Add a Section");
        out.println("2. Remove a Section");
        out.println("3. Update a Section");
        out.println("4. Update class Name");
        out.println("5. Return to Previous Menu");
        out.print("Enter your choice: ");
    }

    public String getNewClassName() throws IOException {
        return ViewUtils.getUserInput(
                "Enter the new name for the class: ",
                "Class name cannot be empty. Please enter a valid name: ",
                inputReader,
                out,
                input -> !input.trim().isEmpty()
        );
    }
    public void showUpdateClassNameConfirmation(String oldClassName, String newClassName) {
        out.println("\nSuccessfully updated the class name from \"" + oldClassName + "\" to \"" + newClassName + "\".");
    }
}
