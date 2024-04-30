package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controller class for the enrollment panel in the enrollment application.
 * This controller manages the navigation between manual enrollment, batch enrollment, and the main screen.
 */
public class EnrollmentPanelController_javafx {

    @FXML
    private Button manualEnrollButton;

    @FXML
    private Button batchEnrollButton;

    @FXML
    private Button returnButton;
    /**
     * Handles the action when the 'Manual Enrollment' button is clicked.
     * Navigates to the manual student enrollment screen.
     */
    @FXML
    protected void onManualEnrollClick() {
        // Placeholder for manual enrollment logic
        try {
            // Load the Batch Student Enroll FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManualStudentEnroll.fxml"));
            Parent batchEnrollRoot = loader.load();

            // Get the current stage (window) from one of the buttons
            Stage stage = (Stage) batchEnrollButton.getScene().getWindow();

            // Create a new scene with the Batch Student Enroll layout
            Scene batchEnrollScene = new Scene(batchEnrollRoot);

            // Set the new scene on the current stage
            stage.setScene(batchEnrollScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
     /**
     * Handles the action when the 'Batch Enrollment' button is clicked.
     * Navigates to the batch student enrollment screen.
     */
    @FXML
    protected void onBatchEnrollClick() {
        // Placeholder for batch enrollment logic
        try {
            // Load the Batch Student Enroll FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BatchStudentEnroll.fxml"));
            Parent batchEnrollRoot = loader.load();

            // Get the current stage (window) from one of the buttons
            Stage stage = (Stage) batchEnrollButton.getScene().getWindow();

            // Create a new scene with the Batch Student Enroll layout
            Scene batchEnrollScene = new Scene(batchEnrollRoot);

            // Set the new scene on the current stage
            stage.setScene(batchEnrollScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
    /**
     * Handles the action when the 'Return' button is clicked.
     * Returns to the main screen.
     */
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

    // Include any additional methods and initialization code as needed
}