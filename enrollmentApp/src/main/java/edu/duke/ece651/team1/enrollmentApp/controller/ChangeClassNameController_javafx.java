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
/**
 * Controller class for changing the class name in the enrollment application.
 * This controller manages the interaction between the user interface and the CourseDao implementation.
 */
public class ChangeClassNameController_javafx {
    @FXML Label currentClassNameLabel;
    @FXML TextField newClassNameField;

    CourseDaoImp courseDao = new CourseDaoImp();
    /**
     * Initializes the controller.
     * Sets the current class name label with the name of the selected course.
     */
    public void initialize() {
        Course selectedCourse = Model.getSelectedCourse();
        if (selectedCourse != null) {
            currentClassNameLabel.setText(selectedCourse.getName());
        } else {
            currentClassNameLabel.setText("No course selected");
        }
        
    }
    /**
     * Handles the action when the 'Change Class Name' button is clicked.
     * Changes the class name of the selected course to the new name entered by the user.
     * Shows appropriate alerts for success or failure.
     * @param event The action event triggered by clicking the 'Change Class Name' button.
     */
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
    /**
     * Handles the action when the 'Return' button is clicked.
     * Returns to the course choice screen.
     */
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
