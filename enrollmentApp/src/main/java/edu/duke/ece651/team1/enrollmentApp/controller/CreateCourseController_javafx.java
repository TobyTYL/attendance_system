package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Course.*;
import edu.duke.ece651.team1.shared.*;
import static edu.duke.ece651.team1.enrollmentApp.controller.UtilController.showAlert;
/**
 * Controller class for creating a new course in the enrollment application.
 * This controller manages the interaction between the user interface and the CourseDao implementation.
 */
public class CreateCourseController_javafx {

    @FXML
    private Button createCourseButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField userInputCourseName;

    @FXML
    private TextField createCourseResult;
    private CourseDaoImp courseDao = new CourseDaoImp();


    /**
     * Handles the action when the 'Create' button is clicked.
     * Creates a new course with the name entered by the user.
     */
    @FXML
    private void onCreateClick() {
        String courseName = userInputCourseName.getText().trim();
        if (courseName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", null, "Course name cannot be empty.");
            return;
        }
    
        try{
            // Check if the course already exists
            if (courseDao.checkCourseExists(courseName)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Error", null, "Course '" + courseName + "' already exists.");
                return;
            }
            Course newCourse = new Course(courseName);
            courseDao.addCourse(newCourse);
            createCourseResult.setText("Course '" + courseName + "' created successfully.");
        }catch(IllegalArgumentException e){
            showAlert(Alert.AlertType.ERROR, "Invalid Input", null, "Invalid course name.");
        }catch(Exception e){
            showAlert(Alert.AlertType.ERROR, "Database Error", null, "Error creating the course: " + e.getMessage());
            e.printStackTrace();
        }
    
        
        createCourseResult.setText("Course '" + courseName + "' created successfully.");
    }

     /**
     * Handles the action when the 'Return' button is clicked.
     * Returns to the course management navigation panel.
     */
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
            showAlert(Alert.AlertType.ERROR, "Loading Error", null, "Failed to load the navigation panel: " + e.getMessage());
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
}