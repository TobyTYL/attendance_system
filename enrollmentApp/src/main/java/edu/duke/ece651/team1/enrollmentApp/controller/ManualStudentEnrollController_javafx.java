package edu.duke.ece651.team1.enrollmentApp.controller;

import edu.duke.ece651.team1.data_access.Course.CourseDao;
import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;


import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.Course;
//import edu.duke.ece651.team1.enrollmentApp.controller.CourseController;
import edu.duke.ece651.team1.enrollmentApp.controller.EnrollmentController;
import edu.duke.ece651.team1.shared.Enrollment;

import edu.duke.ece651.team1.shared.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static java.lang.System.out;
/**
 * Controller class for manual student enrollment in the enrollment application.
 * This controller manages the manual enrollment process, including looking up students,
 * finding available sections, and enrolling students in sections.
 */
public class ManualStudentEnrollController_javafx {
   private StudentDao studentDao = new StudentDaoImp();

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
    /**
    * Initializes the controller.
    * This method is automatically called when the FXML file is loaded.
    */
   public void initialize() {
       // Placeholder text to display at instantiation time
   }
    /**
    * Handles the action when the 'Lookup Student' button is clicked.
    * Looks up the student by ID and displays available courses.
    * @param event The action event triggered by clicking the button.
    */
   @FXML
   protected void onLookupStudentClick(ActionEvent event) {
       int studentID;
       try {
           studentID = Integer.parseInt(inputStudentID.getText());
       } catch (NumberFormatException e) {
           UtilController.showAlert(Alert.AlertType.ERROR, "Invalid Input", null, "Please enter a valid student ID.");
           return;
       }
       if (!studentExists(studentID)) {
           UtilController.showAlert(Alert.AlertType.WARNING, "Student Not Found", null, "Student with ID " + studentID + " not found in the database.");
           availCourses.setText(""); // Clear the availCourses TextArea

           return;
       }
       List<Course> availableCourses = courseDao.getAllCourses();
       if (availableCourses.isEmpty()) {
           UtilController.showAlert(Alert.AlertType.WARNING, "No Available Courses", null, "There are no available courses.");
           availCourses.setText("");
           return;
       }
       displayAllCoursesForStudent(studentID);
   }

   private boolean studentExists(int studentID) {
       return studentDao.findStudentByStudentID(studentID).isPresent();
   }

   private void displayAllCoursesForStudent(int studentId) {
       List<Course> availableCourses = courseDao.getAllCourses();
       StringBuilder coursesText = new StringBuilder();
       for (Course course : availableCourses) {
           coursesText.append("ID: ").append(course.getID()).append(", Name: ").append(course.getName()).append("\n");
       }
       availCourses.setText(coursesText.toString());
   }
   /**
    * Handles the action when the 'Find Section' button is clicked.
    * Finds sections for the given course and displays them.
    * @param event The action event triggered by clicking the button.
    */
   @FXML
   protected void onFindSecClick(ActionEvent event) {
       String courseName = inputCourseName.getText();
       if (!checkClassExist(courseName)) {
           UtilController.showAlert(Alert.AlertType.WARNING, "Class Name Not Found", null, "Class Name is not found in the database.");
           availSections.setText("");
           return;
       }
       int classID = courseDao.getClassIdByName(courseName);
       if (classID == -1) {
           UtilController.showAlert(Alert.AlertType.WARNING, "Invalid Input", null, "Please enter a valid course name.");
           return;
       }
       StringBuilder sectionsText = new StringBuilder();
       List <Section> sections = sectionDao.getSectionsByClassId(classID);
       if (sections.isEmpty()) {
           UtilController.showAlert(Alert.AlertType.WARNING, "No Available Sections", null, "There are no available sections for the selected course.");
           availSections.setText("");
           return;
       }
       for (Section section : sections) {
           sectionsText.append("Section ID: ").append(section.getSectionId()).append(", Course Name: ").append(inputCourseName.getText()).append(", Professor ID: ").append(section.getProfessorId()).append("\n");
       }
       availSections.setText(sectionsText.toString());
   }
   private boolean checkClassExist(String className) {
       return courseDao.checkCourseExists(className);
   }
//    private boolean checkClassExist(int studentID) {
//        return studentDao.findStudentByStudentID(studentID).isPresent();
//    }
/**
    * Handles the action when the 'Enroll' button is clicked.
    * Enrolls the student in the selected section.
    * @param event The action event triggered by clicking the button.
    */
   @FXML
   protected void onEnrollClick(ActionEvent event) {
       String sectionIdStr = inputSecID.getText();
       int sectionId;
       try {
           sectionId = Integer.parseInt(sectionIdStr);
       } catch (NumberFormatException e) {
           enrollResult.setText("Invalid section ID format.");
           UtilController.showAlert(Alert.AlertType.ERROR, "Invalid Input", null, "Please enter a valid section ID.");
           return;
       }
       String studentIdStr = inputStudentID.getText();
       int studentId;
       try {
           studentId = Integer.parseInt(studentIdStr);
       } catch (NumberFormatException e) {
           UtilController.showAlert(Alert.AlertType.ERROR, "Invalid Input", null, "Please enter a valid student ID.");
           return;
       }
       if (!sectionDao.checkSectionExists(sectionId)) {
           UtilController.showAlert(Alert.AlertType.ERROR, "Section Not Found", null, "Section with ID " + sectionId + " does not exist.");
           return;
       }
       EnrollmentController enrollmentController = new EnrollmentController(new BufferedReader(new StringReader("")), System.out);
       boolean success = enrollmentController.enrollStudent(studentId, sectionId);
       if (success) {
           UtilController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Enrollment successful!");
       } else {
           UtilController.showAlert(Alert.AlertType.WARNING, "Enrollment Failed", null, "Student with ID " + studentId + " is already enrolled in this section.");
       }

   }
    /**
    * Handles the action when the 'Return' button is clicked.
    * Returns to the enrollment panel.
    * @param actionEvent The action event triggered by clicking the button.
    */
   @FXML
   protected void onReturnClick(ActionEvent actionEvent) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnrollmentPanel.fxml")); // Replace with actual FXML filename
           Parent root = loader.load();
           Stage stage = (Stage) returnButton.getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
