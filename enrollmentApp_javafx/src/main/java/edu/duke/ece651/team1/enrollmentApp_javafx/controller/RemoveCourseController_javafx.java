package edu.duke.ece651.team1.enrollmentApp_javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class RemoveCourseController_javafx {

    @FXML
    private ListView<String> listCurrCourses;

    @FXML
    private TextField removeResult;

    @FXML
    private Button removeButton;

    @FXML
    private Button returnButton;

    // Method called when the controller is instantiated
    public void initialize() {
        // Initialize the ListView with fake courses
        ObservableList<String> courses = FXCollections.observableArrayList(
                "Course 1",
                "Course 2",
                "Course 3"
        );
        listCurrCourses.setItems(courses);
    }

    @FXML
    private void onRemoveClick() {
        // Get the selected course
        String selectedCourse = listCurrCourses.getSelectionModel().getSelectedItem();

        if (selectedCourse != null) {
            // Here, add your logic to remove the course from the database
            // For now, it just simulates course removal
            listCurrCourses.getItems().remove(selectedCourse);
            removeResult.setText("Removed: " + selectedCourse);
        } else {
            removeResult.setText("No course selected!");
        }
    }

    @FXML
    private void onReturnClick() {
        try {
            // Load the previous panel's FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseMgmtNavi.fxml")); // Replace with actual FXML filename
            Parent root = loader.load();

            // Get the current stage from the return button
            Stage stage = (Stage) returnButton.getScene().getWindow();

            // Create a new scene with the root layout
            Scene scene = new Scene(root);

            // Set the new scene on the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
}