// package edu.duke.ece651.team1.enrollmentApp.view;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Captor;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.io.BufferedReader;
// import java.io.PrintStream;
// import java.util.*;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;

// public class CourseViewTest {
//   @Mock
//     private BufferedReader inputReader;
    
//     @Mock
//     private PrintStream out;
    
//     @Captor
//     private ArgumentCaptor<String> captor;
    
//     private CourseView courseView;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         courseView = new CourseView(inputReader, out);
//     }

//     @Test
//     void testShowClassManageOption() throws Exception {
//         courseView.showClassManageOption();
//         verify(out, times(5)).println(captor.capture());
        
//         // You'll need to assert the correctness of each printed line.
//         // This example just checks that four lines are indeed captured.
//         assertEquals(5, captor.getAllValues().size());
//     }
    
//     // Example test case for getClassNameToCreate
//     @Test
//     void testGetClassNameToCreate() throws Exception {
//         when(inputReader.readLine()).thenReturn("Software Design");
//         assertEquals("Software Design", courseView.getClassNameToCreate());
//     }

//     @Test
//     void testGetStudentManageChoice() throws Exception {
//         when(inputReader.readLine()).thenReturn("3");
//         assertEquals(3, courseView.getStudentManageChoice());
//         verify(inputReader, times(1)).readLine();
//     }

//     @Test
//     void testShowCreateNewClassSuccessMessage() {
//         courseView.showCreateNewClassSuccessMessage("Advanced Math");
//         verify(out).println("You successfully created Advanced Math class");
//     }

//     @Test
//     void testGetClassNameToUpdateOrRemove() throws Exception {
//         when(inputReader.readLine()).thenReturn("Math 101");
//         assertEquals("Math 101", courseView.getClassNameToUpdateOrRemove("update"));
//     }

//     @Test
//     void testConfirmActionYes() throws Exception {
//         when(inputReader.readLine()).thenReturn("yes");
//         assertTrue(courseView.confirmAction("delete", "Math 101"));
//     }

//     @Test
//     void testConfirmActionNo() throws Exception {
//         when(inputReader.readLine()).thenReturn("no");
//         assertFalse(courseView.confirmAction("delete", "Math 101"));
//     }

//     @Test
//     void testShowActionConfirmation() {
//         courseView.showActionConfirmation("deleted", "Math 101");
//         verify(out).println("\nClass Math 101 deleted successfully.");
//     }

//     @Test
//     void testGetStudentUpdateCourseChoice() throws Exception {
//         when(inputReader.readLine()).thenReturn("3");
//         assertEquals(3, courseView.getStudentUpdateCourseChoice());
//     }

//     @Test
//     void testShowClassSectionOptions() {
//         courseView.showClassSectionOptions("Software Engineering");
//         verify(out, times(6)).println(captor.capture());

//         List<String> allValues = captor.getAllValues();
//         assertTrue(allValues.contains("\nManage Software Engineering"));
//         assertTrue(allValues.contains("1. Add a Section"));
//         // Continue for all lines
//     }

//     @Test
//     void testGetNewClassName() throws Exception {
//         when(inputReader.readLine()).thenReturn("Advanced Software Engineering");
//         assertEquals("Advanced Software Engineering", courseView.getNewClassName());
//     }

//     @Test
//     void testShowUpdateClassNameConfirmation() {
//         courseView.showUpdateClassNameConfirmation("Software Engineering", "Advanced Software Engineering");
//         verify(out).println("\nSuccessfully updated the class name from \"Software Engineering\" to \"Advanced Software Engineering\".");
//     }
// }
