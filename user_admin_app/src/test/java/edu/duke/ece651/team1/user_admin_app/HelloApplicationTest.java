// package edu.duke.ece651.team1.user_admin_app;

// import javafx.stage.Stage;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;

// import static org.junit.jupiter.api.Assertions.*;

// public class HelloApplicationTest extends ApplicationTest {
//     private HelloApplication helloApplication;

//     @Override
//     public void start(Stage stage) throws Exception {
//         helloApplication = new HelloApplication();
//         helloApplication.start(stage);
//     }

//     @Test
//     public void testHelloApplication() {
//         assertNotNull(helloApplication);
//         Stage stage = listWindows().stream()
//                 .filter(window -> window instanceof Stage)
//                 .map(window -> (Stage) window)
//                 .findFirst()
//                 .orElse(null);
//         assertEquals("User Admin Center!", stage.getTitle());
//         assertTrue(stage.isShowing());
//         assertEquals(600, stage.getScene().getWidth());
//         assertEquals(400, stage.getScene().getHeight());
//     }

//     @Test
//     public void testMainMethod() {
//         HelloApplication helloApplication = new HelloApplication();
//     }
// }
