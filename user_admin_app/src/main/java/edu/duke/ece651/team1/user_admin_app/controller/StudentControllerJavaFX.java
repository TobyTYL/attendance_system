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
//    @FXML
//    void backButtonClicked(ActionEvent event) {
//        try {
//            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/Student/student-view.fxml"));
//            Parent root = studentLoader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) button_back_to_student_view.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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

//    @FXML
//    void backMainPageButtonClicked(ActionEvent event) {
//        try {
//            FXMLLoader studentLoader = new FXMLLoader(getClass().getResource("/edu.duke.ece651.team1.user_admin_app/hello-view.fxml"));
//            Parent root = studentLoader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) button_back_to_main_view.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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
