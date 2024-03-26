package edu.duke.ece651.team1.client.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MainMenuView {
    BufferedReader inputReader;
    private final PrintStream out;

    public MainMenuView(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
    }

    public void showMainMenu() {
        out.println("Please select an option to begin:");
        out.println("1. Manage your attendance");
        out.println("2. Manage your students");
        out.println("3. Exit");
    }

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
