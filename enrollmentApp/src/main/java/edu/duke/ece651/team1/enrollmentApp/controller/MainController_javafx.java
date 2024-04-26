package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController_javafx {

    @FXML
    private Button manageCoursesButton;

    @FXML
    private Button enrollStudentsButton;

    @FXML
    private Button exitButton;

    @FXML
    protected void onManageCoursesClick() {
        // Add navigation or functionality to manage courses
        changeScene("/CourseMgmtNavi.fxml", manageCoursesButton);
    }

    @FXML
    protected void onEnrollStudentsClick() {
        // Add navigation or functionality to enroll students
        changeScene("/EnrollmentPanel.fxml", enrollStudentsButton);
    }

    @FXML
    protected void onExitClick() {
        System.exit(0);
    }

    // Helper method to change scene
    private void changeScene(String fxmlFile, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
}
