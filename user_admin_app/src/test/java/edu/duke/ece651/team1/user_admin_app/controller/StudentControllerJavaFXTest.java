package edu.duke.ece651.team1.user_admin_app.controller;

import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.shared.Student;
import edu.duke.ece651.team1.shared.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class StudentControllerJavaFXTest {

    @Mock private StudentDaoImp studentDao;
    @Mock private UserDao userDao;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private StudentControllerJavaFX controller;
    private Stage stage;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Platform.runLater(() -> {
            controller = new StudentControllerJavaFX();
            controller.button_add_student = new Button();
            controller.button_remove_student = new Button();
            controller.button_update_student = new Button();
            controller.button_back_to_student_view = new Button();
            controller.button_back_to_main_view = new Button();
            controller.add_student_legalName = new TextField();
            controller.add_student_display_name = new TextField();
            controller.add_student_email = new TextField();
            controller.remove_student_name = new TextField();
            controller.update_student_legalName = new TextField();
            controller.update_student_display_name = new TextField();
            controller.update_student_email = new TextField();

            Pane pane = new Pane(controller.button_add_student, controller.button_remove_student);
            Scene scene = new Scene(pane);
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testAddStudentButtonClicked() {
        Platform.runLater(() -> {
            controller.addStudentButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testRemoveStudentButtonClicked() {
        Platform.runLater(() -> {
            controller.removeStudentButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testAddConfirmButtonClicked() {
        controller.add_student_legalName.setText("teststudentlegalname");
        controller.add_student_display_name.setText("teststudentdisplayname");
        controller.add_student_email.setText("9999@gmail.com");

        when(userDao.addUser(any(User.class))).thenReturn(1);
        when(studentDao.checkStudentExists("teststudentlegalname")).thenReturn(false);

        Platform.runLater(() -> {
            controller.addConfirmButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();

        verify(studentDao).addStudent(any(Student.class));
    }

    @Test
    public void testUpdateConfirmButtonClicked() {
        String legalName = "teststudentlegalname";
        controller.update_student_legalName.setText(legalName);
        controller.update_student_display_name.setText("Johnny");
        controller.update_student_email.setText("8888@gmail.com");
        Optional<Student> foundStudent = Optional.of(new Student(1, legalName, "JohnD", "john@example.com"));
        when(studentDao.findStudentByName(legalName)).thenReturn(foundStudent);

        Platform.runLater(() -> {
            controller.updateConfirmButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();

        verify(studentDao).updateStudent(any(Student.class));
        assertAll(
                () -> assertEquals("", controller.update_student_legalName.getText()),
                () -> assertEquals("", controller.update_student_display_name.getText()),
                () -> assertEquals("", controller.update_student_email.getText())
        );
    }
    @Test
    public void testRemoveConfirmButtonClicked() {
        controller.remove_student_name.setText("teststudentlegalname");
        Optional<Student> optStudent = Optional.of(new Student(1, "toby test", "test", "777@gmail.com"));

        // Mock the database interactions
        when(studentDao.findStudentByName("teststudentlegalname")).thenReturn(optStudent);
        Platform.runLater(() -> {
            controller.removeConfirmButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testUpdateStudentButtonClicked() {
        Platform.runLater(() -> {
            controller.updateStudentButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();
        // Assertions to verify the scene was changed
    }

    @Test
    public void testBackButtonClicked() {
        Platform.runLater(() -> {
            controller.backButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testBackMainPageButtonClicked() {
        Platform.runLater(() -> {
            controller.backMainPageButtonClicked(new ActionEvent());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}