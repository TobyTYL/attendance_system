package edu.duke.ece651.team1.enrollmentApp_javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class BatchStudentEnrollController {

    @FXML
    private TextField userInputText;

    @FXML
    private Button importButton;

    @FXML
    private TextArea importResult;

    @FXML
    private MenuItem returnButton;

    // This method will be called when the 'Import' button is clicked.
    @FXML
    protected void onImportClick() {
        String fileName = userInputText.getText();
        // Here you will put your logic to process the CSV file.
        // For now, it just displays a placeholder message.
        importResult.setText("Processing file: " + fileName);
        // Implement the CSV import logic and update the result in the TextArea.
    }

    // This method will be called when the 'Return' option in the menu is selected.
    @FXML
    protected void onReturnClick() {
        try {
            // Load the Enrollment Panel FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnrollmentPanel.fxml"));
            Parent enrollmentPanelRoot = loader.load();

            // Get the current Scene from any Node that is part of the scene, in this case, the MenuBar itself
            Scene currentScene = returnButton.getParentPopup().getOwnerWindow().getScene();
            // Get the Stage from the Scene
            Stage stage = (Stage) currentScene.getWindow();

            // Create a new scene with the Enrollment Panel layout
            Scene enrollmentPanelScene = new Scene(enrollmentPanelRoot);

            // Set the new scene on the current stage
            stage.setScene(enrollmentPanelScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }

    // You can add more methods if needed for additional functionality.
}
