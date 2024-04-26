package edu.duke.ece651.team1.enrollmentApp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.enrollmentApp.Model;
import edu.duke.ece651.team1.shared.Section;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.shared.Professor;
import java.util.List;
import java.util.ArrayList;
import static edu.duke.ece651.team1.enrollmentApp.controller.UtilController.showAlert;

import java.io.IOException;

import javafx.collections.FXCollections;

public class AddSectionController_javafx {
    @FXML
    private ComboBox<String> professorComboBox;

    private ProfessorDao professorDao = new ProfessorDaoImp(); 
    private SectionDao sectionDao = new SectionDaoImpl();
    private CourseDao courseDao = new CourseDaoImp();
    private UserDao userDao = new UserDaoImp();

    @FXML
    private void initialize() {
        // Populate the comboBox with professors (this should come from a database or similar source)
        List<Professor> professors = professorDao.findAllProfessors();
        List<String> profNames = new ArrayList<>();

        for (Professor prof : professors) {
            int userId = prof.getUserId();
            String professorName = userDao.getUserById(userId).getUsername();

            profNames.add(professorName);
        }
        professorComboBox.setItems(FXCollections.observableArrayList(profNames));
    }

    @FXML
    private void onAddSection() {
        String professorName = professorComboBox.getSelectionModel().getSelectedItem();
        try {
            Course selectedCourse = Model.getSelectedCourse();
            String courseName = selectedCourse.getName();
            Professor professor = professorDao.findProfessorByName(professorName);
            if (professor == null) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Professor not found.");
                return;
            }
            int professorId = professor.getProfessorId();
            int classId = courseDao.getClassIdByName(courseName);
            if (sectionDao.existsSectionWithProfessor(classId, professorId)) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "This professor already teaches a section of this course.");
                return;
            }
            Section section = new Section(classId, professorId);
            sectionDao.addSection(section);
            showAlert(Alert.AlertType.INFORMATION, "Success", null, "Section added successfully!");
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", null, "Failed to add the section to the database.");
        }
    }

    @FXML
    private void onReturn() {
        // Code to return to the main screen or previous screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseChoice.fxml"));
            Stage stage = (Stage) professorComboBox.getScene().getWindow();
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
