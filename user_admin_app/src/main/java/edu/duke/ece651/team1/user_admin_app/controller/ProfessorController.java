package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;


import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.user_admin_app.view.ProfessorView;

import edu.duke.ece651.team1.shared.Professor;

public class ProfessorController {

    private final ProfessorDao professorDao;
    // legal name, email, display name
    BufferedReader inputReader;
    final PrintStream out;
    ProfessorView professorView;

    public ProfessorController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.professorView = new ProfessorView(inputReader, out);
        this.professorDao = new ProfessorDaoImp();

    }

    public void startProfessorMenu() throws IOException {
        while (true) {
            try {
                professorView.showProfessorMenu();
                String option = professorView.readProfessorOption();
                if (option.equals("add")) {
                    addProfessor();
                } else if (option.equals("remove")) {
                    //removeStudent();
                } else if (option.equals("update")) {
                    //todo: updateStudent();
                } else {
                    //back to main menu
                    return;
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for Student Management Menue");
            }
        }
    }

    private void addProfessor() throws IOException {
        String displayName = professorView.readProfessorName();
        Professor newProfessor = new Professor(displayName);
        if (professorDao.checkProfessorExists(displayName)) {
            out.println("Professor Already exists!");
            return;
        }
        professorDao.addProfessor(newProfessor);
        out.println("Professor added successfully.");

    }

}