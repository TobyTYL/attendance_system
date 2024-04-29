package edu.duke.ece651.team1.user_admin_app.controller;

import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.shared.Professor;
import edu.duke.ece651.team1.shared.User;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
//
//@ExtendWith(ApplicationExtension.class)
//public class ProfessorControllerJavaFXTest {
//
//    @Mock
//    private ProfessorDao professorDao;
//    @Mock
//    private UserDao userDao;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    private ProfessorControllerJavaFX professorController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        professorController = new ProfessorControllerJavaFX();
//        professorController.button_add_professor = new Button();
//        professorController.button_remove_professor = new Button();
//        professorController.button_back_to_professor_view = new Button();
//        professorController.button_back_to_main_view = new Button();
//        professorController.add_professor_name = new TextField();
//        professorController.remove_professor_name = new TextField();
//
//        professorController.professorDao = professorDao;
//        professorController.userDao = userDao;
//        professorController.passwordEncoder = passwordEncoder;
//    }
//
//    @Test
//    public void testAddProfessorButtonClicked() {
//        // Setup necessary state or expectations
//        // No additional setup required
//
//        // Action
//        professorController.addProfessorButtonClicked(new ActionEvent());
//
//        // Assert
//        // We expect a new scene set on the stage
//        WaitForAsyncUtils.waitForFxEvents();
//        assertTrue(professorController.button_add_professor.getScene().getWindow() instanceof Stage);
//    }
//
//    @Test
//    public void testAddProfessorConfirmButtonClicked() {
//        // Setup
//        String testUsername = "test";
//        String testPassword = "passw0rd";
//        when(userDao.addUser(new User(testUsername, passwordEncoder.encode(testPassword), "Professor"))).thenReturn(1);
//        when(professorDao.checkProfessorExists(testUsername)).thenReturn(false);
//
//        // Action
//        professorController.add_professor_name.setText(testUsername);
//        professorController.addProfessorConfirmButtonClicked(new ActionEvent());
//
//        // Assert
//        verify(userDao).addUser(any(User.class));
//        verify(professorDao).addProfessor(any(Professor.class));
//        assertEquals("", professorController.add_professor_name.getText());
//    }
//
//    @Test
//    public void testRemoveProfessorButtonClicked() {
//        // Action
//        professorController.removeProfessorButtonClicked(new ActionEvent());
//
//        // Assert
//        WaitForAsyncUtils.waitForFxEvents();
//        assertTrue(professorController.button_remove_professor.getScene().getWindow() instanceof Stage);
//    }
//
//    @Test
//    public void testRemoveProfessorConfirmButtonClicked() {
//        // Setup
//        String testUsername = "test";
//        when(userDao.findUserByUsername(testUsername)).thenReturn(new User(testUsername, "passw0rd", "Professor"));
//        when(professorDao.findProfessorByUsrID(1)).thenReturn(new Professor(1, testUsername));
//
//        // Action
//        professorController.remove_professor_name.setText(testUsername);
//        professorController.removeProfessorConfirmButtonClicked(new ActionEvent());
//
//        // Assert
//        verify(professorDao).removeProfessor(anyInt());
//        assertEquals("", professorController.remove_professor_name.getText());
//    }
//
//    @Test
//    public void testBackProfessorPageButtonClicked() {
//        // Action
//        professorController.backProfessorPageButtonClicked(new ActionEvent());
//
//        // Assert
//        WaitForAsyncUtils.waitForFxEvents();
//        assertTrue(professorController.button_back_to_professor_view.getScene().getWindow() instanceof Stage);
//    }
//
//    @Test
//    public void testBackMainPageButtonClicked() {
//        // Action
//        professorController.backMainPageButtonClicked(new ActionEvent());
//
//        // Assert
//        WaitForAsyncUtils.waitForFxEvents();
//        assertTrue(professorController.button_back_to_main_view.getScene().getWindow() instanceof Stage);
//    }
//}
