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

public class CourseMgmtNaviController_javafx {

    @FXML Button createClassButton;

    @FXML Button updateClassButton;

    @FXML Button removeClassButton;

    @FXML
    private Button changeClassNameButton;
    
    @FXML Button returnButton;

   @FXML void onCreateClassClick() {
        loadScene("/CreateCourse.fxml", createClassButton);
    }

    @FXML void onUpdateClassClick() {
        loadScene("/SelectCourse.fxml", updateClassButton);
    }

    @FXML void onRemoveClassClick() {
        loadScene("/RemoveCourse.fxml", removeClassButton);
    }

    @FXML void onReturnClick() {
        loadScene("/Main.fxml", returnButton);
    }

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