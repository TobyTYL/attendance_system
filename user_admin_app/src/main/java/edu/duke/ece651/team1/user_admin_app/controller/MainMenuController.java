package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import edu.duke.ece651.team1.user_admin_app.view.MainMenuView;

public class MainMenuController {
    BufferedReader inputReader;
    final PrintStream out;
    MainMenuView mainMenuView;
    StudentController studentController;
    ProfessorController professorController;

    public MainMenuController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.mainMenuView = new MainMenuView(inputReader, out);
        this.professorController = new ProfessorController(inputReader, out);
        this.studentController = new StudentController(inputReader, out);
    }

    public void startMainMenu() {
        while (true) {
            try{
                mainMenuView.showMainMenu();
                String option = mainMenuView.readMainOption();
                if (option.equals("professors")) {
                    professorController.startProfessorMenu();
                }
                else if (option.equals("students")) {
//                    attendanceController.startStudentManager();
                    studentController.startStudentMenu();
                }
                else {
                    out.println("GoodBye!");
                    break;
                }
            } catch (Exception e) {
                out.println("Main Menu error because "+e.getMessage());
            }
        }
    }

}