package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.enrollmentApp.Model;
import edu.duke.ece651.team1.shared.Course;

public class SelectCourseController_javafx {
    @FXML
    private ComboBox<String> classComboBox;
    private CourseDao courseDao = new CourseDaoImp(); //
    private Course selectedCourse; // Store the selected course here

    @FXML
    private void initialize() {
        // Populate the ComboBox with class names from the database
        List<Course> courses = courseDao.getAllCourses();
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }
        classComboBox.setItems(FXCollections.observableArrayList(courseNames));
    }

    @FXML
    private void onSelectClass() {
        String selectedClass = classComboBox.getSelectionModel().getSelectedItem();
        if (selectedClass != null) {
            for (Course course : courseDao.getAllCourses()) {
                if (course.getName().equals(selectedClass)) {
                    Model.setSelectedCourse(course);
                    break;
                }
            }
            System.out.println("Selected Class: " + selectedClass);
            loadScene("/CourseChoice.fxml");
        }
    }

    @FXML
    private void onReturn() {
        // Return to the main menu or previous screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseMgmtNavi.fxml")); // Adjust the FXML file name as necessary
            Parent root = loader.load();
            Stage stage = (Stage) classComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) classComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, display an error message to the user if the FXML file cannot be loaded.
        }
    }
}
