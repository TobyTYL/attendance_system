package edu.duke.ece651.team1.enrollmentApp_javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button manageStudentsButton;

    @FXML
    private Button manageProfessorsButton;

    @FXML
    private Button exitButton;

    @FXML
    protected void onManageStudentsClick() {
        // Add navigation or functionality to manage students
    }

    @FXML
    protected void onManageProfessorsClick() {
        try {
            // Load the professor management FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfessorManagement.fxml"));
            Parent professorManagementRoot = loader.load();

            // Get the current stage (window) using the manageProfessorsButton
            Stage stage = (Stage) manageProfessorsButton.getScene().getWindow();

            // Create a new scene with the professor management layout
            Scene professorManagementScene = new Scene(professorManagementRoot);

            // Set the new scene on the current stage
            stage.setScene(professorManagementScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }


    @FXML
    protected void onExitClick() {
        System.exit(0);
    }
}
