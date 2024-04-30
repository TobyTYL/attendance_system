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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static java.lang.System.out;
/**
 * Controller class for managing professor-related operations in the JavaFX user admin application.
 */
public class ProfessorControllerJavaFX {
    ProfessorDao professorDao = new ProfessorDaoImp();
    UserDao userDao = new UserDaoImp();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @FXML
    Button button_add_professor;
    @FXML
    Button button_remove_professor;
    @FXML
    Button button_back_to_professor_view;
    @FXML
    Button button_back_to_main_view;
    @FXML
    TextField add_professor_name;
    @FXML
    TextField remove_professor_name;
    /**
     * Event handler for clicking the add professor button.
     * Loads the add professor view.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void addProfessorButtonClicked(ActionEvent event) {
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
    /**
     * Event handler for confirming the addition of a professor.
     * Adds a new professor and associated user to the database.
     * Displays success or error message accordingly.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void addProfessorConfirmButtonClicked(ActionEvent event) {
        String professorName = add_professor_name.getText();
        User user = new User(professorName, passwordEncoder.encode("passw0rd"), "Professor");
        int userId = userDao.addUser(user);
        Professor newProfessor = new Professor(userId, professorName);
        if (professorDao.checkProfessorExists(professorName)) {
            AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Professor Already exists!");
            return;
        }
        professorDao.addProfessor(newProfessor);
        AlertWindowController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Professor add Successful!");
    }
    /**
     * Event handler for clicking the remove professor button.
     * Loads the remove professor view.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void removeProfessorButtonClicked(ActionEvent event) {
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
    /**
     * Event handler for confirming the removal of a professor.
     * Retrieves the professor name from the text field, finds the associated user ID,
     * removes the professor and user from the database if found, and displays success or error message accordingly.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void removeProfessorConfirmButtonClicked(ActionEvent event) {
        String professorName = remove_professor_name.getText();
        int userId = userDao.findUserByUsername(professorName).getUserId();
        if (userId != -1) {
            int professorId = professorDao.findProfessorByUsrID(userId).getProfessorId();
            professorDao.removeProfessor(professorId);
            userDao.removeUser(userId);
            AlertWindowController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Professor removed successfully!");
        } else {
            AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Professor not found.");
        }
    }
    /**
     * Event handler for clicking the back button to return to the professor page.
     * Retrieves the scene of the button, gets the stage from the scene,
     * loads the professor view using FXMLLoader, and sets the scene to the loaded view.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void backProfessorPageButtonClicked(ActionEvent event) {
        Scene buttonScene = button_back_to_professor_view.getScene();
        if (buttonScene != null) {
            Stage stage = (Stage) buttonScene.getWindow();
            if (stage != null) {
                try {
                    FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Professor/professor-view.fxml"));
                    Parent root = professorLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Stage is null!");
            }
        } else {
            System.err.println("Scene is null!");
        }
    }
    /**
     * Event handler for clicking the back button to return to the main page.
     * Retrieves the scene of the button, gets the stage from the scene,
     * loads the main view using FXMLLoader, and sets the scene to the loaded view.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void backMainPageButtonClicked(ActionEvent event) {
        Scene buttonScene = button_back_to_main_view.getScene();
        if (buttonScene != null) {
            Stage stage = (Stage) buttonScene.getWindow();
            if (stage != null) {
                try {
                    FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/hello-view.fxml"));
                    Parent root = studentLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Stage is null!");
            }
        } else {
            System.err.println("Scene is null!");
        }
    }
}
