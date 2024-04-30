package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController_javafx {

    @FXML Button manageCoursesButton;

    @FXML Button enrollStudentsButton;

    @FXML Button exitButton;
    
    private ExitStrategy exitStrategy = () -> System.exit(0);
    @FXML
    protected void onManageCoursesClick() {
        // Add navigation or functionality to manage courses
        changeScene("/CourseMgmtNavi.fxml", manageCoursesButton);
        System.out.println("Manage Courses clicked");
    }

    @FXML
    protected void onEnrollStudentsClick() {
        // Add navigation or functionality to enroll students
        changeScene("/EnrollmentPanel.fxml", enrollStudentsButton);
    }

    @FXML
    protected void onExitClick() {
        exitStrategy.exit();
    }

    // Helper method to change scene
    void changeScene(String fxmlFile, Button button) {
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
      // Method to set the exit strategy
    public void setExitStrategy(ExitStrategy exitStrategy) {
        this.exitStrategy = exitStrategy;
    }
}
