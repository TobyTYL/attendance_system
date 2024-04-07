package edu.duke.ece651.team1.enrollmentApp.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;


public class MainMenuView {
    private final BufferedReader inputReader;
    private final PrintStream out;

    public MainMenuView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

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

