package edu.duke.ece651.team1.user_admin_app.controller;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static java.lang.System.out;

public class ProfessorControllerJavaFX {
    private ProfessorDao professorDao = new ProfessorDaoImp();
    private UserDao userDao = new UserDaoImp();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @FXML
    private Button button_add_professor;
    @FXML
    private Button button_remove_professor;
    @FXML
    private Button button_back_to_professor_view;
    @FXML
    private Button button_back_to_main_view;
    @FXML
    private TextField add_professor_name;
    @FXML
    private TextField remove_professor_name;
    @FXML
    private void addProfessorButtonClicked(ActionEvent event) {
        try {
            FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Professor/add-professor-view.fxml"));
            Parent root = professorLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_add_professor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addProfessorConfirmButtonClicked(ActionEvent event) {
        String professorName = add_professor_name.getText();
        User user = new User(professorName, passwordEncoder.encode("passw0rd"), "Professor");
        int userId = userDao.addUser(user);
        Professor newProfessor = new Professor(userId, professorName);
        if (professorDao.checkProfessorExists(professorName)) {
            out.println("Professor Already exists!");
            return;
        }
        professorDao.addProfessor(newProfessor);
        out.println("Professor added successfully.");    }
    @FXML
    private void removeProfessorButtonClicked(ActionEvent event) {
        try {
            FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Professor/remove-professor-view.fxml"));
            Parent root = professorLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_remove_professor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void removeProfessorConfirmButtonClicked(ActionEvent event) {
        String professorName = remove_professor_name.getText();
        int userId = userDao.findUserByUsername(professorName).getUserId();
        if (userId != -1) {
            int professorId = professorDao.findProfessorByUsrID(userId).getProfessorId();
            professorDao.removeProfessor(professorId);
            userDao.removeUser(userId);
            out.println("Professor removed successfully.");
        } else {
            out.println("Professor not found.");
        }
    }
    @FXML
    private void backProfessorPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Professor/professor-view.fxml"));
            Parent root = professorLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_professor_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void backMainPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/hello-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_main_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
