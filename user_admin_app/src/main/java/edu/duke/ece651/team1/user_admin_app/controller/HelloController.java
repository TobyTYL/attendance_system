package edu.duke.ece651.team1.user_admin_app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloController {
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    @FXML
    Button button_manage_student;
    @FXML
    Button button_manage_professor;

//    @FXML
//    public void manageStudentButtonClicked(ActionEvent event) {
//        try {
//            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/student-view.fxml"));
//            Parent root = studentLoader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) button_manage_student.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    public void manageStudentButtonClicked(ActionEvent event) {

        Scene buttonScene = button_manage_student.getScene();
        if (buttonScene != null) {
            Stage stage = (Stage) buttonScene.getWindow();
            if (stage != null) {
                try {
                    FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/student-view.fxml"));
                    Parent root = studentLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Stage is null!");
            }
        } else {
            System.err.println("Scene is null!");
        }
    }

    @FXML
    public void manageProfessorButtonClicked (ActionEvent event){
        try {
            FXMLLoader professor_loader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Professor/professor-view.fxml"));
            Parent root = professor_loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_manage_professor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}