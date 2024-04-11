package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;

import edu.duke.ece651.team1.shared.Professor;
import java.util.List;
/**
 * The ProfessorView class handles displaying options related to professors to the user
 * and reading professor-related input from the user.
 */
public class ProfessorView {
    public BufferedReader getInputReader() {
        return inputReader;
    }

    public PrintStream getOut() {
        return out;
    }

    private final BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a ProfessorView object with the given input reader and output stream.
     *
     * @param inputReader The BufferedReader object for reading user input.
     * @param out         The PrintStream object for displaying messages to the user.
     */
    public ProfessorView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays the menu options related to professors to the user.
     */
    public void showProfessorMenu() {
        out.println("Please select an option:");
        out.println("1. Add a professor");
        out.println("2. Remove a professor");
//        out.println("3. Update professor's info");
        out.println("3. Back to main menu");
    }
    /**
     * Reads the user's choice of professor-related option from the menu.
     *
     * @return A string representing the user's selected option ("add", "remove", or "back").
     * @throws IOException If an I/O error occurs.
     */
    public String readProfessorOption() throws IOException {
        int optionNum = ViewUtils.getUserOption(inputReader, out, 5);
        if (optionNum == 1) {
            return "add";
        } else if (optionNum == 2) {
            return "remove";
        } else {
            return "back";
        }
    }
    /**
     * Reads the name of the professor entered by the user.
     *
     * @return A string representing the name of the professor entered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public String readProfessorName() throws IOException {
        out.println("Enter the name of the professor:");
        return inputReader.readLine().trim();
    }
    /**
     * Reads the ID of the professor entered by the user.
     *
     * @return An integer representing the ID of the professor entered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public int readProfessorId() throws IOException {
        out.println("Enter the ID of the professor:");
        String input = inputReader.readLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            out.println("Invalid input. Please enter a valid integer ID.");
            return readProfessorId();
        }
    }
}
