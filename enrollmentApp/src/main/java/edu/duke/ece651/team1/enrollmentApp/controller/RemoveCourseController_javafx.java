package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.shared.Course;
import static edu.duke.ece651.team1.enrollmentApp.controller.UtilController.showAlert;
public class RemoveCourseController_javafx {

    @FXML
    private ListView<String> listCurrCourses;

    @FXML
    private TextField removeResult;

    @FXML
    private Button removeButton;

    @FXML
    private Button returnButton;
    private CourseDaoImp courseDao = new CourseDaoImp();

    // Method called when the controller is instantiated
    public void initialize() {
        List<Course> coursesFromDB = courseDao.getAllCourses();

        ObservableList<String> courses = FXCollections.observableArrayList(
                coursesFromDB.stream().map(Course::getName).collect(Collectors.toList())
        );
        listCurrCourses.setItems(courses);
    }

    @FXML
    private void onRemoveClick() {
        // Get the selected course
        String selectedCourse = listCurrCourses.getSelectionModel().getSelectedItem();
         if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", null, "No course selected!");
            return;
        }

        // Confirmation dialog to ensure that the user wants to proceed with deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove '" + selectedCourse + "'?", ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Proceed with deletion if user confirms
                if (courseDao.checkCourseExists(selectedCourse)) {
                    courseDao.deleteCourse(selectedCourse);
                    listCurrCourses.getItems().remove(selectedCourse);
                    showAlert(Alert.AlertType.INFORMATION, "Course Removed", null, "Removed: " + selectedCourse);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "Course '" + selectedCourse + "' does not exist.");
                }
            }
        });
        
    }

    @FXML
    private void onReturnClick() {
        try {
            // Load the previous panel's FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseMgmtNavi.fxml")); // Replace with actual FXML filename
            Parent root = loader.load();
            Stage stage = (Stage) returnButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Loading Error", null, "Failed to load the navigation panel: " + e.getMessage());
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
}