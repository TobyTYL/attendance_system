package edu.duke.ece651.team1.javafx_test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloController {
    @FXML
    private Button button_manage_student;
    @FXML
    private Button button_manage_professor;

    @FXML
    private void manageStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/Student/student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_manage_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void manageProfessorButtonClicked (ActionEvent event){
        try {
            FXMLLoader professor_loader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/Professor/professor-view.fxml"));
            Parent root = professor_loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_manage_professor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}