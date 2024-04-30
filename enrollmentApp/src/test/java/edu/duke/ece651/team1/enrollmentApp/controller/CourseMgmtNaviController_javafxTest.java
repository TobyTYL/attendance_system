// package edu.duke.ece651.team1.enrollmentApp.controller;
// import javafx.application.Platform;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
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

// import java.io.IOException;

// import static org.mockito.Mockito.*;

// @ExtendWith(ApplicationExtension.class)
// public class CourseMgmtNaviController_javafxTest {

//    @Mock
//    private FXMLLoader mockLoader;
//    @Mock
//    private Parent mockParent;

//    private CourseMgmtNaviController_javafx controller;
//    private Stage stage;

//    @BeforeEach
//    public void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//        when(mockLoader.load()).thenReturn(mockParent);

//        Platform.runLater(() -> {
//            controller = new CourseMgmtNaviController_javafx();
//            stage = new Stage();
//            Pane root = new Pane(); // Using Pane as a simple container

//            // Setup buttons
//            controller.createClassButton = new Button("Create Class");
//            controller.updateClassButton = new Button("Update Class");
//            controller.removeClassButton = new Button("Remove Class");
//            controller.returnButton = new Button("Return");

//            // Add buttons to the root pane
//            root.getChildren().addAll(controller.createClassButton, controller.updateClassButton,
//                                      controller.removeClassButton, controller.returnButton);

//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        });
//    }

//    @Test
//    public void testOnCreateClassClick() {
//        Platform.runLater(() -> {
//            controller.onCreateClassClick();
//            //verify(controller).loadScene("/CreateCourse.fxml", controller.createClassButton);
//        });
//    }

//    @Test
//    public void testOnUpdateClassClick() {
//        Platform.runLater(() -> {
//            controller.onUpdateClassClick();
//            //verify(controller).loadScene("/SelectCourse.fxml", controller.updateClassButton);
//        });
//    }

//    @Test
//    public void testOnRemoveClassClick() {
//        Platform.runLater(() -> {
//            controller.onRemoveClassClick();
//            //verify(controller).loadScene("/RemoveCourse.fxml", controller.removeClassButton);
//        });
//    }

//    @Test
//    public void testOnReturnClick() {
//        Platform.runLater(() -> {
//            controller.onReturnClick();
//            //verify(controller).loadScene("/Main.fxml", controller.returnButton);
//        });
//    }
// }
