package edu.duke.ece651.team1.javafx_test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentController {
    @FXML
    private Button button_add_student;
    @FXML
    private Button button_remove_student;
    @FXML
    private Button button_update_student;
    @FXML
    private Button button_back_to_student_view;
    @FXML
    private Button button_back_to_main_view;
    @FXML
    private void addStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/Student/add-student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_add_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void removeStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/Student/remove-student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_remove_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void updateStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/Student/update-student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_update_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void backButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/Student/student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_student_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backMainPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu/duke/ece651/team1/javafx_test/hello-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_main_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
