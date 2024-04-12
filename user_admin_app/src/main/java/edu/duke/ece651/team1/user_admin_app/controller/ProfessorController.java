package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;

import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.user_admin_app.view.ProfessorView;
import edu.duke.ece651.team1.shared.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * The ProfessorController class manages professor-related actions in the application.
 * It handles adding and removing professors.
 */
public class ProfessorController {

    private ProfessorDao professorDao;
    // legal name, email, display name
    BufferedReader inputReader;
    final PrintStream out;
    ProfessorView professorView;
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;

    /**
     * Constructs a ProfessorController object with the given input reader and output stream.
     * Initializes the professor view, DAOs, and password encoder.
     *
     * @param inputReader The BufferedReader object for reading user input.
     * @param out The PrintStream object for displaying messages to the user.
     */
    public ProfessorController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.professorView = new ProfessorView(inputReader, out);
        this.professorDao = new ProfessorDaoImp();
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userDao = new UserDaoImp();
    }
    /**
     * Starts the professor menu, allowing users to add or remove professors.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void startProfessorMenu() throws IOException {
        while (true) {
            try {
                professorView.showProfessorMenu();
                String option = professorView.readProfessorOption();
                if (option.equals("add")) {
                    addProfessor();
                } else if (option.equals("remove")) {
                    removeProfessor();
                } else {
                    return;
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for Student Management Menue");
            }
        }
    }
    /**
     * Adds a new professor to the system.
     *
     * @throws IOException If an I/O error occurs.
     */
    // edit from private to public
    public void addProfessor() throws IOException {
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
    /**
     * Removes a professor from the system.
     *
     * @throws IOException If an I/O error occurs.
     */
    // edit from private to public
    public void removeProfessor() throws IOException {
        String professorName = professorView.readProfessorName();
        int userId = userDao.findUserByUsername(professorName).getUserId();
        if (userId != -1) {
//            int professorId = professorDao.findProfessorByUsrID(userId).getProfessorId();
            int professorId = professorDao.findProfessorByUsrID(userId).getProfessorId();

            professorDao.removeProfessor(professorId);
            userDao.removeUser(userId);
            out.println("Professor removed successfully.");
        } else {
            out.println("Professor not found.");
        }
    }
    /**
     * Sets the professor DAO.
     *
     * @param professorDao The ProfessorDao object to be set.
     */
    public void setProfessorDao(ProfessorDao professorDao) {
        this.professorDao = professorDao;
    }
    /**
     * Sets the user DAO.
     *
     * @param userDao The UserDao object to be set.
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * Sets the professor view.
     *
     * @param professorView The ProfessorView object to be set.
     */
    public void setProfessorView(ProfessorView professorView) {
        this.professorView = professorView;
    }
}