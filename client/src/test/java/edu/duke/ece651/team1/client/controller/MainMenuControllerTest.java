// package edu.duke.ece651.team1.client.controller;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import edu.duke.ece651.team1.client.view.MainMenuView;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;

// import static org.mockito.Mockito.*;
// @ExtendWith(MockitoExtension.class)
// public class MainMenuControllerTest {
//    @Mock
//     private BufferedReader inputReader;
    
//     @Mock
//     private PrintStream out;
    
//     @Mock
//     private MainMenuView mainMenuView;
    
//     @Mock
//     private AttendanceController attendanceController;
    
//     @Mock
//     private StudentController studentController;
    
//     @InjectMocks
//     private ProfessorMainMenuController mainMenuController;

//     @BeforeEach
//     void setUp() {
//         // Configure default behaviors for the mocked mainMenuView
//         mainMenuController = new ProfessorMainMenuController(inputReader, out);
//         mainMenuController.mainMenuView = mainMenuView; // Direct assignment as Mockito might not inject into non-setter public fields
//         mainMenuController.attendanceController = attendanceController;
//         mainMenuController.studentController = studentController;
//     }
//     @Test
//     void testAttendanceOption() throws IOException {
//         when(mainMenuView.readMainOption()).thenReturn("attendance", "exit");

//         mainMenuController.startMainMenu();

//         verify(attendanceController).startAttendanceMenue();
//         verify(out).println("GoodBye!");
//     }

//     @Test
//     void testStudentsOption() throws IOException {
//         when(mainMenuView.readMainOption()).thenReturn("students", "exit");

//         mainMenuController.startMainMenu();

//         verify(studentController).startStudentMenu();
//         verify(out).println("GoodBye!");
//     }

//     @Test
//     void testInvalidOption() throws IOException {
//         when(mainMenuView.readMainOption()).thenReturn("invalid", "exit");

//         mainMenuController.startMainMenu();

//         verify(out).println("GoodBye!");
//     }
//     @Disabled
//     @Test
//     void testExceptionHandlingInMenu() throws IOException {
//         when(mainMenuView.readMainOption()).thenThrow(new IOException("Test Exception"));

//         mainMenuController.startMainMenu();

//         verify(out).println("Main Menu error because Test Exception");
//     }

// }
