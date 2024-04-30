package edu.duke.ece651.team1.user_admin_app.controller;

import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.Student;
import edu.duke.ece651.team1.shared.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.IOException;
import java.util.Optional;

import static java.lang.System.out;
/**
 * Controller class for managing student-related operations in the JavaFX user admin application.
 */
public class StudentControllerJavaFX {
    private StudentDaoImp studentDao = new StudentDaoImp();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserDao userDao = new UserDaoImp();
    @FXML
    Button button_add_student;
    @FXML
    Button button_remove_student;
    @FXML
    Button button_update_student;
    @FXML
    Button button_back_to_student_view;
    @FXML
    Button button_back_to_main_view;
    @FXML
    TextField add_student_legalName;
    @FXML
    TextField add_student_display_name;
    @FXML
    TextField add_student_email;
    @FXML
    TextField remove_student_name;
    @FXML
    TextField update_student_legalName;
    @FXML
    TextField update_student_display_name;
    @FXML
    TextField update_student_email;
    /**
     * Event handler for clicking the add student button.
     * Loads the add student view.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void addStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/add-student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_add_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Add appropriate Javadoc comments for the rest of the methods as well.

    /**
     * Event handler for confirming the addition of a student.
     * Adds a new student and associated user to the database.
     * Displays success or error message accordingly.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void addConfirmButtonClicked(ActionEvent event) {
        String legalName = add_student_legalName.getText();
        String displayName = add_student_display_name.getText();
        String email = add_student_email.getText();
        User user = new User(legalName, passwordEncoder.encode("passw0rd"), "Student");
        int uid = userDao.addUser(user);
        Student student = new Student(uid, legalName, displayName, email);
        if (studentDao.checkStudentExists(legalName)) {
            AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Student already exists!");
            add_student_legalName.setText("");
            add_student_display_name.setText("");
            add_student_email.setText("");
            return;
        }
        studentDao.addStudent(student);
        AlertWindowController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Student add successful!");
        add_student_legalName.setText("");
        add_student_display_name.setText("");
        add_student_email.setText("");
    }

    /**
     * Event handler for clicking the button to remove a student.
     * Loads the remove student view.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void removeStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/remove-student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_remove_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for confirming the removal of a student.
     * Retrieves the student name from the text field, finds the associated student in the database,
     * removes the student and associated user from the database if found, and displays success or error message accordingly.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void removeConfirmButtonClicked(ActionEvent event) {
        String legalName = remove_student_name.getText();
        Optional<Student> optionalStudent = studentDao.findStudentByName(legalName);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Integer studentId = student.getStudentId();
            if (studentId != null) {
                studentDao.removeStudent(student);
                userDao.removeUser(student.getUserId());
                AlertWindowController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Student remove successful!");
                remove_student_name.setText("");
            } else {
                AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Student ID is null!");
                remove_student_name.setText("");
            }
        } else {
            AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Can't find the Student Name!");
            remove_student_name.setText("");
        }
    }
//    @FXML
//    void updateStudentButtonClicked(ActionEvent event) {
//        try {
//            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/update-student-view.fxml"));
//            Parent root = studentLoader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) button_update_student.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Event handler for the 'Update Student' button click.
     * This method initiates the process of updating student information in the application.
     * It first retrieves the scene associated with the button clicked to ensure that it's part of a valid UI structure.
     * If the scene exists, it then attempts to retrieve the stage, which is the top-level JavaFX container holding this scene.
     * Once the stage is obtained, it attempts to load the FXML file for updating student details and sets it as the current scene on the stage,
     * effectively changing the display to the update student view.
     * If any part of this process fails, such as if the scene or stage cannot be retrieved, or the FXML file cannot be loaded due to an IOException,
     * an error is printed to the console.
     *
     * @param event the ActionEvent that triggered the method call, which provides information about the event and its source
     */

