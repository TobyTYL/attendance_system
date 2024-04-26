package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.collections.FXCollections;

public class UpdateSectionController_javafx {
    @FXML
    private ListView<String> sectionListView;
    @FXML
    private ComboBox<String> professorComboBox;
    // @FXML
    // private Button returnButton;

    @FXML
    private void initialize() {
        // Populate these from a database or service
        sectionListView.setItems(FXCollections.observableArrayList("Section 1: Prof. A", "Section 2: Prof. B"));
        professorComboBox.setItems(FXCollections.observableArrayList("Prof. A", "Prof. B", "Prof. C"));
    }

    @FXML
    private void onUpdateSection() {
        String selectedSection = sectionListView.getSelectionModel().getSelectedItem();
        String selectedProfessor = professorComboBox.getSelectionModel().getSelectedItem();
        // Update logic here
        System.out.println("Updating " + selectedSection + " to have professor " + selectedProfessor);
    }
    @FXML
    private void onReturn() {
        // Code to return to the main screen or previous screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseChoice.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) sectionListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
