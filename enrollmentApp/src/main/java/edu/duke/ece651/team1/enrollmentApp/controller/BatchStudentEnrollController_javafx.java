package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Controller class for batch student enrollment in the enrollment application.
 * This controller manages the interaction between the user interface and the data access objects (DAOs).
 */
public class BatchStudentEnrollController_javafx {

    @FXML TextField userInputText;

    @FXML Button importButton;

    @FXML Label importResult;

    @FXML Button returnButton;

    // This method will be called when the 'Import' button is clicked.
     /**
     * Handles the action when the 'Import' button is clicked.
     * Reads the specified CSV file and processes student enrollment.
     * Shows appropriate alerts for success or failure.
     * @param actionEvent The action event triggered by clicking the 'Import' button.
     */
    @FXML
    protected void onImportClick(ActionEvent actionEvent) {
        String csvFileName = userInputText.getText();
        String basePath = System.getProperty("user.dir");
        String filePath = basePath + "/src/test/resources/" + csvFileName;
        List<String> errors = new ArrayList<>();
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            UtilController.showAlert(Alert.AlertType.ERROR, "File Not Found", null, "The specified file was not found: " + filePath);
            return;
        } catch (IOException e) {
            UtilController.showAlert(Alert.AlertType.ERROR, "File Error", null, "An error occurred while reading the file: " + filePath);
            return;
        }
        if (!errors.isEmpty()) {
            UtilController.showAlert(Alert.AlertType.ERROR, "Import Error", null, "Errors occurred during the import process.");
        } else {
            UtilController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Batch enrollment process completed successfully.");
            userInputText.clear();
        }
    }

     /**
     * Handles the action when the 'Return' button is clicked.
     * Returns to the main enrollment panel.
     * @param actionEvent The action event triggered by clicking the 'Return' button.
     */
    @FXML
    protected void onReturnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnrollmentPanel.fxml")); // Replace with actual FXML filename
            Parent root = loader.load();
            Stage stage = (Stage) returnButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
