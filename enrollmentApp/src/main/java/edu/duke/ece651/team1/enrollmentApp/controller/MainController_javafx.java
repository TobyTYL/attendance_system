package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controller class for the main screen of the enrollment application.
 * This controller manages navigation to the course management and student enrollment screens,
 * as well as handling the exit action.
 */
public class MainController_javafx {

    @FXML Button manageCoursesButton;

    @FXML Button enrollStudentsButton;

    @FXML Button exitButton;
    
    private ExitStrategy exitStrategy = () -> System.exit(0);
    /**
     * Handles the action when the 'Manage Courses' button is clicked.
     * Navigates to the course management panel.
     */
    @FXML
    protected void onManageCoursesClick() {
        // Add navigation or functionality to manage courses
        changeScene("/CourseMgmtNavi.fxml", manageCoursesButton);
        System.out.println("Manage Courses clicked");
    }
    /**
     * Handles the action when the 'Enroll Students' button is clicked.
     * Navigates to the enrollment panel.
     */
    @FXML
    protected void onEnrollStudentsClick() {
        // Add navigation or functionality to enroll students
        changeScene("/EnrollmentPanel.fxml", enrollStudentsButton);
    }
    /**
     * Handles the action when the 'Exit' button is clicked.
     * Executes the exit strategy to gracefully exit the application.
     */
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
     /**
     * Sets the exit strategy for the application.
     * @param exitStrategy The exit strategy to be set.
     */
    public void setExitStrategy(ExitStrategy exitStrategy) {
        this.exitStrategy = exitStrategy;
    }
}
