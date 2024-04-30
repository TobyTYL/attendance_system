// ////package edu.duke.ece651.team1.user_admin_app.controller;
// ////
// ////import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
// ////import edu.duke.ece651.team1.data_access.User.UserDao;
// ////import edu.duke.ece651.team1.shared.Student;
// ////import edu.duke.ece651.team1.shared.User;
// ////import javafx.application.Platform;
// ////import javafx.event.ActionEvent;
// ////import javafx.scene.Scene;
// ////import javafx.scene.control.Button;
// ////import javafx.scene.control.TextField;
// ////import javafx.scene.layout.Pane;
// ////import javafx.stage.Stage;
// ////import org.junit.jupiter.api.BeforeEach;
// ////import org.junit.jupiter.api.Test;
// ////import org.junit.jupiter.api.extension.ExtendWith;
// ////import org.mockito.Mock;
// ////import org.mockito.Mockito;
// ////import org.mockito.MockitoAnnotations;
// ////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// ////import org.springframework.security.crypto.password.PasswordEncoder;
// ////import org.testfx.framework.junit5.ApplicationExtension;
// ////import org.testfx.util.WaitForAsyncUtils;
// ////
// ////import java.util.Optional;
// ////
// ////import static org.junit.jupiter.api.Assertions.assertAll;
// ////import static org.junit.jupiter.api.Assertions.assertEquals;
// ////import static org.mockito.Mockito.*;
// ////
// ////@ExtendWith(ApplicationExtension.class)
// ////public class StudentControllerJavaFXTest {
// ////
// ////    @Mock private StudentDaoImp studentDao;
// ////    @Mock private UserDao userDao;
// ////    @Mock private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
// ////
// ////    private StudentControllerJavaFX controller;
// ////    private Stage mockStage;
// ////    private Scene mockScene;
// ////    private Stage stage;
// ////
// ////    @BeforeEach
// ////    public void setUp() throws Exception {
// ////        MockitoAnnotations.openMocks(this);
// ////        Platform.runLater(() -> {
// ////            controller = Mockito.spy(new StudentControllerJavaFX()); // Create a spy of your controller
// ////
// ////        // Mock the JavaFX components
// ////        mockStage = mock(Stage.class);
// ////        mockScene = mock(Scene.class);
// ////        when(mockStage.getScene()).thenReturn(mockScene);
// ////
// ////        // Ensure that the buttons and text fields are instantiated
// ////        controller.button_add_student = new Button();
// ////        controller.button_remove_student = new Button();
// ////        controller.button_update_student = new Button();
// ////        controller.button_back_to_student_view = new Button();
// ////        controller.button_back_to_main_view = new Button();
// ////        controller.add_student_legalName = new TextField();
// ////        controller.add_student_display_name = new TextField();
// ////        controller.add_student_email = new TextField();
// ////        controller.remove_student_name = new TextField();
// ////        controller.update_student_legalName = new TextField();
// ////        controller.update_student_display_name = new TextField();
// ////        controller.update_student_email = new TextField();
// ////
// ////        Platform.runLater(() -> {
// ////            Pane pane = new Pane(controller.button_add_student, controller.button_remove_student);
// ////            mockScene.setRoot(pane);
// ////            mockStage.setScene(mockScene);
// ////            mockStage.show();
// ////        });
// ////    }
// ////    @Test
// ////    public void testAddStudentButtonClicked() {
// ////        Platform.runLater(() -> {
// ////            controller.addStudentButtonClicked(null);
// ////        });
// ////    }
// ////
// ////
// ////    @Test
// ////    public void testAddConfirmButtonClicked() {
// ////        controller.add_student_legalName.setText("teststudentlegalname");
// ////        controller.add_student_display_name.setText("teststudentdisplayname");
// ////        controller.add_student_email.setText("9999@gmail.com");
// ////
// ////        when(userDao.addUser(any(User.class))).thenReturn(1);
// ////        when(studentDao.checkStudentExists("teststudentlegalname")).thenReturn(false);
// ////
// ////        Platform.runLater(() -> {
// ////            controller.addConfirmButtonClicked(null);
// ////        });
// ////
// ////    }
// ////    @Test
// ////    public void testBackButtonClicked1() {
// ////        Platform.runLater(() -> {
// ////            controller.backButtonClicked(null);
// ////        });
// ////    }
// ////
// ////    @Test
// ////    public void testUpdateStudentButtonClicked() {
// ////        Platform.runLater(() -> {
// ////            controller.updateStudentButtonClicked(null);
// ////        });
// ////    }
// ////    @Test
// ////    public void testUpdateConfirmButtonClicked() {
// ////        String legalName = "teststudentlegalname";
// ////        controller.update_student_legalName.setText(legalName);
// ////        controller.update_student_display_name.setText("Johnny");
// ////        controller.update_student_email.setText("8888@gmail.com");
// ////        Optional<Student> foundStudent = Optional.of(new Student(1, legalName, "JohnD", "john@example.com"));
// ////        when(studentDao.findStudentByName(legalName)).thenReturn(foundStudent);
// ////
// ////        Platform.runLater(() -> {
// ////            controller.updateConfirmButtonClicked(new ActionEvent());
// ////        });
// ////    }
// ////    @Test
// ////    public void testRemoveStudentButtonClicked() {
// ////        Platform.runLater(() -> {
// ////            controller.removeStudentButtonClicked(null);
// ////        });
// ////    }
// ////    @Test
// ////    public void testRemoveConfirmButtonClicked() {
// ////        controller.remove_student_name.setText("teststudentlegalname");
// ////        Optional<Student> optStudent = Optional.of(new Student(1, "teststudentlegalname", "test", "777@gmail.com"));
// ////        when(studentDao.findStudentByName("teststudentlegalname")).thenReturn(optStudent);
// ////        Platform.runLater(() -> {
// ////            controller.removeConfirmButtonClicked(new ActionEvent());
// ////        });
// ////    }
// ////
// ////
// ////
// ////
// ////    @Test
// ////    public void testBackButtonClicked() {
// ////        Platform.runLater(() -> {
// ////            controller.backButtonClicked(null);
// ////        });
// ////    }
// ////
// ////    @Test
// ////    public void testBackMainPageButtonClicked() {
// ////        Platform.runLater(() -> {
// ////            controller.backMainPageButtonClicked(null);
// ////        });
// ////    }
// ////}
// //
// //package edu.duke.ece651.team1.user_admin_app.controller;
// //import edu.duke.ece651.team1.data_access.Student.StudentDao;
// //import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
// //import edu.duke.ece651.team1.data_access.User.UserDao;
// //import edu.duke.ece651.team1.shared.Student;
// //import edu.duke.ece651.team1.shared.User;
// //import javafx.application.Platform;
// //import javafx.event.ActionEvent;
// //import javafx.scene.Scene;
// //import javafx.scene.control.Button;
// //import javafx.scene.control.TextField;
// //import javafx.scene.layout.Pane;
// //import javafx.stage.Stage;
// //import org.junit.jupiter.api.BeforeEach;
// //import org.junit.jupiter.api.Test;
// //import org.junit.jupiter.api.extension.ExtendWith;
// //import org.mockito.Mock;
// //import org.mockito.Mockito;
// //import org.mockito.MockitoAnnotations;
// //import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// //import org.springframework.security.crypto.password.PasswordEncoder;
// //import org.testfx.framework.junit5.ApplicationExtension;
// //import org.testfx.util.WaitForAsyncUtils;
// //
// //import java.util.Optional;
// //
// //import static org.junit.jupiter.api.Assertions.assertAll;
// //import static org.junit.jupiter.api.Assertions.assertEquals;
// //import static org.mockito.Mockito.*;
// //
// //@ExtendWith(ApplicationExtension.class)
// //public class StudentControllerJavaFXTest {
// //
// //    @Mock private StudentDaoImp studentDao;
// //    @Mock private UserDao userDao;
// //    @Mock private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
// //    private StudentControllerJavaFX controller;
// //    private Stage stage;
// //
// //
// //    @BeforeEach
// //    public void setUp() throws Exception {
// //        MockitoAnnotations.openMocks(this);
// //        Platform.runLater(() -> {
// //            controller = Mockito.spy(new StudentControllerJavaFX()); // Create a spy of your controller
// //            controller.button_add_student = new Button();
// //            controller.button_remove_student = new Button();
// //            controller.button_update_student = new Button();
// //            controller.button_back_to_student_view = new Button();
// //            controller.button_back_to_main_view = new Button();
// //            controller.add_student_legalName = new TextField();
// //            controller.add_student_display_name = new TextField();
// //            controller.add_student_email = new TextField();
// //            controller.remove_student_name = new TextField();
// //            controller.update_student_legalName = new TextField();
// //            controller.update_student_display_name = new TextField();
// //            controller.update_student_email = new TextField();
// //            stage = new Stage();
// //            Scene scene = new Scene(new Pane(controller.button_add_student, controller.button_remove_student));
// //            stage.setScene(scene);
// //            scene.setRoot(new Pane(controller.button_add_student, controller.button_remove_student));
// //            stage.show();
// //
// //        });
// //        WaitForAsyncUtils.waitForFxEvents();
// //    }
// //        @Test
// //        public void testAddStudentButtonClicked() {
// //            Platform.runLater(() -> {
// //                controller.addStudentButtonClicked(null);
// //            });
// //        }
// //
// //    @Test
// //    public void testRemoveStudentButtonClicked() {
// //        Platform.runLater(() -> {
// //            controller.removeStudentButtonClicked(null);
// //        });
// //    }
// //        @Test
// //        public void testAddConfirmButtonClicked() {
// //            controller.add_student_legalName.setText("teststudentlegalname");
// //            controller.add_student_display_name.setText("teststudentdisplayname");
// //            controller.add_student_email.setText("9999@gmail.com");
// //
// //            when(userDao.addUser(any(User.class))).thenReturn(1);
// //            when(studentDao.checkStudentExists("teststudentlegalname")).thenReturn(false);
// //
// //            Platform.runLater(() -> {
// //                controller.addConfirmButtonClicked(null);
// //            });
// //
// //        }
// //        @Test
// //        public void testBackButtonClicked1() {
// //            Platform.runLater(() -> {
// //                controller.backButtonClicked(null);
// //            });
// //        }
// //
// //        @Test
// //        public void testUpdateStudentButtonClicked() {
// //            Platform.runLater(() -> {
// //                controller.updateStudentButtonClicked(null);
// //            });
// //        }
// //        @Test
// //        public void testUpdateConfirmButtonClicked() {
// //            String legalName = "teststudentlegalname";
// //            controller.update_student_legalName.setText(legalName);
// //            controller.update_student_display_name.setText("Johnny");
// //            controller.update_student_email.setText("8888@gmail.com");
// //            Optional<Student> foundStudent = Optional.of(new Student(1, legalName, "JohnD", "john@example.com"));
// //            when(studentDao.findStudentByName(legalName)).thenReturn(foundStudent);
// //
// //            Platform.runLater(() -> {
// //                controller.updateConfirmButtonClicked(new ActionEvent());
// //            });
// //        }
// //
// //        @Test
// //        public void testRemoveConfirmButtonClicked() {
// //            controller.remove_student_name.setText("teststudentlegalname");
// //            Optional<Student> optStudent = Optional.of(new Student(1, "teststudentlegalname", "test", "777@gmail.com"));
// //            when(studentDao.findStudentByName("teststudentlegalname")).thenReturn(optStudent);
// //            Platform.runLater(() -> {
// //                controller.removeConfirmButtonClicked(new ActionEvent());
// //            });
// //        }
// //
// //
// //
// //
// //        @Test
// //        public void testBackButtonClicked() {
// //            Platform.runLater(() -> {
// //                controller.backButtonClicked(null);
// //            });
// //        }
// //
// //        @Test
// //        public void testBackMainPageButtonClicked() {
// //            Platform.runLater(() -> {
// //                controller.backMainPageButtonClicked(null);
// //            });
// //        }
// //    }

