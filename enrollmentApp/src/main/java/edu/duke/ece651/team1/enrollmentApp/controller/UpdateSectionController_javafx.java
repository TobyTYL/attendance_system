package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.enrollmentApp.Model;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.shared.User;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;

public class UpdateSectionController_javafx {
    @FXML
    private ListView<String> sectionListView;
    @FXML
    private ComboBox<String> professorComboBox;
    private SectionDaoImpl sectionDao = new SectionDaoImpl();
    private ProfessorDaoImp professorDao = new ProfessorDaoImp();  
    private CourseDao courseDao = new CourseDaoImp();
    private UserDao userDao = new UserDaoImp();
    @FXML
    private void initialize() {
        Course selectedCourse = Model.getSelectedCourse();
        String courseName = selectedCourse.getName();
        int classID = courseDao.getClassIdByName(courseName);
        List<Section> sections = sectionDao.getSectionsByClassId(classID);//get all sections
        if(sections.isEmpty()){
            System.out.println("No sections available for class " + courseName);
            return;
        }

        // Populate these from a database or service
        sectionListView.setItems(FXCollections.observableArrayList(sections.stream().map(section -> {
            Integer professorID = section.getProfessorId();
            Professor professor = professorDao.getProfessorById(professorID);
            User professorUser = userDao.getUserById(professor.getUserId());
            return "Section " + section.getSectionId() + ": " + professorUser.getUsername();
        }).toList()));
        // professorComboBox.setItems(FXCollections.observableArrayList(professorDao.findAllProfessors().stream().map(professor -> {;
        //     return professorDao.getProfessorById(professor.getProfessorId()).getDisplayName();
        // })
        // .toList()));
            List<String> professorNames = new ArrayList<>();
        for (Professor professor : professorDao.findAllProfessors()) {
            User professorUser = userDao.getUserById(professor.getUserId());
            professorNames.add(professorUser.getUsername());
        }

        professorComboBox.setItems(FXCollections.observableArrayList(professorNames));
    }

    @FXML
    private void onUpdateSection() {
        String selectedSectionDetail = sectionListView.getSelectionModel().getSelectedItem();
        String selectedProfessor = professorComboBox.getSelectionModel().getSelectedItem();

        if (selectedSectionDetail != null && selectedProfessor != null) {
            try {
                // Parse the section ID and class name from the selected item
                int sectionId = parseSectionId(selectedSectionDetail);
                int professorId = professorDao.findProfessorByName(selectedProfessor).getProfessorId();

                if (sectionId != -1 && professorId != -1) {
                    // Use the existing DAO method to check if this professor is already assigned to a section of this course
                    Optional<Section> existingSection = sectionDao.findSectionByProfessorIdAndClassID(professorId, Model.getSelectedCourse().getID());
    
                    if(existingSection.isPresent()) {
                        UtilController.showAlert(Alert.AlertType.ERROR, "Update Error", "Duplicate Assignment", "Professor " + selectedProfessor + " is already teaching a section of this course.");
                        // Show an alert or some error message to the user
                    }else {
                        sectionDao.updateSectionProfessor(Model.getSelectedCourse().getName(), sectionId, professorId);
                        UtilController.showAlert(Alert.AlertType.INFORMATION, "Update Success", "Section Updated", "Updated section " + sectionId + " to have professor " + selectedProfessor);
                        System.out.println("Updated section " + sectionId + " to have professor " + selectedProfessor);
                    }
                }else {
                    UtilController.showAlert(Alert.AlertType.ERROR, "Update Error", "Invalid Selection", "Invalid section or professor selected.");
                }
            } catch (SQLException e) {
                UtilController.showAlert(Alert.AlertType.ERROR, "Database Error", "SQL Exception", "Database error: " + e.getMessage());
            } catch (NumberFormatException e) {
                UtilController.showAlert(Alert.AlertType.ERROR, "Parsing Error", "Number Format Exception", "Error parsing section ID: " + e.getMessage());
            }
        } else {
            UtilController.showAlert(Alert.AlertType.WARNING, "Update Warning", "Selection Missing", "Please select a section and a professor to update.");
        }
    }
    @FXML
    private void onReturn() {
        // Code to return to the main screen or previous screen
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
    private int parseSectionId(String sectionDetail) {
        // Assuming section details are in the format "Section 1: Prof. A"
        // Extracts the numeric part after "Section "
        try {
            String[] parts = sectionDetail.split(":")[0].trim().split(" ");
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Failed to parse section ID: " + e.getMessage());
            return -1; // Return -1 as an indication of failure
        }
    }
}
