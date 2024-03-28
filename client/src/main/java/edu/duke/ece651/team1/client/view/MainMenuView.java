package edu.duke.ece651.team1.client.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.PrintStream;
/**
 * The MainMenuView class manages the presentation of the main menu options to the user.
 * It provides methods to display the main menu, read the user's choice, and handle the options accordingly.
 */
public class MainMenuView {
    BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a MainMenuView object with the specified input reader and output stream.
     * @param inputReader The BufferedReader to read user input from.
     * @param out The PrintStream to print output to.
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
        out.println("1. Manage your attendance");
        out.println("2. Manage your students");
        out.println("3. Exit");
    }
    /**
     * Reads the user's choice from the main menu options.
     * @return The selected option ("attendance", "students", or "exit").
     * @throws IOException If an I/O error occurs.
     */
    public String readMainOption() throws IOException {
        while (true) {
            try {
                int optionNum = ViewUtils.getUserOption(inputReader, out, 3);
                if (optionNum == 1) {
                    return "attendance";
                } if(optionNum==2) {
                    return "students";
                }else{
                    return "exit";
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option. Please select 1 for Manage attendance or 2 for Manage Students.");
            }
        }

    }

}
