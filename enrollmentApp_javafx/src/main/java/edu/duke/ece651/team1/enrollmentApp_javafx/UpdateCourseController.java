package edu.duke.ece651.team1.enrollmentApp_javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCourseController {

    @FXML
    private ListView<String> coursesListView;

    @FXML
    private ListView<String> sectionsListView;

    @FXML
    private ListView<String> professorsListView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button assignButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField resultText;

    // This method is called upon the FXML file loading
    public void initialize() {
        // Populate the courses ListView with fake data
        ObservableList<String> courses = FXCollections.observableArrayList("Course A", "Course B", "Course C");
        coursesListView.setItems(courses);

        // Select the first course by default and load its sections
        coursesListView.getSelectionModel().selectFirst();
        loadSectionsForCourse(coursesListView.getSelectionModel().getSelectedItem());

        // Populate the professors ListView with fake data
        ObservableList<String> professors = FXCollections.observableArrayList("Prof. X", "Prof. Y", "Prof. Z");
        professorsListView.setItems(professors);
    }

    private void loadSectionsForCourse(String course) {
        // Assume this method fetches sections for the given course
        ObservableList<String> sections = FXCollections.observableArrayList("Section 1", "Section 2", "Section 3");
        sectionsListView.setItems(sections);
    }

    @FXML
    private void onAddClick() {
        // Logic for adding a section to the selected course
    }

    @FXML
    private void onRemoveClick() {
        // Logic for removing the selected section
    }

    @FXML
    private void onAssignClick() {
        // Logic for assigning the selected professor to the selected section
    }

    @FXML
    private void onReturnClick() {
        try {
            // Load the previous panel's FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseMgmtNavi.fxml")); // Replace with actual FXML filename for the navigation panel
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

    // Include any additional methods and event handling code as needed
}