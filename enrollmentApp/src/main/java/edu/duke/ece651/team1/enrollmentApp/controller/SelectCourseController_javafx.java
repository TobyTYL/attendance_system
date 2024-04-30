package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.enrollmentApp.Model;
import edu.duke.ece651.team1.shared.Course;
import static edu.duke.ece651.team1.enrollmentApp.controller.UtilController.showAlert;
/**
 * Controller class responsible for handling user interactions related to selecting a course.
 * Allows users to choose a course from a ComboBox and navigate to the course-specific functionalities.
 */
public class SelectCourseController_javafx {
    @FXML
    private ComboBox<String> classComboBox;
    private CourseDao courseDao = new CourseDaoImp(); //
    private List<Course> courses;
    /**
     * Initializes the ComboBox with course names from the database.
     */
    @FXML
    private void initialize() {
        // Populate the ComboBox with class names from the database
        List<Course> courses = courseDao.getAllCourses();
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }
        classComboBox.setItems(FXCollections.observableArrayList(courseNames));
    }

     /**
     * Handles the action of selecting a course from the ComboBox.
     * Sets the selected course in the Model and loads the Course Choice view.
     */
    @FXML
    private void onSelectClass() {
        String selectedClass = classComboBox.getSelectionModel().getSelectedItem();
        if (selectedClass != null) {
            for (Course course : courseDao.getAllCourses()) {
                if (course.getName().equals(selectedClass)) {
                    Model.setSelectedCourse(course);
                    break;
                }
            }
            System.out.println("Selected Class: " + selectedClass);
            loadScene("/CourseChoice.fxml");
        }
    }

    /**
     * Handles the action of clicking the "Return" button.
     * Returns to the Course Management Navigation view.
     */
    @FXML
    private void onReturn() {
        loadScene("/CourseMgmtNavi.fxml");
    }
    /**
     * Loads the specified FXML file and sets it as the scene of the stage.
     * 
     * @param fxmlPath The path of the FXML file to load.
     */
    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) classComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Error", null, "Failed to load the screen: " + e.getMessage());
        }
    }
}
