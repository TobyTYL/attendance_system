package edu.duke.ece651.team1.enrollmentApp.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UtilController {
    public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
