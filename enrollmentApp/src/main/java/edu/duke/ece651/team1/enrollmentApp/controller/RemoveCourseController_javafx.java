package edu.duke.ece651.team1.enrollmentApp.controller;

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
import java.util.List;
import java.util.stream.Collectors;

import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.shared.Course;

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

        if (selectedCourse != null) {
            // Remove the course from the database
            if (courseDao.checkCourseExists(selectedCourse)) {
                // Remove the course from the database
                courseDao.deleteCourse(selectedCourse);
                listCurrCourses.getItems().remove(selectedCourse);
                removeResult.setText("Removed: " + selectedCourse);
            }else{
                removeResult.setText("Course '" + selectedCourse + "' does not exist.");
            }
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