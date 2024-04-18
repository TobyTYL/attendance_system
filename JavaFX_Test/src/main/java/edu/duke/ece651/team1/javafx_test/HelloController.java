package edu.duke.ece651.team1.javafx_test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane manageStudentButton;
//    @FXML
//    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML

    private void manageStudentButtonClicked (ActionEvent event){
        try {
            FXMLLoader student_loader = new FXMLLoader(getClass().getResource("student-view.fxml"));
            Parent root = student_loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) manageStudentButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}