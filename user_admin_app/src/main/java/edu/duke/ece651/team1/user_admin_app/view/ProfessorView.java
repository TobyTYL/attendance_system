package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.EOFException;

import edu.duke.ece651.team1.shared.Professor;
import java.util.List;

public class ProfessorView {
    public BufferedReader getInputReader() {
        return inputReader;
    }

    public PrintStream getOut() {
        return out;
    }

    private final BufferedReader inputReader;
    private final PrintStream out;

    public ProfessorView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }
    public void showProfessorMenu() {
        out.println("Please select an option:");
        out.println("1. Add a professor");
        out.println("2. Remove a professor");
        out.println("3. Update professor's info");
        out.println("4. Back to main menu");

    }
    public String readProfessorOption() throws IOException {
        int optionNum = ViewUtils.getUserOption(inputReader, out, 5);
        if (optionNum == 1) {
            return "add";
        } else if (optionNum == 2) {
            return "remove";
        } else if (optionNum == 3) {
            return "update";
        } else {
            return "back";
        }
    }

    public String readProfessorName() throws IOException {
        out.println("Enter the name of the professor:");
        return inputReader.readLine().trim();
    }


    public void displayProfessorList(Iterable<Professor> professors) {
        out.println("Professor Name");
        out.println("=============");
        for (Professor professor : professors) {
            out.printf(professor.getDisplayName() + "\n");
        }

    }
    public void showSuccessAddDisplayNameMessage(String professor, String status) {
        out.println("You successfully added " + professor + " to this class " + status);
    }
    public void showSuccessRemoveMessage(String professor) {
        out.println("You successfully removed " + professor);
    }

}