// package edu.duke.ece651.team1.user_admin_app.controller;

// import edu.duke.ece651.team1.data_access.Student.StudentDao;
// import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
// import edu.duke.ece651.team1.data_access.User.UserDao;
// import edu.duke.ece651.team1.shared.Student;
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
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.util.WaitForAsyncUtils;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.assertAll;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// @ExtendWith(ApplicationExtension.class)
// public class StudentControllerJavaFXTest {

//     @Mock private StudentDaoImp studentDao;
//     @Mock private UserDao userDao;
//     @Mock private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//     private StudentControllerJavaFX controller;
//     private Stage stage;


//     @BeforeEach
//     public void setUp() throws Exception {
//         MockitoAnnotations.openMocks(this);
//         Platform.runLater(() -> {
//             controller = new StudentControllerJavaFX();
//             controller.button_add_student = new Button();
//             controller.button_remove_student = new Button();
//             controller.button_update_student = new Button();
//             controller.button_back_to_student_view = new Button();
//             controller.button_back_to_main_view = new Button();
//             controller.add_student_legalName = new TextField();
//             controller.add_student_display_name = new TextField();
//             controller.add_student_email = new TextField();
//             controller.remove_student_name = new TextField();
//             controller.update_student_legalName = new TextField();
//             controller.update_student_display_name = new TextField();
//             controller.update_student_email = new TextField();
//             stage = new Stage();
//             Scene scene = new Scene(new Pane(controller.button_add_student, controller.button_remove_student));
//             stage.setScene(scene);
//             stage.show();
//         });
//         WaitForAsyncUtils.waitForFxEvents();
//     }

