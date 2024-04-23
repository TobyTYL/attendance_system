package edu.duke.ece651.team1.useradmin_javafx;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfessorController {
    @FXML
    private Button button_add_professor;
    @FXML
    private Button button_remove_professor;
    @FXML
    private Button button_back_to_professor_view;
    @FXML
    private Button button_back_to_main_view;
    @FXML
    private void addProfessorButtonClicked(ActionEvent event) {
        try {
            FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/enrollmentApp_javafx/Professor/add-professor-view.fxml"));
            Parent root = professorLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_add_professor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void removeProfessorButtonClicked(ActionEvent event) {
        try {
            FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/enrollmentApp_javafx/Professor/remove-professor-view.fxml"));
            Parent root = professorLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_remove_professor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void backProfessorPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader professorLoader = new FXMLLoader(getClass().getResource("/enrollmentApp_javafx/Professor/professor-view.fxml"));
            Parent root = professorLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_professor_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void backMainPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/enrollmentApp_javafx/hello-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_main_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
