// package edu.duke.ece651.team1.enrollmentApp.view;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
// import edu.duke.ece651.team1.data_access.User.UserDao;
// import edu.duke.ece651.team1.shared.Course;
// import edu.duke.ece651.team1.shared.Professor;
// import edu.duke.ece651.team1.shared.Section;
// import edu.duke.ece651.team1.shared.User;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;
// import java.util.Arrays;
// import java.util.List;


// import static org.mockito.Mockito.*;

// public class EnrollmentViewTest {
//     @Mock
//     private BufferedReader inputReader;
//     @Mock
//     private PrintStream out;
//     @Mock
//     private ProfessorDao professorDao;
//     @Mock
//     private UserDao userDao;
//     private EnrollmentView enrollmentView;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         enrollmentView = new EnrollmentView(inputReader, out, professorDao, userDao);
//     }
//     @Test
//     void testShowEnrollmentOptions() throws IOException {
//         enrollmentView.showEnrollmentOptions();
//         verify(out, times(4)).println(anyString());
//     }
//     @Test
//     void testGetEnrollmentChoice() throws IOException {
//         when(inputReader.readLine()).thenReturn("2");
//         assertEquals(2, enrollmentView.getEnrollmentChoice());
//     }

//     @Test
//     void testShowAllCourses() {
//         List<Course> courses = Arrays.asList(new Course(1, "Math"), new Course(2, "Science"));
//         enrollmentView.showAllCourses(courses);
//         verify(out, times(3)).println(anyString());
//     }
//     @Test
//     void testGetSectionChoice() throws IOException {
//         when(inputReader.readLine()).thenReturn("101");
//         assertEquals(101, enrollmentView.getSectionChoice());
//     }
    
//     @Test
//     void testShowAllSections() throws IOException {
//         List<Section> sections = Arrays.asList(new Section(1, 1, 1));
//         Professor professor = new Professor(1, 1);
//         User user = new User(1, "profName", "hash", "role");
        
//         when(professorDao.getProfessorById(1)).thenReturn(professor);
//         when(userDao.getUserById(1)).thenReturn(user);
        
//         ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        
//         enrollmentView.showAllSections(sections);
        
//         verify(out, times(2)).println(captor.capture()); // Adjust the times() value based on the expected number of println calls
//         List<String> allStrings = captor.getAllValues();
        
//         // Now assert that one of the captured strings contains "profName"
//         System.out.println(allStrings);
//         assertTrue(allStrings.stream().anyMatch(s -> s.contains("profName")), "Expected professor's name was not found in the output.");
//     }
//     @Test
//     void testGetClassForEnrollment_ValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("Mathematics");
//         assertEquals("Mathematics", enrollmentView.getClassForEnrollment());
//     }

//     @Test
//     void testGetClassForEnrollment_InvalidInputThenValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("").thenReturn("Physics");
//         // Simulate invalid input followed by valid input
//         assertEquals("Physics", enrollmentView.getClassForEnrollment());
//         // Verify that the user is prompted until valid input is provided
//         verify(out).println("Invalid input. Please enter a valid class name.");
//     }
//     @Test
//     void testGetCsvFilePath_ValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("students.csv");
//         assertEquals("students.csv", enrollmentView.getCsvFilePath());
//     }

//     @Test
//     void testGetCsvFilePath_InvalidInputThenValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("").thenReturn("enrollment.csv");
//         // Simulate invalid input followed by valid input
//         assertEquals("enrollment.csv", enrollmentView.getCsvFilePath());
//         // Verify that the user is prompted until valid input is provided
//         verify(out).println("Filename cannot be empty. Please enter a valid file path.");
//     }
//     @Test
//     void testGetStudentIDForEnrollment_ValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("12345");
//         assertEquals("12345", enrollmentView.getStudentIDForEnrollment());
//     }

//     @Test
//     void testGetStudentIDForEnrollment_InvalidInputThenValidInput() throws IOException {
//         when(inputReader.readLine()).thenReturn("").thenReturn("67890");
//         assertEquals("67890", enrollmentView.getStudentIDForEnrollment());
//         verify(out).println("Invalid input. Please enter a valid student ID.");
//     }
//     @Test
//     void testShowAllSections_WithKnownAndUnknownProfessor() {
//         List<Section> sections = Arrays.asList(
//             new Section(1, 1, 1), // Known professor
//             new Section(2, 2, 999) // Unknown professor, assuming 999 does not exist
//         );
//         Professor knownProfessor = new Professor(1, 1);
//         User professorUser = new User(1, "KnownProf", "hash", "role");

//         when(professorDao.getProfessorById(1)).thenReturn(knownProfessor);
//         when(userDao.getUserById(1)).thenReturn(professorUser);
//         when(professorDao.getProfessorById(999)).thenReturn(null);

//         enrollmentView.showAllSections(sections);

//         verify(out).println("\nAvailable Sections for Selected Course:");
//         verify(out).println("SectionID: 1 - Professor ID: 1 - Name: KnownProf");
//         verify(out).println("SectionID: 2 - Professor ID: 999 - Name: Unknown");
//     }
//     @Test
//     void testConstructor(){
//         final BufferedReader inputReader2 = new BufferedReader(inputReader);
//         final PrintStream out2 = new PrintStream(out);
//         EnrollmentView testiew = new EnrollmentView(inputReader2, out2);
//     }
//   }