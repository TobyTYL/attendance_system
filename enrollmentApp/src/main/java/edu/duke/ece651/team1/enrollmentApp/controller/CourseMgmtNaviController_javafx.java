package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import static edu.duke.ece651.team1.enrollmentApp.controller.UtilController.showAlert;
import java.io.IOException;
/**
 * Controller class for navigation within the course management section of the enrollment application.
 * This controller manages the navigation between different screens for creating, updating, and removing courses.
 */
public class CourseMgmtNaviController_javafx {

    @FXML Button createClassButton;

    @FXML Button updateClassButton;

    @FXML Button removeClassButton;

    @FXML
    private Button changeClassNameButton;
    
    @FXML Button returnButton;
    /**
     * Loads the screen for creating a new course.
     */
   @FXML void onCreateClassClick() {
        loadScene("/CreateCourse.fxml", createClassButton);
    }
      /**
     * Loads the screen for updating an existing course.
     */
    @FXML void onUpdateClassClick() {
        loadScene("/SelectCourse.fxml", updateClassButton);
    }
    /**
     * Loads the screen for removing a course.
     */
    @FXML void onRemoveClassClick() {
        loadScene("/RemoveCourse.fxml", removeClassButton);
    }
    /**
     * Handles the action when the 'Return' button is clicked.
     * Returns to the main screen.
     */
    @FXML void onReturnClick() {
        loadScene("/Main.fxml", returnButton);
    }

    /**
     * Loads the specified FXML scene.
     * @param fxmlPath The path to the FXML file.
     * @param buttonContext The button context used for retrieving the current window.
     */
    void loadScene(String fxmlPath, Button buttonContext) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) buttonContext.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Loading Error", null, "Failed to load the screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}