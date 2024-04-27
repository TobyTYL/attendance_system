package edu.duke.ece651.team1.enrollmentApp.controller;

import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;


import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.shared.Course;
import edu.duke.ece651.team1.enrollmentApp.controller.CourseController;
import edu.duke.ece651.team1.enrollmentApp.controller.EnrollmentController;
import edu.duke.ece651.team1.shared.Enrollment;

import edu.duke.ece651.team1.shared.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static java.lang.System.out;

public class ManualStudentEnrollController_javafx {
    private CourseDao courseDao = new CourseDaoImp();
    private SectionDao sectionDao = new SectionDaoImpl();
    private EnrollmentDaoImpl enrollmentDao = new EnrollmentDaoImpl();

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
    private Label enrollResult;

    @FXML
    private TextField inputStudentID;

    public void initialize() {
        // Placeholder text to display at instantiation time
    }

    @FXML
    protected void onLookupStudentClick(ActionEvent event) {
        int studentID = Integer.parseInt(inputStudentID.getText());
        displayAllCoursesForStudent(studentID);

    }
    private void displayAllCoursesForStudent(int studentId) {
        List<Course> availableCourses = courseDao.getAllCourses();
        StringBuilder coursesText = new StringBuilder();
        for (Course course : availableCourses) {
            coursesText.append("ID: ").append(course.getID()).append(", Name: ").append(course.getName()).append("\n");
        }
        availCourses.setText(coursesText.toString());
    }

    @FXML
    protected void onFindSecClick(ActionEvent event) {
        String courseName = inputCourseName.getText();
        int classID = courseDao.getClassIdByName(courseName);
        StringBuilder sectionsText = new StringBuilder();
        List <Section> sections = sectionDao.getSectionsByClassId(classID);
        for (Section section : sections) {
            sectionsText.append("Section ID: ").append(section.getSectionId()).append(", Course ID: ").append(section.getClassId()).append(", Professor ID: ").append(section.getProfessorId()).append("\n");
        }
        availSections.setText(sectionsText.toString());
    }

    @FXML
    protected void onEnrollClick(ActionEvent event) {
        String sectionIdStr = inputSecID.getText();
        int sectionId;
        try {
            sectionId = Integer.parseInt(sectionIdStr);
        } catch (NumberFormatException e) {
            enrollResult.setText("Invalid section ID format.");
            return;
        }
        String studentIdStr = inputStudentID.getText();
        int studentId;
        try {
            studentId = Integer.parseInt(studentIdStr);
        } catch (NumberFormatException e) {
            enrollResult.setText("Invalid student ID format.");
            return;
        }
        if (!sectionDao.checkSectionExists(sectionId)) {
            enrollResult.setText("Section with ID " + sectionId + " does not exist.");
            return;
        }
        EnrollmentController enrollmentController = new EnrollmentController(new BufferedReader(new StringReader("")), System.out);
        boolean success = enrollmentController.enrollStudent(studentId, sectionId);
        if (success) {
            enrollResult.setText("Student with ID " + studentId + " successfully enrolled in section with ID " + sectionId + ".");
        } else {
            enrollResult.setText("Student with ID " + studentId + " already in this section. Failed to enroll student with ID " + studentId + " in section with ID " + sectionId + ".");
        }
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
