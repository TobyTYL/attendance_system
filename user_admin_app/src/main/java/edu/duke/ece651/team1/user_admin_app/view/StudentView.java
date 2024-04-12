package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;

import edu.duke.ece651.team1.shared.Student;
import java.util.List;
/**
 * The StudentView class handles displaying options related to students to the user
 * and reading student-related input from the user.
 */
public class StudentView {
    public BufferedReader getInputReader() {
        return inputReader;
    }
    public PrintStream getOut() {
        return out;
    }
    private final BufferedReader inputReader;
    private final PrintStream out;
    /**
     * Constructs a StudentView object with the given input reader and output stream.
     *
     * @param inputReader The BufferedReader object for reading user input.
     * @param out         The PrintStream object for displaying messages to the user.
     */
    public StudentView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    /**
     * Displays the menu options related to students to the user.
     */
    public void showStudentMenu() {
        out.println("Please select an option:");
        out.println("1. Add a student");
        out.println("2. Remove a student");
        out.println("3. Update a student");
        out.println("4. Back to main menu");
    }
    /**
     * Reads the user's choice of student-related option from the menu.
     *
     * @return A string representing the user's selected option ("add", "remove", "update", or "back").
     * @throws IOException If an I/O error occurs.
     */
    public String readStudentOption() throws IOException {
        int optionNum = ViewUtils.getUserOption(inputReader, out, 5);
        if (optionNum == 1) {
            return "add";
        } else if (optionNum == 2) {
            return "remove";
        } else if (optionNum == 3) {
            return "update";
        } else  {
            return "back";
        }
    }
    /**
     * Reads the name of the student entered by the user.
     *
     * @return A string representing the name of the student entered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public String readStudentName() throws IOException {
        out.println("Enter the name of the student:");
        return inputReader.readLine().trim();
    }
    /**
     * Reads the display name of the student entered by the user.
     *
     * @return A string representing the display name of the student entered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public String readStudentDisplayName() throws IOException {
        out.println("Enter the display name of the student:");
        return inputReader.readLine().trim();
    }
    /**
     * Reads the email of the student entered by the user.
     *
     * @return A string representing the email of the student entered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public String readStudentEmail() throws IOException {
        out.println("Enter the email of the student:");
        return inputReader.readLine();
    }
    /**
     * Displays a list of students to the user.
     *
     * @param students The list of students to be displayed.
     */
    public void displayStudentList(Iterable<Student> students) {
        // out.println("You successfully Load student");
        out.println("Legal Name   Display Name   Email");
        out.println("=====================================");
        for (Student student : students) {
            out.printf(student.getLegalName() + ", " + student.getDisPlayName() + ", " + student.getEmail() + "\n");
        }
    }
    /**
     * Displays a success message for adding a display name to a student.
     *
     * @param student The name of the student.
     * @param status  The status of the action.
     */
    public void showSuccessAddDisplayNameMessage(String student, String status) {
        out.println("You successfully added " + student + " display name to this class " + status);
    }
    /**
     * Displays a success message for removing a student.
     *
     * @param student The name of the student.
     */
    public void showSuccessRemoveMessage(String student) {
        out.println("You successfully removed " + student);
    }
    /**
     * Displays a message indicating that the student was not found for the specified action.
     *
     * @param action The action for which the student was not found (e.g., "add", "remove", "update").
     */
    public void showStudentNotFoundMessage(String action) {
        out.println("The student you want to "+ action +" was not found");
    }

}
