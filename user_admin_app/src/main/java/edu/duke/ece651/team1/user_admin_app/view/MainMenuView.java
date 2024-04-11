package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
/**
 * The MainMenuView class handles displaying the main menu options to the user.
 * It allows the user to select options for managing professors, students, or exiting the application.
 */
public class MainMenuView {
    BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a MainMenuView object with the given input reader and output stream.
     *
     * @param inputReader The BufferedReader object for reading user input.
     * @param out The PrintStream object for displaying messages to the user.
     */
    public MainMenuView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays the main menu options to the user.
     */
    public void showMainMenu() {
        out.println("Please select an option to begin:");
        out.println("1. Manage Professor Users");
        out.println("2. Manage Student Users");
        out.println("3. Exit");
    }
    /**
     * Reads the user's main menu option choice.
     *
     * @return A string representing the user's selected option ("professors", "students", or "exit").
     * @throws IOException If an I/O error occurs.
     */
    public String readMainOption() throws IOException {
        while (true) {
            try {
                int optionNum = ViewUtils.getUserOption(inputReader, out, 3);
                if (optionNum == 1) {
                    return "professors";
                } if(optionNum==2) {
                    return "students";
                }else{
                    return "exit";
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option. Please select 1 for Manage professors or 2 for Manage students.");
            }
        }
    }
}