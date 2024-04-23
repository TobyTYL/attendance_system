package com.example.java_fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProfessorController {

    @FXML
    private TextField nameTextField;

    @FXML
    private Label confirmationLabel;

    @FXML
    private Button applyButton;

    @FXML
    private Button backButton;

    @FXML
    protected void onApplyButtonClick() {
        String professorName = nameTextField.getText();
        if (!professorName.isEmpty()) {
            confirmationLabel.setText("Professor " + professorName + " added successfully");
        } else {
            confirmationLabel.setText("Please enter a name.");
        }
    }

    @FXML
    protected void onBackButtonClick() {
        try {
            // Load the Professor Management FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfessorManagement.fxml"));
            Parent professorManagementRoot = loader.load();

            // Get the current stage (window) using the back button
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create a new scene with the Professor Management layout
            Scene professorManagementScene = new Scene(professorManagementRoot);

            // Set the new scene on the current stage
            stage.setScene(professorManagementScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }

    // Include other methods and initialization code as needed...
}
