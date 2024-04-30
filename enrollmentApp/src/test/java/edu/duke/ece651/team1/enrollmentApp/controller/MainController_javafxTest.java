// package edu.duke.ece651.team1.enrollmentApp.controller;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.framework.junit5.ApplicationTest;
// import org.testfx.util.WaitForAsyncUtils;
// import static org.junit.Assert.assertNull;

// import edu.duke.ece651.team1.enrollmentApp.MainApp;
// import edu.duke.ece651.team1.enrollmentApp.controller.*;
// import javafx.scene.input.KeyCode;
// import javafx.scene.input.MouseButton;
// import javafx.scene.layout.Pane;

// import static org.testfx.assertions.api.Assertions.assertThat;
// import static org.testfx.matcher.base.NodeMatchers.isVisible;

// import java.security.Permission;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.testfx.api.FxAssert.verifyThat;

// import javafx.application.Platform;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.framework.junit5.ApplicationTest;
// import org.testfx.api.FxRobot;
// import org.testfx.api.FxToolkit;
// import java.io.IOException;

// @ExtendWith(ApplicationExtension.class)
// public class MainController_javafxTest extends ApplicationTest {
//    private MainController_javafx mainController;
//    private Stage stage;


//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        Platform.runLater(() -> {
//            mainController = Mockito.spy(new MainController_javafx());
//            mainController.manageCoursesButton = new Button();
//            mainController.enrollStudentsButton = new Button();
//            mainController.exitButton = new Button();

//            stage = new Stage();
//            stage.show();
//            stage.toFront();
//        });
//    }

//    @Test
//    public void testOnManageCoursesClick() {
//        Platform.runLater(() -> {
//            // Simulate adding the button to a scene
//            Scene scene = new Scene(new Pane(mainController.manageCoursesButton));
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();

//            // Perform the action
//            mainController.onManageCoursesClick();

//            // Assert that the scene's window is not null after clicking the button
//            //assertNull(mainController.manageCoursesButton.getScene().getWindow());
//        });
//        //WaitForAsyncUtils.waitForFxEvents();
//    }

//   @Test
//    public void testOnEnrollStudentsClick() {
//        Platform.runLater(() -> {
//            // Simulate adding the button to a scene
//            Scene scene = new Scene(new Pane(mainController.enrollStudentsButton));
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();

//            // Perform the action
//            mainController.onEnrollStudentsClick();

//            // Assert that the scene's window is not null after clicking the button
//            //assertNull(mainController.enrollStudentsButton.getScene().getWindow());
//        });
//        //WaitForAsyncUtils.waitForFxEvents();
//    }

//    // @Test
//    // public void testOnExitClick() {
//    //     Platform.runLater(() -> {
//    //         // Perform the action
//    //         mainController.onExitClick();
//    //     });
//    //     WaitForAsyncUtils.waitForFxEvents();
//    // }

// //    @Test
// //    public void testChangeScene() {
// //        Platform.runLater(() -> {
// //            // Simulate adding the button to a scene
// //            Scene scene = new Scene(new Pane(mainController.manageCoursesButton));
// //            Stage stage = new Stage();
// //            stage.setScene(scene);
// //            stage.show();
// //
// //            // Perform the action
// //            mainController.changeScene("/CourseMgmtNavi.fxml", mainController.manageCoursesButton);
// //
// //            // Assert any relevant post-action state
// //        });
// //        //WaitForAsyncUtils.waitForFxEvents();
// //    }
// private void setupSceneAndStage() {
//    Scene scene = new Scene(new Pane(mainController.manageCoursesButton));
//    Stage stage = new Stage();
//    stage.setScene(scene);
//    stage.show();
// }

//    @Test
//    public void testChangeScene() {
//        Platform.runLater(() -> {
//            setupSceneAndStage();

//            // Perform the action
//            mainController.changeScene("/CourseMgmtNavi.fxml", mainController.manageCoursesButton);

//            // Assert any relevant post-action state
//        });
//    }
// }