    @FXML
    void updateStudentButtonClicked(ActionEvent event) {
        Scene buttonScene = button_update_student.getScene();
        if (buttonScene != null) {
            Stage stage = (Stage) buttonScene.getWindow();
            if (stage != null) {
                try {
                    FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/update-student-view.fxml"));
                    Parent root = studentLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Stage is null!");
            }
        } else {
            System.err.println("Scene is null!");
        }
    }
    /**
     * Handles the confirmation action for updating student information.
     * When the 'Update Confirm' button is clicked, this method attempts to update the student's details.
     * It retrieves the student's legal name entered in the update_student_legalName field,
     * searches for the student in the database, and if found, updates the student's display name and email.
     * A success alert is shown if the update is successful. If the student ID is not found or is null,
     * an error alert is displayed. All text fields are cleared after the operation.
     *
     * @param event the ActionEvent object representing the click event
     */

    @FXML
    void updateConfirmButtonClicked(ActionEvent event) {
        String legalName = update_student_legalName.getText();
        Optional<Student> optionalStudent = studentDao.findStudentByName(legalName);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Integer studentId = student.getStudentId();
            if (studentId != null) {
                String newDisplayName = update_student_display_name.getText();
                String newEmail = update_student_email.getText();
                student.updateDisplayName(newDisplayName);
                student.setStudentEmail(newEmail);
                studentDao.updateStudent(student);
                AlertWindowController.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Student update successful!");
                update_student_legalName.setText("");
                update_student_display_name.setText("");
                update_student_email.setText("");
            } else {
                AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Student ID is null!");
                update_student_legalName.setText("");
                update_student_display_name.setText("");
                update_student_email.setText("");
            }
        } else {
            AlertWindowController.showAlert(Alert.AlertType.ERROR, "Error", null, "Can't find the Student ID!");
            update_student_legalName.setText("");
            update_student_display_name.setText("");
            update_student_email.setText("");
        }
    }

    /**
     * Event handler for the 'Back' button click.
     * When the 'Back' button is clicked, this method controls the navigation by taking the user back to the student view.
     * It first checks whether the button is part of a scene and retrieves the stage the scene belongs to.
     * Then, it loads the student-view FXML document, creating a new scene for the student view layout.
     * If the loading is successful, the scene on the current stage is set to the student view scene,
     * effectively changing the displayed content.
     * In case of failure during the process, such as not being able to retrieve the scene or stage,
     * or an error when loading the FXML, an error message is printed to the standard error stream.
     *
     * @param event the ActionEvent that contains details about the event that triggered this method
     */

    @FXML
    void backButtonClicked(ActionEvent event) {
        Scene buttonScene = button_back_to_student_view.getScene();
        if (buttonScene != null) {
            Stage stage = (Stage) buttonScene.getWindow();
            if (stage != null) {
                try {
                    FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/student-view.fxml"));
                    Parent root = studentLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Stage is null!");
            }
        } else {
            System.err.println("Scene is null!");
        }
    }

    /**
     * Event handler for the 'Back to Main Page' button click.
     * This method manages the UI flow for returning to the main page of the application.
     * It starts by retrieving the scene that the 'Back to Main Page' button belongs to, ensuring the button is placed within a scene.
     * If the scene is not null, it then fetches the stage, which is the window containing this scene.
     * Provided the stage is valid, it proceeds to load the main view layout from the 'hello-view.fxml' file.
     * A new scene is created with this layout and set on the current stage, effectively navigating the user back to the main page.
     * If any part of this process encounters an issue, such as the scene or stage being null, or an IOException during FXML loading,
     * an appropriate error message is printed to the console to aid in debugging.
     *
     * @param event the ActionEvent that triggered the method, carrying information about the click event.
     */

    @FXML
    void backMainPageButtonClicked(ActionEvent event) {
        Scene buttonScene = button_back_to_main_view.getScene();
        if (buttonScene != null) {
            Stage stage = (Stage) buttonScene.getWindow();
            if (stage != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/hello-view.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Stage is null!");
            }
        } else {
            System.err.println("Scene is null!");
        }
    }


}
