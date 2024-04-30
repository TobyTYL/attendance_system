package edu.duke.ece651.team1.enrollmentApp.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**
 * Utility class for displaying alert messages in the JavaFX application.
 */
public class UtilController {
    /**
     * Displays an alert dialog with the specified parameters.
     * 
     * @param alertType The type of alert (e.g., INFORMATION, WARNING, ERROR).
     * @param title The title of the alert dialog.
     * @param header The header text of the alert dialog.
     * @param content The content text of the alert dialog.
     */
    public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
