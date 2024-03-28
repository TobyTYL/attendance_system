package edu.duke.ece651.team1.client.view;

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
        out.println("3. Load a Student info CSV file");
        out.println("4. Edit a student display name");
        out.println("5. Back to main menue");

    }

    public void showSuccessEditNameMessage(String legalName, String displayName) {
        out.println("You successfully edit " + legalName + "'s display name to " + displayName);
    }


    public void showSuccessAddDisplayNameMessage(String student, String status) {
        out.println("You successfully added " + student + " display name to this class " + status);
    }

    public void showSuccessRemoveMessage(String student) {
        out.println("You successfully removed " + student);
    }

    
    // public String readStudentOption() throws IOException {
    // int optionNum = ViewUtils.getUserOption(inputReader, out, 3);
    //
    // Map<Integer, String> optionToString = Map.of(1, "add",
    // 2, "remove",
    // 3, "import",
    // 4, "change");
    // String res = optionToString.get(optionNum);
    // if (res == null) {
    // throw new NullPointerException("key nonexistent");
    // }
    // return res;
    // }

    public String readStudentOption() throws IOException {
        int optionNum = ViewUtils.getUserOption(inputReader, out, 5);
        if (optionNum == 1) {
            return "add";
        } else if (optionNum == 2) {
            return "remove";
        } else if (optionNum == 3) {
            return "load";
        } else if (optionNum == 4) {
            return "edit";
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

    // public String prompt
    public void showLoadSuccessMessage() {
        out.println("You have successfully Load students from csv file");
        out.println("Please check the information below");
    }

    public void displayStudentList(Iterable<Student> students) {
        // out.println("You successfully Load student");
        out.println("Legal Name   Display Name   Email");
        out.println("=====================================");
        for (Student student : students) {
            out.printf(student.getLegalName() + ", " + student.getDisPlayName() + ", " + student.getEmail() + "\n");
        }

    }

    public String getFileName() throws IOException {
        String input;
        out.println("Firstly please put your file under src/input folder");
        out.println("Secoundly please input a file name (including .csv extension)");
        input = inputReader.readLine();
        if (input == null) {
            throw new EOFException("End of input reached");
        }
        input = input.trim();
        return input;

    }

    public void showStudentNotFoundMessage(String action) {
        out.println("The student you want to "+action+" was not found");
    }

}
