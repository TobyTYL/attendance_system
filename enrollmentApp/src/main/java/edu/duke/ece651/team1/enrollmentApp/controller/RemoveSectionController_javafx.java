package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.enrollmentApp.Model;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.shared.User;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**
 * Controller responsible for removing sections from a course.
 * Allows the user to select a section from a list and remove it from the course.
 */
public class RemoveSectionController_javafx {
    @FXML
    private ListView<String> sectionListView;
    private SectionDaoImpl sectionDao = new SectionDaoImpl();
    private CourseDaoImp courseDao = new CourseDaoImp();
    private ProfessorDaoImp professorDao = new ProfessorDaoImp();
    private UserDaoImp userDao = new UserDaoImp();
     /**
     * Initializes the controller. Retrieves sections associated with the selected course
     * and populates the ListView with section details.
     */
    @FXML
    private void initialize() {
        Course selectedCourse = Model.getSelectedCourse();
    if (selectedCourse == null) {
        System.out.println("No course selected.");
        return;
    }

    String courseName = selectedCourse.getName();
    int classID = courseDao.getClassIdByName(courseName);
    List<Section> sections = sectionDao.getSectionsByClassId(classID); //get all sections
    if (sections.isEmpty()) {
        System.out.println("No sections available for class " + courseName);
        // Handle the case where no sections are available, e.g., by disabling the remove button
        return;
    }

    // Map sections to string representations to display in the ListView
    List<String> sectionDetails = sections.stream().map(section -> {
        Integer professorID = section.getProfessorId();
        Professor professor = professorDao.getProfessorById(professorID);
        User professorUser = userDao.getUserById(professor.getUserId());
        return "Section " + section.getSectionId() + ": " + professorUser.getUsername();
    }).collect(Collectors.toList());

    sectionListView.setItems(FXCollections.observableArrayList(sectionDetails));
    }
    /**
     * Handles the action of removing a selected section from the course.
     */
    @FXML
    private void onRemoveSection() {
         String selectedSection = sectionListView.getSelectionModel().getSelectedItem();
    if (selectedSection == null || selectedSection.isEmpty()) {
        showAlert("No Section Selected", "Please select a section to remove.");
        return;
    }

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + selectedSection + "?", ButtonType.YES, ButtonType.NO);
    confirm.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            // Extract section ID from the selected item
            int sectionId = parseSectionId(selectedSection);
            if (sectionId != -1) {
                // Remove the section
                try {
                    sectionDao.deleteSection(sectionId);
                    System.out.println("Removed " + selectedSection);
                    // Refresh the list or indicate success to the user
                    initialize(); // Call initialize to refresh the list, or implement a dedicated refresh method
                } catch (Exception e) {
                    System.out.println("Database error: " + e.getMessage());
                    showAlert("Database Error", "Failed to remove the section: " + e.getMessage());
                }
            } else {
                showAlert("Parsing Error", "Failed to parse the section ID.");
            }
        }
    });
    }
     /**
     * Handles the action of returning to the previous screen.
     */
     @FXML
    private void onReturn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseChoice.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) sectionListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Parses the section ID from the section detail string.
     * 
     * @param sectionDetail The string containing section details.
     * @return The parsed section ID.
     */
    private int parseSectionId(String sectionDetail) {
        try {
            return Integer.parseInt(sectionDetail.replaceFirst("Section ", "").split(":")[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing section ID: " + e.getMessage());
            return -1;
        }
    }
    /**
     * Displays an alert with the specified title and content.
     * 
     * @param title   The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
