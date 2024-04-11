package edu.duke.ece651.team1.enrollmentApp.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The MainMenuView class provides methods to display the main menu and retrieve user choices
 * in the Class Management System. This class facilitates navigation through various functionalities
 * such as managing courses, enrolling students, and exiting the application.
 */
public class MainMenuView {
    private final BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a MainMenuView with necessary I/O components.
     *
     * @param inputReader A BufferedReader to handle input from the user.
     * @param out A PrintStream to handle output to the user.
     */
    public MainMenuView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays the main menu options to the user.
     */
    public void displayMenu() {
        out.println("Welcome to the Class Management System (CMS)");
        out.println("Please select an option:");
        out.println("\n==== Main Menu ====");
        out.println("1. Manage Courses");
        out.println("2. Enroll Students");
        // Add other main menu options here
        out.println("3. Exit");
        out.println("Please select an option:");
    }
    /**
     * Reads the user's choice from the input and validates it.
     *
     * @return An integer representing the valid menu option chosen by the user, or -1 if an invalid choice is made.
     * @throws IOException if an input or output exception occurred.
     */
    public int getMenuChoice() throws IOException {
        String line = inputReader.readLine();
        try {
            int choice = Integer.parseInt(line);
            if (choice < 1 || choice > 3) { // Adjust the upper bound based on the number of menu items
                out.println("Invalid choice. Please enter a number between 1 and 3.");
                return -1; // Indicates an invalid choice
            }
            return choice;
        } catch (NumberFormatException e) {
            out.println("Invalid input. Please enter a number.");
            return -1; // Indicates an invalid choice
        }
    }
}

