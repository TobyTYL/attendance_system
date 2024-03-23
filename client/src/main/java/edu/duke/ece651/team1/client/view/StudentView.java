package edu.duke.ece651.team1.client.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

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
        // add, remove, load, change_display_name
        out.println("You are accessing student records. Insert number for options: ");
        out.println("1. manually add student record(s)");
        out.println("2. remove student record(s)");
        out.println("3. import student from CSV");
        out.println("4. change student display name ");
    }

    public String readStudentOption() throws IOException {
        int optionNum = ViewUtils.getUserOption(inputReader, out, 3);

        Map<Integer, String> optionToString = Map.of(1, "add",
                2, "remove",
                3, "import",
                4, "change");
        String res = optionToString.get(optionNum);
        if (res == null) {
            throw new NullPointerException("key nonexistent");
        }
        return res;
    }

    public String readStudentName() throws IOException {
        out.println("Enter the name of the student:");
        return inputReader.readLine().trim();
    }

    public String readStudentDisplayName() throws IOException {
        out.println("Enter the display name of the student:");
        return inputReader.readLine().trim();
    }








}
