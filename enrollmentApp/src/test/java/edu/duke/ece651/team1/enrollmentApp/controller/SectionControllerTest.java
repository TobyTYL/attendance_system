//package edu.duke.ece651.team1.enrollmentApp.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import edu.duke.ece651.team1.data_access.Course.CourseDao;
//import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
//import edu.duke.ece651.team1.data_access.Section.SectionDao;
//import edu.duke.ece651.team1.shared.Professor;
//import edu.duke.ece651.team1.shared.Section;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.util.Arrays;
//import java.util.List;
//import java.sql.*;
//public class SectionControllerTest {
//    @Mock
//    private BufferedReader inputReader;
//    @Mock
//    private PrintStream out;
//    @Mock
//    private SectionDao sectionDao;
//    @Mock
//    private CourseDao courseDao;
//    @Mock
//    private ProfessorDao professorDao;
//    private SectionController sectionController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        // Using the new constructor for dependency injection
//        sectionController = new SectionController(inputReader, out, sectionDao, courseDao, professorDao);
//    }
//
//    @Test
//    void testStartSectionManagement_InvalidOption() {
//        try {
//            when(inputReader.readLine()).thenReturn("999", "5"); // Inputs causing an exception and then valid exit
//            sectionController.startSectionManagement("Math");
//            fail("Expected IllegalArgumentException was not thrown.");
//        } catch (IllegalArgumentException e) {
//            assertEquals("That option is invalid: it does not have the correct format.", e.getMessage());
//        } catch (Exception e) {
//            fail("Unexpected exception type thrown.");
//        }
//    }
//
//
//
//    @Test
//    void testAddSection_ProfessorNotFound() throws IOException {
//        when(inputReader.readLine()).thenReturn("Dr. Who");
//        when(professorDao.findAllProfessors()).thenReturn(List.of());
//        when(professorDao.findProfessorByName("Dr. Who")).thenReturn(null);
//        sectionController.addSection("Math");
//        verify(out).println("Professor with name Dr. Who not found. Please add the professor first.");
//    }
//    @Test
//    void testAddSection_ClassNotFound() throws IOException {
//        // Use mock setup to ensure professorId is correctly handled
//        Professor mockProfessor = mock(Professor.class);
//        when(mockProfessor.getProfessorId()).thenReturn(1);
//        when(professorDao.findProfessorByName("Dr. Smith")).thenReturn(mockProfessor);
//
//        when(inputReader.readLine()).thenReturn("Dr. Smith");
//        when(courseDao.getClassIdByName("Math")).thenReturn(-1); // Class not found scenario
//
//        sectionController.addSection("Math");
//
//        verify(out).println("Class Math not found.");
//    }
//
//    @Test
//    void testAddSection_Success() throws IOException {
//        // Mocking the Professor object to ensure non-null values
//        Professor mockProfessor = mock(Professor.class);
//        when(mockProfessor.getProfessorId()).thenReturn(1); // Ensures getProfessorId() returns 1
//        when(professorDao.findProfessorByName("Dr. Smith")).thenReturn(mockProfessor);
//
//        when(inputReader.readLine()).thenReturn("Dr. Smith");
//        when(courseDao.getClassIdByName("Math")).thenReturn(1); // Simulating the class "Math" exists
//
//        sectionController.addSection("Math");
//
//        verify(sectionDao).addSection(any(Section.class)); // Verifies a Section is added
//        verify(out).println(contains("New section added")); // Verifies the success message is printed
//    }
//    // @Test
//    // void testRemoveSection_DoesNotExist() throws IOException {
//    //     // Mock the Professor object to ensure getUserId() doesn't cause NullPointerException
//    //     Professor mockProfessor = mock(Professor.class);
//    //     when(mockProfessor.getUserId()).thenReturn(1); // Mocking the behavior of getUserId() method
//
//    //     // Assuming Section has a constructor that accepts professorId, or you have a setter method to set the Professor
//    //     Section mockSection = new Section(1, mockProfessor.getUserId(), 1); // Adjust constructor usage as per your implementation
//
//    //     when(sectionDao.getAllSections()).thenReturn(List.of(mockSection));
//    //     when(inputReader.readLine()).thenReturn("999"); // Simulating user input that doesn't match any Section ID
//
//    //     sectionController.removeSection("Math");
//
//    //     verify(out, never()).println(contains("removed successfully"));
//    // }
//
//
//    @Test
//    void testUpdateSection_SectionDoesNotExist() throws IOException, SQLException {
//        when(inputReader.readLine()).thenReturn("999", "1", "New Prof");
//        when(sectionDao.checkSectionExists(999)).thenReturn(false);
//        sectionController.updateSection("Math");
//        verify(out).println(contains("Section do not exists!"));
//    }
//    @Test
//    void testUpdateSection_ProfessorNotFound() throws IOException, SQLException {
//        when(inputReader.readLine()).thenReturn("1", "1", "Dr. Who");
//        when(sectionDao.checkSectionExists(1)).thenReturn(true);
//        when(professorDao.findProfessorByName("Dr. Who")).thenReturn(null);
//        sectionController.updateSection("Math");
//        verify(out).println(contains("Professor with name Dr. Who not found."));
//    }
//    @Test
//    void testUpdateSection_Success() throws IOException, SQLException {
//        // Mocking input for section update process
//        when(inputReader.readLine()).thenReturn("1", "1", "Dr. Smith");
//
//        // Ensuring the section exists
//        when(sectionDao.checkSectionExists(1)).thenReturn(true);
//
//        // Mocking the Professor object to ensure non-null values
//        Professor mockProfessor = mock(Professor.class);
//        when(mockProfessor.getProfessorId()).thenReturn(2); // Simulating finding a Professor with ID 2
//        when(professorDao.findProfessorByName("Dr. Smith")).thenReturn(mockProfessor);
//
//        sectionController.updateSection("Math");
//
//        // Verifying the update operation was called with expected parameters
//        verify(sectionDao).updateSectionProfessor(eq("Math"), eq(1), eq(2));
//
//        // Verifying the success message is printed
//        verify(out).println(contains("now assigned to Professor Dr. Smith successfully."));
//    }
//    @Test
//    void testConstructor(){
//        SectionController testController = new SectionController(inputReader, out);
//    }
//    @Test
//    void testRemoveSection_Success() throws IOException {
//        // Given
//        List<Section> sections = Arrays.asList(
//            new Section(1, 1, 1),
//            new Section(2, 2, 2)
//        );
//        when(sectionDao.getAllSections()).thenReturn(sections);
//
//    }
//
//
//
//}
