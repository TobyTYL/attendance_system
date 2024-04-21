// package edu.duke.ece651.team1.enrollmentApp.view;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;
// import static org.mockito.Mockito.*;
// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;

// class MainMenuViewTest {

//   @Mock
//   private BufferedReader inputReader;
//   @Mock
//   private PrintStream out;
//   private MainMenuView mainMenuView;

//   @BeforeEach
//   void setUp() {
//       MockitoAnnotations.openMocks(this);
//       mainMenuView = new MainMenuView(inputReader, out);
//   }
//   @Test
//   void testDisplayMenu() {
//       mainMenuView.displayMenu();
//       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//       verify(out, times(7)).println(captor.capture()); // Adjust the number based on actual menu items

//       var capturedStrings = captor.getAllValues();
//       assertTrue(capturedStrings.contains("Welcome to the Class Management System (CMS)"));
//       assertTrue(capturedStrings.contains("1. Manage Courses"));
//       assertTrue(capturedStrings.contains("3. Exit"));
//   }
//   @Test
//   void testGetMenuChoice_Valid() throws IOException {
//       when(inputReader.readLine()).thenReturn("2");
//       assertEquals(2, mainMenuView.getMenuChoice());
//   }
//   @Test
//   void testGetMenuChoice_InvalidChoice() throws IOException {
//       when(inputReader.readLine()).thenReturn("4");
//       assertEquals(-1, mainMenuView.getMenuChoice());
//       verify(out).println("Invalid choice. Please enter a number between 1 and 3.");
//   }
//   @Test
//   void testGetMenuChoice_InvalidInput() throws IOException {
//       when(inputReader.readLine()).thenReturn("abc");
//       assertEquals(-1, mainMenuView.getMenuChoice());
//       verify(out).println("Invalid input. Please enter a number.");
//   }

// }