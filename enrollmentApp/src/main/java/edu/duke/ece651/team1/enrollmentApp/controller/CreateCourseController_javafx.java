package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
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


    // Handler for the 'Create' button action
    @FXML
    private void onCreateClick() {
        String courseName = userInputCourseName.getText().trim();
        if (courseName.isEmpty()) {
            createCourseResult.setText("Course name cannot be empty.");
            return;
        }
    
        try{
            // Check if the course already exists
            if (courseDao.checkCourseExists(courseName)) {
                createCourseResult.setText("Course '" + courseName + "' already exists.");
                return;
            }
            Course newCourse = new Course(courseName);
            courseDao.addCourse(newCourse);
            createCourseResult.setText("Course '" + courseName + "' created successfully.");
        }catch(IllegalArgumentException e){
            createCourseResult.setText("Invalid course name.");
        }catch(Exception e){
            createCourseResult.setText("Error creating the course: " + e.getMessage());
            e.printStackTrace();
        }
    
        
        createCourseResult.setText("Course '" + courseName + "' created successfully.");
    }

    // Handler for the 'Return' button action
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
}