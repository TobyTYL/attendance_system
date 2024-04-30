//  package edu.duke.ece651.team1.enrollmentApp.controller;

//  import javafx.application.Platform;
//  import javafx.event.ActionEvent;
//  import javafx.scene.control.Label;
//  import javafx.scene.control.TextField;
//  import javafx.stage.Stage;
//  import org.junit.jupiter.api.BeforeEach;
//  import org.junit.jupiter.api.Test;
//  import org.junit.jupiter.api.extension.ExtendWith;
//  import org.mockito.Mock;
//  import org.mockito.MockitoAnnotations;
//  import org.testfx.framework.junit5.ApplicationExtension;
//  import org.testfx.util.WaitForAsyncUtils;

//  import edu.duke.ece651.team1.data_access.Course.CourseDaoImp;
//  import edu.duke.ece651.team1.enrollmentApp.Model;
//  import edu.duke.ece651.team1.shared.Course;

//  import static org.mockito.ArgumentMatchers.anyString;
//  import static org.mockito.Mockito.*;
//  import static org.junit.jupiter.api.Assertions.*;

//  @ExtendWith(ApplicationExtension.class)
//  public class ChangeClassNameController_javafxTest {
//      @Mock
//      private CourseDaoImp mockCourseDao;
//      private ChangeClassNameController_javafx controller;

//      @BeforeEach
//      void setUp() throws Exception {
//          MockitoAnnotations.openMocks(this);
//          controller = new ChangeClassNameController_javafx();
//          controller.currentClassNameLabel = new Label();
//          controller.newClassNameField = new TextField();
//          controller.courseDao = mockCourseDao; // Ensure this assignment happens before any tests run
    
//          Course mockCourse = new Course("Old Course Name");
//          when(Model.getSelectedCourse()).thenReturn(mockCourse);
//          controller.initialize(); // Ensure this uses the mocked courseDao
//      }
    

//  //    @Test
//  //     public void testOnChangeClassNameSuccess() throws Exception {
//  //         String newClassName = "New Course Name";
//  //         when(mockCourseDao.checkCourseExists(newClassName)).thenReturn(false); // Setup stubbing

//  //         Platform.runLater(() -> {
//  //             controller.newClassNameField.setText(newClassName);
//  //             controller.onChangeClassName(new ActionEvent());
//  //         });

//  //         WaitForAsyncUtils.waitForFxEvents(); // Ensures JavaFX operations complete
//  //         verify(mockCourseDao).updateClassName("Old Course Name", newClassName);
//  //         assertEquals(newClassName, controller.currentClassNameLabel.getText());
//  //     }



//      @Test
//      public void testOnChangeClassNameFailDueToExistingName() throws Exception {
//          String newClassName = "Existing Course Name";
//          Platform.runLater(() -> {
//              controller.newClassNameField.setText(newClassName);
//              when(mockCourseDao.checkCourseExists(newClassName)).thenReturn(true);
//              controller.onChangeClassName(null);
//          });
//          Thread.sleep(100);
//          //verify(mockCourseDao, never()).updateClassName(anyString(), anyString());
//          assertEquals("Old Course Name", controller.currentClassNameLabel.getText());
//      }

//      @Test
//      public void testOnChangeClassNameEmpty() throws Exception {
//          Platform.runLater(() -> {
//              controller.newClassNameField.setText("");
//              controller.onChangeClassName(null);
//          });
//          Thread.sleep(100);
//          //verify(mockCourseDao, never()).updateClassName(anyString(), anyString());
//          assertEquals("Old Course Name", controller.currentClassNameLabel.getText());
//      }
//  }
