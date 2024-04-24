package edu.duke.ece651.team1.enrollmentApp_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CreateCourseController {

    @FXML
    private Button createCourseButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField userInputCourseName;

    @FXML
    private TextField createCourseResult;



    // Handler for the 'Create' button action
    @FXML
    private void onCreateClick() {
        String courseName = userInputCourseName.getText();
        // Here, add your logic to create a course using the name provided
        // For now, it just simulates a course creation
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