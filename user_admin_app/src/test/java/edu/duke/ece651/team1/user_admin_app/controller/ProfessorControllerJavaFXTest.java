// package edu.duke.ece651.team1.user_admin_app.controller;

// import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
// import edu.duke.ece651.team1.data_access.User.UserDao;
// import edu.duke.ece651.team1.shared.Professor;
// import edu.duke.ece651.team1.shared.User;
// import javafx.application.Platform;
// import javafx.event.ActionEvent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.Pane;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.util.WaitForAsyncUtils;

// import static org.mockito.Mockito.*;

// @ExtendWith(ApplicationExtension.class)
// public class ProfessorControllerJavaFXTest {

//     @Mock private ProfessorDao professorDao;
//     @Mock private UserDao userDao;
//     @Mock private PasswordEncoder passwordEncoder;

//     private ProfessorControllerJavaFX professorController;
//     private Stage stage;

//     @BeforeEach
//     public void setUp() throws Exception {
//         MockitoAnnotations.openMocks(this);
//         Platform.runLater(() -> {
//             professorController = new ProfessorControllerJavaFX();
//             professorController.button_add_professor = new Button();
//             professorController.button_remove_professor = new Button();
//             professorController.button_back_to_professor_view = new Button();
//             professorController.button_back_to_main_view = new Button();
//             professorController.add_professor_name = new TextField();
//             professorController.remove_professor_name = new TextField();

//             stage = new Stage();
//             Scene scene = new Scene(new Pane(professorController.button_add_professor, professorController.button_remove_professor));
//             stage.setScene(scene);
//             scene.setRoot(new Pane(professorController.button_add_professor, professorController.button_remove_professor));
//             stage.show();
//         });
//     }

//     @Test
//     public void testAddProfessorButtonClicked() {
//         Platform.runLater(() -> {
//             professorController.addProfessorButtonClicked(null);
//         });
//     }
//     @Test
//     public void testRemoveProfessorButtonClicked() {
//         Platform.runLater(() -> {
//             professorController.removeProfessorButtonClicked(null);
//         });
//     }

//     @Test
//     public void testBackProfessorPageButtonClicked() {
//         Platform.runLater(() -> {
//             professorController.backProfessorPageButtonClicked(null);
//         });
//     }

//     @Test
//     public void testAddProfessorConfirmButtonClicked() {
//         Platform.runLater(() -> {
//             professorController.addProfessorConfirmButtonClicked(null);
//         });
//     }
//     @Test
//     public void testBackMainPageButtonClicked() {
//         Platform.runLater(() -> {
//             professorController.backMainPageButtonClicked(null);
//         });
//     }
//     @Test
//     public void testAddProfessorConfirmButtonClicked2() {
//         Platform.runLater(() -> {
//             String testUsername = "test";
//             String testPassword = "passw0rd";
//             when(userDao.addUser(new User(testUsername, passwordEncoder.encode(testPassword), "Professor"))).thenReturn(1);
//             when(professorDao.checkProfessorExists(testUsername)).thenReturn(false);
//             professorController.add_professor_name.setText(testUsername);
//             professorController.addProfessorConfirmButtonClicked(new ActionEvent());
//         });
//     }
//     @Test
//     public void testRemoveProfessorConfirmButtonClicked() {
//         Platform.runLater(() -> {
//             String testUsername = "test";
//             when(userDao.findUserByUsername(testUsername)).thenReturn(new User(testUsername, "passw0rd", "Professor"));
//             when(professorDao.findProfessorByUsrID(1)).thenReturn(new Professor(1, testUsername));
//             professorController.remove_professor_name.setText(testUsername);
//             professorController.removeProfessorConfirmButtonClicked(new ActionEvent());
//         });
//     }
// }
