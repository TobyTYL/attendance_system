// package edu.duke.ece651.team1.client.controller;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.mockito.MockedStatic;
// import org.mockito.Mockito;

// import edu.duke.ece651.team1.client.view.AttendanceView;
// import edu.duke.ece651.team1.client.view.CourseView;
// import edu.duke.ece651.team1.client.view.MainMenuView;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;
// import edu.duke.ece651.team1.client.model.*;

// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// public class MainMenuControllerTest {
//     @Mock
//     private BufferedReader inputReader;

//     @Mock
//     private PrintStream out;


//     private ProfessorMainMenuController mainMenuController;
//     @Mock
//     private UserSession userSession;

//     @BeforeEach
//     void setUp() {
//         // Configure default behaviors for the mocked mainMenuView
//         int sectionId = 1;
//         int classId = 1;
//         String className = "ECE101";
//         userSession.setHost("localhost");
//         userSession.setPort("8080");
//         mainMenuController = new ProfessorMainMenuController(sectionId, classId, className, inputReader, out);
//     }



//     @Test
//     void testAttendanceOption_back() throws IOException {
//         when(inputReader.readLine()).thenReturn("1", "5", "2", "2");
//         mainMenuController.startMainMenu();
//         verify(out).println("1. Take attendance for today's class");
//         verify(out).println("2. Edit your previous attendance");
//         verify(out).println("3. Export attendance records");
//         verify(out).println("4. Obtain class Attendance report ");
//         verify(out).println("5. Go back");
//     }

//     @Test
//     void testInvalidOption() throws IOException {
//         when(inputReader.readLine()).thenReturn("invalid", "1", "5", "2", "2");
//         mainMenuController.startMainMenu();
//         verify(out).println("Invalid option. Please select 1 for Manage attendance or 2 for Go back.");
//     }

//     @Test
//     void testException() throws IOException {
//         when(inputReader.readLine())
//                 .thenThrow(new IOException("Simulated IOException")).thenReturn("1", "5", "2", "2");
//         mainMenuController.startMainMenu();
//         verify(out).println("Main Menu error because Simulated IOException");
//     }

// }
