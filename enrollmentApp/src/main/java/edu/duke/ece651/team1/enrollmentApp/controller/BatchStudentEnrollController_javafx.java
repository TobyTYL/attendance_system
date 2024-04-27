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

public class BatchStudentEnrollController_javafx {

    @FXML
    private TextField userInputText;

    @FXML
    private Button importButton;

    @FXML
    private Label importResult;

    @FXML
    private Button returnButton;

    // This method will be called when the 'Import' button is clicked.
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
            importResult.setText("File not found: " + filePath);
            return;
        } catch (IOException e) {
            importResult.setText("Error reading file: " + filePath);
            return;
        }
        importResult.setText("Batch enrollment process completed.\n");
        if (!errors.isEmpty()) {
            StringBuilder errorDetails = new StringBuilder("Error details:\n");
            errors.forEach(error -> errorDetails.append(error).append("\n"));
            importResult.setText(errorDetails.toString());
        }
    }



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
