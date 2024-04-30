// package edu.duke.ece651.team1.enrollmentApp.view;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
// import edu.duke.ece651.team1.data_access.User.UserDao;
// import edu.duke.ece651.team1.shared.Professor;
// import edu.duke.ece651.team1.shared.Section;
// import edu.duke.ece651.team1.shared.User;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;
// import java.util.Arrays;
// import java.util.List;
// import java.util.ArrayList;

// public class SectionViewTest {
//   @Mock
//     private BufferedReader inputReader;
//     @Mock
//     private PrintStream out;
//     @Mock
//     private UserDao userDao;
//     @Mock
//     private ProfessorDao professorDao;
//     private SectionView sectionView;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         sectionView = new SectionView(inputReader, out, userDao, professorDao);
//         // Assuming you have a way to set userDao and professorDao in SectionView, if not, use reflection or adjust the design.
//     }
//     @Test
//     void testShowClassSectionOptions() {
//         ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//         sectionView.showClassSectionOptions("Math");
//         verify(out, times(5)).println(captor.capture());
//         List<String> capturedOutputs = captor.getAllValues();
//         assertTrue(capturedOutputs.contains("\nManage Sections for Math:"));
//         assertTrue(capturedOutputs.contains("1. Add a Section"));
//         // Assert for the rest of the options...
//     }
//     @Test
//     void testShowAllSections_NonEmpty() {
//         List<Section> sections = Arrays.asList(new Section(1, 1, 1));
//         when(professorDao.getProfessorById(1)).thenReturn(new Professor(1, 1));
//         when(userDao.getUserById(1)).thenReturn(new User(1, "ProfA", "password", "role"));
//         sectionView.showAllSections(sections);
//         verify(out).println("Available sections:");
//         // Capture the output to verify correct section details are printed
//     }
//     @Test
//     void testGetSectionManageChoice() throws IOException {
//         when(inputReader.readLine()).thenReturn("3");
//         assertEquals(3, sectionView.getSectionManageChoice());
//     }

//     @Test
//     void testConfirmAction_Yes() throws IOException {
//         when(inputReader.readLine()).thenReturn("yes");
//         assertTrue(sectionView.confirmAction("delete", 1));
//     }
//     @Test
//     void testShowAllSections_EmptyList() throws IOException {
//         sectionView.showAllSections(new ArrayList<>());
//         verify(out).println("No sections available.");
//     }
//     @Test
//     void testGetSectionToRemove_ValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("123");
//         int sectionId = sectionView.getSectionToRemove("Math");
//         assertEquals(123, sectionId);
//     }
//     @Test
//     void testGetSectionToUpdate() throws IOException {
//         String expectedInput = "update123";
//         when(inputReader.readLine()).thenReturn(expectedInput);
//         String actualInput = sectionView.getSectionToUpdate("Science");
//         assertEquals(expectedInput, actualInput);
//     }
//     @Test
//     void testGetProfessorDetailsForSection() throws IOException {
//         when(inputReader.readLine()).thenReturn("Prof. Smith");
//         assertEquals("Prof. Smith", sectionView.getProfessorDetailsForSection());
//     }
//     @Test
//     void testShowAddSectionSuccessMessage() {
//         ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//         sectionView.showAddSectionSuccessMessage("Math101", "Dr. John");
//         verify(out).println(captor.capture());
//         assertTrue(captor.getValue().contains("New section added to [SectionID: Math101] with Professor [Dr. John] successfully."));
//     }
//     @Test
//     void testConstructor(){
//       UserDao userDao;
//       ProfessorDao professorDao;
//       SectionView testiew = new SectionView(inputReader, out);
//     }
//     @Test
//   void testShowRemoveSectionSuccessMessage() {
//       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//       sectionView.showRemoveSectionSuccessMessage(101, "Mathematics");
//       verify(out).println(captor.capture());
//       String expectedMessage = "Section [SectionID: 101] removed successfully from [Class: Mathematics].";
//       assertEquals(expectedMessage, captor.getValue());
//   }
//   @Test
//   void testGetDetailToUpdateForSection() throws IOException {
//       when(inputReader.readLine()).thenReturn("1");
//       assertEquals(1, sectionView.getDetailToUpdateForSection());
//       verify(out).println("\nWhat would you like to update for the section?");
//       verify(out).println("1. Professor");
//       verify(out).print("Enter your choice: ");
//   }
//   @Test
//   void testGetNewProfessorName() throws IOException {
//       when(inputReader.readLine()).thenReturn("Dr. Einstein");
//       assertEquals("Dr. Einstein", sectionView.getNewProfessorName());
//   }
//   @Test
//   void testShowUpdateSectionSuccessMessage() {
//       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//       sectionView.showUpdateSectionSuccessMessage("202", "Dr. Curie");
//       verify(out).println(captor.capture());
//       String expectedMessage = "Section 202 now assigned to Professor Dr. Curie successfully.";
//       assertEquals(expectedMessage, captor.getValue());
//   }
//   @Test
//   void testShowUpdateClassNameConfirmation() {
//       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//       sectionView.showUpdateClassNameConfirmation("OldName", "NewName");
//       verify(out).println(captor.capture());
//       String expectedMessage = "Successfully update the class name: OldName to: NewName";
//       assertEquals(expectedMessage, captor.getValue());
//   }
//   @Test
//   void testShowAllProfessors_NonEmptyList() {
//       List<Professor> professors = Arrays.asList(new Professor(1, 1));
//       when(userDao.getUserById(1)).thenReturn(new User(1, "ProfName", "password", "role"));
      
//       sectionView.showAllProfessors(professors);
      
//       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//       verify(out, atLeastOnce()).println(captor.capture());
//       List<String> allOutputs = captor.getAllValues();
      
//       assertTrue(allOutputs.contains("Available Professors:"));
//       assertTrue(allOutputs.stream().anyMatch(s -> s.contains("ID: 1 - Name: ProfName")));
//   }
//   @Test
//   void testShowAllProfessors_EmptyList() {
//       // Create an empty list of professors
//       List<Professor> professors = new ArrayList<>();

//       // Execute the method with the empty list
//       sectionView.showAllProfessors(professors);

//       // Capture and verify the output
//       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//       verify(out).println(captor.capture());

//       // The expected message when the list is empty
//       String expectedMessage = "No professors available.";
//       assertEquals(expectedMessage, captor.getValue());
//       verify(out, times(1)).println(anyString());
//   }

// }
