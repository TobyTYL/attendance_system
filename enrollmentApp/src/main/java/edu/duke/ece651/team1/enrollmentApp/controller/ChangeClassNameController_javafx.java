package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;

public class ChangeClassNameController_javafx {
    @FXML
    private Label currentClassNameLabel;
    @FXML
    private TextField newClassNameField;

    // This method can be called during the initialization to set the current class name
    public void initialize() {
        // Simulating fetching the current class name from a database
        String currentClassName = "Introduction to Java";
        currentClassNameLabel.setText(currentClassName);
    }

    @FXML
    private void onChangeClassName(ActionEvent event) {
        String newClassName = newClassNameField.getText();
        if (!newClassName.isEmpty()) {
            // Logic to update the class name in the database or wherever it's stored
            System.out.println("Class name updated from " + currentClassNameLabel.getText() + " to " + newClassName);
            currentClassNameLabel.setText(newClassName); // Update the label to reflect the change
        } else {
            System.out.println("New class name cannot be empty");
        }
    }
    @FXML
    private void onReturn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseChoice.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) newClassNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
