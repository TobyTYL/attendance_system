package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.enrollmentApp.Model;
import edu.duke.ece651.team1.shared.Course;
import static edu.duke.ece651.team1.enrollmentApp.controller.UtilController.showAlert;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class ChangeClassNameController_javafx {
    @FXML Label currentClassNameLabel;
    @FXML TextField newClassNameField;

    CourseDaoImp courseDao = new CourseDaoImp();
    // This method can be called during the initialization to set the current class name
    public void initialize() {
        Course selectedCourse = Model.getSelectedCourse();
        if (selectedCourse != null) {
            currentClassNameLabel.setText(selectedCourse.getName());
        } else {
            currentClassNameLabel.setText("No course selected");
        }
        
    }

    @FXML void onChangeClassName(ActionEvent event) {
        String oldClassName = currentClassNameLabel.getText();
        String newClassName = newClassNameField.getText();
        if (!newClassName.isEmpty()) {
            try {
                if (courseDao.checkCourseExists(newClassName)) {
                    System.out.println("The class name already exists!");
                    return;
                }
        
                courseDao.updateClassName(oldClassName, newClassName);
                // Update the selected course name in the model after successful database update
                Course updatedCourse = Model.getSelectedCourse();//also modify the course name in model
                if (updatedCourse != null) {
                    updatedCourse.setName(newClassName);
                }
                currentClassNameLabel.setText(newClassName); // Update the label to reflect the change
                showAlert(Alert.AlertType.INFORMATION, "Success", null, "Class name changed successfully!");
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Name", null, e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", null, "New class name cannot be empty");
        }
    }
    @FXML void onReturn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseChoice.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) newClassNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