//     @Test
//     public void testAddStudentButtonClicked() {
//         Platform.runLater(() -> {
//             controller.addStudentButtonClicked(null);
//         });
//         WaitForAsyncUtils.waitForFxEvents();

//     }

//     @Test
//     public void testRemoveStudentButtonClicked() {
//         Platform.runLater(() -> {
//             controller.removeStudentButtonClicked(null);
//         });
//         WaitForAsyncUtils.waitForFxEvents();
//     }

//     @Test
//     public void testAddConfirmButtonClicked() {
//         controller.add_student_legalName.setText("teststudentlegalname");
//         controller.add_student_display_name.setText("teststudentdisplayname");
//         controller.add_student_email.setText("9999@gmail.com");

//         when(userDao.addUser(any(User.class))).thenReturn(1);
//         when(studentDao.checkStudentExists("teststudentlegalname")).thenReturn(false);

//         Platform.runLater(() -> {
//             controller.addConfirmButtonClicked(null);
//         });
//         WaitForAsyncUtils.waitForFxEvents();

//     }


//     @Test
//     public void testUpdateStudentButtonClicked() {
//         Platform.runLater(() -> {
//             controller.updateStudentButtonClicked(null);
//         });
//         WaitForAsyncUtils.waitForFxEvents();

//     }

