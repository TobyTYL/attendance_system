package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ManualStudentEnrollController_javafx {
    @FXML
    private TextField studentQueryResult;
    @FXML
    private Button returnButton;

    @FXML
    private TextField inputSecID;

    @FXML
    private TextField inputCourseName;

    @FXML
    private TextArea availSections;

    @FXML
    private Button findSecButton;

    @FXML
    private Button lookupStudentButton;

    @FXML
    private TextArea availCourses;

    @FXML
    private Button enrollButton;

    @FXML
    private TextField enrollResult;

    @FXML
    private TextField inputStudentID;

    public void initialize() {
        // Placeholder text to display at instantiation time
        availCourses.setText("Before any operations, call DB and display available courses to the user");
    }

    @FXML
    protected void onLookupStudentClick(ActionEvent event) {
        // Display text from "Insert student ID" in "Student query result"
        studentQueryResult.setText("call DB and look for student: " + inputStudentID.getText());
    }

    @FXML
    protected void onFindSecClick(ActionEvent event) {
        // Display text from "Insert course name" in "Available Sections"
        availSections.setText("look for course:" + inputCourseName.getText());
    }

    @FXML
    protected void onEnrollClick(ActionEvent event) {
        // Inject text from "Insert section id" into "Enroll result"
        enrollResult.setText("show enrollment results for stuID, CourseID and SecID: " + inputSecID.getText());
    }

    @FXML
    protected void onReturnClick(ActionEvent actionEvent) {
        try {
            // Load the previous panel's FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnrollmentPanel.fxml")); // Replace with actual FXML filename
            Parent root = loader.load();

            // Get the current stage from the return button
            Stage stage = (Stage) returnButton.getScene().getWindow();

            // Create a new scene with the root layout
            Scene scene = new Scene(root);

            // Set the new scene on the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate
        }
    }
}
