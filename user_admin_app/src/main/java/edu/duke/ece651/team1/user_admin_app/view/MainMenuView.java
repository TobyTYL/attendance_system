package edu.duke.ece651.team1.user_admin_app.view;

import java.io.BufferedReader;
import java.io.IOException;
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
        out.println("1. Manage Professor Users");
        out.println("2. Manage Student Users");
        out.println("3. Exit");
    }
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