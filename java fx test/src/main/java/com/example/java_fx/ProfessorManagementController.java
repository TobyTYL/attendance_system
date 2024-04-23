package com.example.java_fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfessorManagementController {

    @FXML
    private Button addProfessorButton;

    @FXML
    private Button removeProfessorButton;

    @FXML
    private Button backButton;

    @FXML
    protected void onAddProfessorClick() {
        try {
            // Load the Add Professor FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProfessor.fxml"));
            Parent addProfessorRoot = loader.load();

            // Get the current stage (window) using any known control on the current scene
            Stage stage = (Stage) backButton.getScene().getWindow(); // Assuming backButton is on the scene

            // Create a new scene with the Add Professor layout
            Scene addProfessorScene = new Scene(addProfessorRoot);

            // Set the new scene on the current stage
            stage.setScene(addProfessorScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }


    @FXML
    protected void onRemoveProfessorClick() {
        // Logic for removing a professor
    }

    @FXML
    protected void onBackClick() {
        try {
            // Load the main menu FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainApp.fxml"));
            Parent mainMenuRoot = loader.load();

            // Get the current stage (window) from the back button using getScene() then getWindow()
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create a new scene with the main menu layout
            Scene mainMenuScene = new Scene(mainMenuRoot);

            // Set the new scene on the current stage
            stage.setScene(mainMenuScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
}
