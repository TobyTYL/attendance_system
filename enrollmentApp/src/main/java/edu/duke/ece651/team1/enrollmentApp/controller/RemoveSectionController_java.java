package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class RemoveSectionController_java {
    @FXML
    private ListView<String> sectionListView;

    @FXML
    private void initialize() {
        sectionListView.setItems(FXCollections.observableArrayList("Section 1: Prof. A", "Section 2: Prof. B"));
    }

    @FXML
    private void onRemoveSection() {
        String selectedSection = sectionListView.getSelectionModel().getSelectedItem();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + selectedSection + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Remove logic here
                System.out.println("Removed " + selectedSection);
            }
        });
    }
     @FXML
    private void onReturn() {
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