//     @Test
//     public void testUpdateConfirmButtonClicked() {
//         String legalName = "teststudentlegalname";
//         controller.update_student_legalName.setText(legalName);
//         controller.update_student_display_name.setText("Johnny");
//         controller.update_student_email.setText("8888@gmail.com");
//         Optional<Student> foundStudent = Optional.of(new Student(1, legalName, "JohnD", "john@example.com"));
//         when(studentDao.findStudentByName(legalName)).thenReturn(foundStudent);

//         Platform.runLater(() -> {
//             controller.updateConfirmButtonClicked(new ActionEvent());
//         });
//         WaitForAsyncUtils.waitForFxEvents();

//     }

//     @Test
//     public void testRemoveConfirmButtonClicked() {
//         controller.remove_student_name.setText("teststudentlegalname");
//         Optional<Student> optStudent = Optional.of(new Student(1, "teststudentlegalname", "test", "777@gmail.com"));
//         when(studentDao.findStudentByName("teststudentlegalname")).thenReturn(optStudent);
//         Platform.runLater(() -> {
//             controller.removeConfirmButtonClicked(new ActionEvent());
//         });
//         WaitForAsyncUtils.waitForFxEvents();

//     }

//     @Test
//     public void testBackButtonClicked() {
//         Platform.runLater(() -> {
//             controller.backButtonClicked(null);
//         });
//         WaitForAsyncUtils.waitForFxEvents();

//     }

//     @Test
//     public void testBackMainPageButtonClicked() {
//         Platform.runLater(() -> {
//             controller.backMainPageButtonClicked(null);
//         });
//         WaitForAsyncUtils.waitForFxEvents();
//     }
// }
