// package edu.duke.ece651.team1.user_admin_app.controller;

// import javafx.application.Platform;
// import javafx.event.ActionEvent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.Pane;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.util.WaitForAsyncUtils;

// import static org.mockito.Mockito.*;

// @ExtendWith(ApplicationExtension.class)
// public class HelloControllerTest {

//     @Mock
//     private Stage stage;

//     private HelloController helloController;

//     @BeforeEach
//     public void setUp() throws Exception {
//         MockitoAnnotations.openMocks(this);
//         Platform.runLater(() -> {
//             helloController = new HelloController();
//             helloController.button_manage_student = new Button();
//             helloController.button_manage_professor = new Button();
//             Scene scene = new Scene(new Pane(helloController.button_manage_professor, helloController.button_manage_student));
//             stage = new Stage();
//             stage.setScene(scene);
//             stage.show();

//             helloController.setScene(scene);
//         });
//     }

//     @Test
//     public void testManageStudentButtonClicked() {
//         Platform.runLater(() -> {
//             // 模拟一个合适的 ActionEvent 对象
//             ActionEvent event = mock(ActionEvent.class);
//             // 将模拟的事件对象传递给方法
//             helloController.manageStudentButtonClicked(event);
//         });
//         WaitForAsyncUtils.waitForFxEvents();
//     }


//     @Test
//     public void testManageProfessorButtonClicked() {
//         Platform.runLater(() -> {
//             helloController.manageProfessorButtonClicked(new ActionEvent(new Object(), null));
//         });
//         WaitForAsyncUtils.waitForFxEvents();
//     }
// }
