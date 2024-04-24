package edu.duke.ece651.team1.enrollmentApp_javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseMgmtNaviController {

    @FXML
    private Button createClassButton;

    @FXML
    private Button updateClassButton;

    @FXML
    private Button removeClassButton;

    @FXML
    private Button returnButton;

    @FXML
    private void onCreateClassClick() {
        try {
            // Load the Create Course FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateCourse.fxml"));
            Parent createCourseRoot = loader.load();

            // Get the current stage (window) using any node that is part of the scene
            Stage stage = (Stage) createClassButton.getScene().getWindow(); // Replace 'createClassButton' if it's not in the current context

            // Create a new scene with the Create Course layout
            Scene createCourseScene = new Scene(createCourseRoot);

            // Set the new scene on the current stage
            stage.setScene(createCourseScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }


    @FXML
    private void onUpdateClassClick() {
        // Handle the update class action
        try {
            // Load the Remove Course FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCourse.fxml"));
            Parent removeCourseRoot = loader.load();

            // Get the current stage (window) using any node that is part of the scene
            Stage stage = (Stage) removeClassButton.getScene().getWindow(); // replace 'removeClassButton' with an actual node from your scene if needed

            // Create a new scene with the Remove Course layout
            Scene removeCourseScene = new Scene(removeCourseRoot);

            // Set the new scene on the current stage
            stage.setScene(removeCourseScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }

    @FXML
    private void onRemoveClassClick() {
        try {
            // Load the Remove Course FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RemoveCourse.fxml"));
            Parent removeCourseRoot = loader.load();

            // Get the current stage (window) using any node that is part of the scene
            Stage stage = (Stage) removeClassButton.getScene().getWindow(); // replace 'removeClassButton' with an actual node from your scene if needed

            // Create a new scene with the Remove Course layout
            Scene removeCourseScene = new Scene(removeCourseRoot);

            // Set the new scene on the current stage
            stage.setScene(removeCourseScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }

    @FXML
    protected void onReturnClick() {
        try {
            // Load the Main FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            Parent mainRoot = loader.load();

            // Get the current stage (window) from the return button
            Stage stage = (Stage) returnButton.getScene().getWindow();

            // Create a new scene with the Main layout
            Scene mainScene = new Scene(mainRoot);

            // Set the new scene on the current stage
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }

    }
}