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
    @FXML ComboBox<String> professorComboBox;

    private ProfessorDao professorDao;
    private SectionDao sectionDao;
    private CourseDao courseDao;
    private UserDao userDao;
    // Constructor that accepts DAOs
    /**
     * Default constructor that initializes DAOs with default implementations.
     */
    public AddSectionController_javafx() {
        this.professorDao = new ProfessorDaoImp();
        this.sectionDao = new SectionDaoImpl();
        this.courseDao = new CourseDaoImp();
        this.userDao = new UserDaoImp();
    }
    /**
     * Constructor that accepts DAOs as parameters.
     * @param professorDao The ProfessorDao implementation.
     * @param sectionDao The SectionDao implementation.
     * @param courseDao The CourseDao implementation.
     * @param userDao The UserDao implementation.
     */
    public AddSectionController_javafx(ProfessorDao professorDao, SectionDao sectionDao, CourseDao courseDao, UserDao userDao) {
        this.professorDao = professorDao;
        this.sectionDao = sectionDao;
        this.courseDao = courseDao;
        this.userDao = userDao;
    }
    /**
     * Initializes the controller.
     * Populates the comboBox with professors retrieved from the database.
     */
    @FXML void initialize() {
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
    /**
     * Handles the action when the user clicks the "Add Section" button.
     * Attempts to add a new section to the selected course with the selected professor.
     * Shows appropriate alerts for success or failure.
     */
    @FXML void onAddSection() {
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
    /**
     * Handles the action when the user clicks the "Return" button.
     * Returns to the main screen or the previous screen.
     */
    @FXML void onReturn() {
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
