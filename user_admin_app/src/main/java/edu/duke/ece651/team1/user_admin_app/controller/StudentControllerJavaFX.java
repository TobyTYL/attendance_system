package edu.duke.ece651.team1.user_admin_app.controller;

import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.Student;
import edu.duke.ece651.team1.shared.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class StudentControllerJavaFX {
    private StudentDaoImp studentDao = new StudentDaoImp();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserDao userDao = new UserDaoImp();

    @FXML
    private Button button_add_student;
    @FXML
    private Button button_remove_student;
    @FXML
    private Button button_update_student;
    @FXML
    private Button button_back_to_student_view;
    @FXML
    private Button button_back_to_main_view;
    @FXML
    private TextField add_student_legalName;
    @FXML
    private TextField add_student_display_name;
    @FXML
    private TextField add_student_email;
    @FXML
    private TextField remove_student_name;
    @FXML
    private TextField update_student_legalName;
    @FXML
    private TextField update_student_display_name;
    @FXML
    private TextField update_student_email;
    @FXML
    private void addStudentButtonClicked(ActionEvent event) {
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

    @FXML
    private void addConfirmButtonClicked(ActionEvent event) {
        String legalName = add_student_legalName.getText();
        String displayName = add_student_display_name.getText();
        String email = add_student_email.getText();
        User user = new User(legalName, passwordEncoder.encode("passw0rd"), "Student");
        int uid = userDao.addUser(user);
        Student student = new Student(uid, legalName, displayName, email);
        studentDao.addStudent(student);
        out.println("Student added successfully.");
    }
    @FXML
    private void removeStudentButtonClicked(ActionEvent event) {
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
    @FXML
    private void removeConfirmButtonClicked(ActionEvent event) {
        String legalName = remove_student_name.getText();
        Optional<Student> optionalStudent = studentDao.findStudentByName(legalName);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Integer studentId = student.getStudentId();
            if (studentId != null) {
                studentDao.removeStudent(student);
                userDao.removeUser(student.getUserId());
                out.println("Student removed successfully.");
            } else {
                out.println("Student ID is null.");
            }
        } else {
            out.println("Student not found.");
        }
    }
    @FXML
    private void updateStudentButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/update-student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_update_student.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void updateConfirmButtonClicked(ActionEvent event) {
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
                out.println("Student updated successfully.");
            } else {
                out.println("Student ID is null.");
            }
        } else {
            out.println("Student not found.");
        }
    }
    @FXML
    private void backButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/student-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_student_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backMainPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/hello-view.fxml"));
            Parent root = studentLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) button_back_to_main_view.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
