package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;

import edu.duke.ece651.team1.shared.Student;
import java.util.List;

public class StudentView {
    public BufferedReader getInputReader() {
        return inputReader;
    }

    public PrintStream getOut() {
        return out;
    }

    private final BufferedReader inputReader;
    private final PrintStream out;

    public StudentView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    public void showStudentMenu() {
        out.println("Please select an option:");
        out.println("1. Add a student");
        out.println("2. Remove a student");
        out.println("3. Update a student");
        out.println("4. Back to main menu");

    }
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

    public String readStudentName() throws IOException {
        out.println("Enter the name of the student:");
        return inputReader.readLine().trim();
    }

    public String readStudentDisplayName() throws IOException {
        out.println("Enter the display name of the student:");
        return inputReader.readLine().trim();
    }

    public String readStudentEmail() throws IOException {
        out.println("Enter the email of the student:");
        return inputReader.readLine();
    }

    public void displayStudentList(Iterable<Student> students) {
        // out.println("You successfully Load student");
        out.println("Legal Name   Display Name   Email");
        out.println("=====================================");
        for (Student student : students) {
            out.printf(student.getLegalName() + ", " + student.getDisPlayName() + ", " + student.getEmail() + "\n");
        }

    }
    public void showSuccessAddDisplayNameMessage(String student, String status) {
        out.println("You successfully added " + student + " display name to this class " + status);
    }
    public void showSuccessRemoveMessage(String student) {
        out.println("You successfully removed " + student);
    }
    public void showStudentNotFoundMessage(String action) {
        out.println("The student you want to "+action+" was not found");
    }

}
