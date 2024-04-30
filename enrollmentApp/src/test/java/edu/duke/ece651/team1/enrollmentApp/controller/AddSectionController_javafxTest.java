// package edu.duke.ece651.team1.enrollmentApp.controller;
// import javafx.application.Platform;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.ComboBox;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.framework.junit5.Start;

// import edu.duke.ece651.team1.data_access.Course.CourseDao;
// import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
// import edu.duke.ece651.team1.data_access.Section.SectionDao;
// import edu.duke.ece651.team1.data_access.User.UserDao;
// import edu.duke.ece651.team1.enrollmentApp.Model;
// import edu.duke.ece651.team1.shared.Course;
// import edu.duke.ece651.team1.shared.Professor;
// import edu.duke.ece651.team1.shared.Section;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;
// import static org.testfx.api.FxAssert.verifyThat;
// import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;

// import java.util.Arrays;

// @ExtendWith(ApplicationExtension.class)
// public class AddSectionController_javafxTest {

//    @Mock private ProfessorDao professorDao;
//    @Mock private UserDao userDao;
//    @Mock private SectionDao sectionDao;
//    @Mock private CourseDao courseDao;

//    private AddSectionController_javafx controller;
//    private Stage stage;

//    @Start
//    private void start(Stage stage) {
//        this.stage = stage;
//    }

//    @BeforeEach
//    void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//        Platform.runLater(() -> {
//            // Setup scene and stage
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddSection.fxml"));
//            loader.setControllerFactory(clazz -> new AddSectionController_javafx(professorDao, sectionDao, courseDao, userDao));

//            try {
//                Parent root = loader.load();
//                controller = loader.getController();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        controller = new AddSectionController_javafx(professorDao, sectionDao, courseDao, userDao);
//        controller.professorComboBox = new ComboBox<>();
//    }

//    @Test
//    void testInitialize() {
//        Professor professor = new Professor(1, "John Doe");
//        when(professorDao.findAllProfessors()).thenReturn(Arrays.asList(professor));
//        when(userDao.getUserById(1)).thenReturn(new edu.duke.ece651.team1.shared.User("John Doe", "password", "Professor"));

//        Platform.runLater(() -> {
//            controller.initialize();
//            verify(professorDao).findAllProfessors();
//            verify(userDao).getUserById(1);
//            verifyThat(controller.professorComboBox, hasItems(1));
//        });
//    }

//    @Test
//    void testOnAddSection() {
//        Platform.runLater(() -> {
//            when(professorDao.findProfessorByName("John Doe")).thenReturn(new Professor(1, "John Doe"));
//            when(courseDao.getClassIdByName("CS101")).thenReturn(101);
//            when(sectionDao.existsSectionWithProfessor(101, 1)).thenReturn(false);
//            //when(sectionDao.addSection(any(Section.class))).thenReturn(true);

//            // Set up environment to test onAddSection
//            Model.setSelectedCourse(new edu.duke.ece651.team1.shared.Course("CS101"));
//            controller.professorComboBox.getSelectionModel().select("John Doe");

//            controller.onAddSection();
//            //verify(sectionDao).addSection(any(Section.class));
//        });
//    }

//    @Test
//    void testOnReturn() {
//        Platform.runLater(() -> {
//            controller.onReturn();
//            // Assertions to verify that the scene has changed, potentially by checking the loader's state
//        });
//    }


//    @Test
//    void testAddSectionExceptionHandling() {
//        Platform.runLater(() -> {
//            when(professorDao.findProfessorByName(anyString())).thenThrow(new RuntimeException("Database error"));

//            controller.professorComboBox.getSelectionModel().select("John Doe");
//            assertThrows(RuntimeException.class, () -> controller.onAddSection());
//            // Verify that an error alert was shown
//        });
//    }


// }
