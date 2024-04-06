package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.user_admin_app.view.ProfessorView;
import edu.duke.ece651.team1.shared.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
public class ProfessorController {

    private final ProfessorDao professorDao;
    // legal name, email, display name
    BufferedReader inputReader;
    final PrintStream out;
    ProfessorView professorView;
    private PasswordEncoder passwordEncoder;
    private final UserDao userDao;


    public ProfessorController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.professorView = new ProfessorView(inputReader, out);
        this.professorDao = new ProfessorDaoImp();
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userDao = new UserDaoImp();



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
        String professorName = professorView.readProfessorName();
        User user = new User(professorName, passwordEncoder.encode("passw0rd"), "Professor");
        int userId = userDao.addUser(user);
        Professor newProfessor = new Professor(userId, professorName);
        if (professorDao.checkProfessorExists(professorName)) {
            out.println("Professor Already exists!");
            return;
        }
        professorDao.addProfessor(newProfessor);
        out.println("Professor added successfully.");

    }

}