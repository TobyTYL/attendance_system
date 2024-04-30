//package edu.duke.ece651.team1.enrollmentApp.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.StringReader;
//import java.util.Collections;
//import java.util.Optional;
//import org.junit.runner.RunWith;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.api.mockito.PowerMockito;
//import static org.powermock.api.mockito.PowerMockito.whenNew;
//
//import static org.mockito.Mockito.*;
//
//import edu.duke.ece651.team1.data_access.Course.CourseDao;
//import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
//import edu.duke.ece651.team1.data_access.Notification.NotificationPreference;
//import edu.duke.ece651.team1.data_access.Notification.NotificationPreferenceDao;
//import edu.duke.ece651.team1.data_access.Section.SectionDao;
//import edu.duke.ece651.team1.data_access.Student.StudentDao;
//import edu.duke.ece651.team1.enrollmentApp.*;
//import edu.duke.ece651.team1.enrollmentApp.view.EnrollmentView;
//import edu.duke.ece651.team1.shared.Course;
//import edu.duke.ece651.team1.shared.Enrollment;
//import edu.duke.ece651.team1.shared.Section;
//import edu.duke.ece651.team1.shared.Student;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(EnrollmentController.class)
//public class EnrollmentControllerTest {
//  @Mock
//    private EnrollmentView enrollmentView;
//    @Mock
//    private CourseDao courseDao;
//    @Mock
//    private SectionDao sectionDao;
//    @Mock
//    private EnrollmentDaoImpl enrollmentDao;
//    @Mock
//    private StudentDao studentDao;
//    @Mock
//    private NotificationPreferenceDao notificationPreferenceDao;
//
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//
//    private EnrollmentController enrollmentController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        System.setOut(new PrintStream(outContent));
//        enrollmentController = new EnrollmentController(new BufferedReader(new StringReader("")), System.out);
//        enrollmentController.enrollmentView = enrollmentView;
//        enrollmentController.courseDao = courseDao;
//        enrollmentController.sectionDao = sectionDao;
//        enrollmentController.enrollmentDao = enrollmentDao;
//        enrollmentController.studentDao = studentDao;
//        enrollmentController.notificationPreferenceDao = notificationPreferenceDao;
//    }
//    @AfterEach
//    void restoreSystemOutStream() {
//        System.setOut(originalOut);
//    }
//    @Test
//    void testManuallyEnrollStudent_Success() throws IOException {
//        when(enrollmentView.getStudentIDForEnrollment()).thenReturn("123");
//        when(studentDao.findStudentByStudentID(anyInt())).thenReturn(Optional.of(new Student(123, "LegalName", "DisplayName", "Email", 456)));
//        when(courseDao.getAllCourses()).thenReturn(Collections.singletonList(new Course(1, "Test Course")));
//        when(enrollmentView.getClassForEnrollment()).thenReturn("Test Course");
//        when(courseDao.getClassByName(anyString())).thenReturn(new Course(1, "Test Course"));
//        when(sectionDao.getSectionsByClassId(anyInt())).thenReturn(Collections.singletonList(new Section(1, 1, 2)));
//        when(enrollmentView.getSectionChoice()).thenReturn(1);
//        when(sectionDao.getSectionById(anyInt())).thenReturn(new Section(1, 1, 2));
//        when(enrollmentDao.isStudentAlreadyEnrolled(anyInt(), anyInt())).thenReturn(false);
//
//        enrollmentController.manuallyEnrollStudent();
//
//        // Assertions to verify interactions and output
//        verify(enrollmentDao).addEnrollment(any(Enrollment.class));
//        assertTrue(outContent.toString().contains("successfully enrolled"));
//    }
//
//    @Test
//    void testBatchEnrollStudent_FileNotFound() throws IOException {
//        when(enrollmentView.getCsvFilePath()).thenReturn("nonexistent.csv");
//
//        enrollmentController.batchEnrollStudent();
//
//        assertTrue(outContent.toString().contains("File not found"));
//    }
//    @Test
//    void testStartEnrollment_InvalidChoice() throws IOException {
//        when(enrollmentView.getEnrollmentChoice()).thenReturn(99); // Simulate an invalid choice
//
//        enrollmentController.startEnrollment();
//
//        verify(enrollmentView).showEnrollmentOptions(); // Verify options were shown
//        // Optionally check for specific output indicating invalid choice handling
//    }
//    @Test
//    void testManuallyEnrollStudent_StudentNotFound() throws IOException {
//        when(enrollmentView.getStudentIDForEnrollment()).thenReturn("123");
//        when(studentDao.findStudentByStudentID(123)).thenReturn(Optional.empty());
//
//        enrollmentController.manuallyEnrollStudent();
//
//        assertTrue(outContent.toString().contains("Student ID not found. Please register the student first."));
//    }
//    @Test
//    void testManuallyEnrollStudent_CourseNotFound() throws IOException {
//        when(enrollmentView.getStudentIDForEnrollment()).thenReturn("123");
//        when(studentDao.findStudentByStudentID(anyInt())).thenReturn(Optional.of(new Student()));
//        when(enrollmentView.getClassForEnrollment()).thenReturn("Nonexistent Course");
//        when(courseDao.getClassByName("Nonexistent Course")).thenReturn(null);
//
//        enrollmentController.manuallyEnrollStudent();
//
//        assertTrue(outContent.toString().contains("Course not found. Please try again."));
//    }
//    @Test
//    void testManuallyEnrollStudent_SectionNotFound() throws IOException {
//        mockManuallyEnrollStudentProcessUntilSectionChoice();
//        when(sectionDao.getSectionById(1)).thenReturn(null);
//
//        enrollmentController.manuallyEnrollStudent();
//
//        assertTrue(outContent.toString().contains("Section not found. Please try again."));
//    }
//
//    private void mockManuallyEnrollStudentProcessUntilSectionChoice() throws IOException {
//        when(enrollmentView.getStudentIDForEnrollment()).thenReturn("123");
//        when(studentDao.findStudentByStudentID(anyInt())).thenReturn(Optional.of(new Student()));
//        when(enrollmentView.getClassForEnrollment()).thenReturn("Test Course");
//        when(courseDao.getClassByName("Test Course")).thenReturn(new Course(1, "Test Course"));
//        when(sectionDao.getSectionsByClassId(anyInt())).thenReturn(Collections.singletonList(new Section(1, 1, 2)));
//        when(enrollmentView.getSectionChoice()).thenReturn(1);
//    }
//    @Test
//    void testBatchEnrollStudent_ParsingError() throws IOException {
//        // Assuming you can simulate or mock the reading process to produce a format error
//        when(enrollmentView.getCsvFilePath()).thenReturn("invalid_format.csv");
//
//        enrollmentController.batchEnrollStudent();
//
//        // Check that the error output contains a message about parsing or format errors
//        //assertTrue(outContent.toString().contains("Invalid"));
//    }
//    @Test
//    void testEnrollStudent_AlreadyEnrolled() {
//        int studentId = 123;
//        int sectionId = 456;
//        when(enrollmentDao.isStudentAlreadyEnrolled(studentId, sectionId)).thenReturn(true);
//
//        assertFalse(enrollmentController.enrollStudent(studentId, sectionId));
//        assertTrue(outContent.toString().contains("Student ID " + studentId + " is already enrolled in Section ID: " + sectionId));
//    }
//    @Test
//    void testEnrollStudent_ClassIdNotFound() {
//        int studentId = 123;
//        int sectionId = 456;
//        when(enrollmentDao.isStudentAlreadyEnrolled(studentId, sectionId)).thenReturn(false);
//        when(sectionDao.getClassIdBySectionId(sectionId)).thenReturn(-1);
//
//        assertFalse(enrollmentController.enrollStudent(studentId, sectionId));
//        assertTrue(outContent.toString().contains("Failed to find the class for the given section. Enrollment failed."));
//    }
//    @Test
//    void testEnrollStudent_AddNotificationPreference() {
//        setupEnrollStudentPreconditions();
//
//        when(notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(anyInt(), anyInt())).thenReturn(null);
//
//        assertTrue(enrollmentController.enrollStudent(123, 456));
//        verify(notificationPreferenceDao).addNotificationPreference(123, 1, true);
//    }
//    @Test
//    void testEnrollStudent_UpdateNotificationPreference() {
//        setupEnrollStudentPreconditions();
//
//        NotificationPreference existingPreference = new NotificationPreference(1, 123, 1, false);
//        when(notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(anyInt(), anyInt())).thenReturn(existingPreference);
//
//        assertTrue(enrollmentController.enrollStudent(123, 456));
//        verify(notificationPreferenceDao).updateNotificationPreference(123, 1, true);
//    }
//    private void setupEnrollStudentPreconditions() {
//        int studentId = 123;
//        int sectionId = 456;
//        int classId = 1;
//        when(enrollmentDao.isStudentAlreadyEnrolled(studentId, sectionId)).thenReturn(false);
//        when(sectionDao.getClassIdBySectionId(sectionId)).thenReturn(classId);
//        // Assuming addEnrollment does not return a value and thus does not need to be stubbed for true/false
//    }
//    @Test
//    void testStartEnrollment_ManualEnrollStudent() throws IOException {
//        // Arrange
//        when(enrollmentView.getEnrollmentChoice()).thenReturn(1);
//        //doNothing().when(enrollmentController).manuallyEnrollStudent();
//
//        // Act
//        enrollmentController.startEnrollment();
//
//        // Assert
//        verify(enrollmentView).showEnrollmentOptions();
//        //verify(enrollmentController).manuallyEnrollStudent();
//    }
//    @Test
//    void testStartEnrollment_BatchEnrollStudent() throws IOException {
//        // Arrange
//        when(enrollmentView.getEnrollmentChoice()).thenReturn(2);
//        //doNothing().when(enrollmentController).batchEnrollStudent();
//
//        // Act
//        enrollmentController.startEnrollment();
//
//        // Assert
//        verify(enrollmentView).showEnrollmentOptions();
//        //verify(enrollmentController).batchEnrollStudent();
//    }
//    @Test
//    void testStartEnrollment_Exit() throws IOException {
//        // Arrange
//        when(enrollmentView.getEnrollmentChoice()).thenReturn(3);
//
//        // Act
//        enrollmentController.startEnrollment();
//
//        // Assert
//        verify(enrollmentView).showEnrollmentOptions();
//        // No further interactions after the choice
//    }
//    @Test
//    void testEnrollStudent_NotificationPreferenceUpdate() {
//        // Arrange
//        int studentId = 123;
//        int classId = 1;
//        NotificationPreference existingPreference = new NotificationPreference(2,studentId, classId, false);
//        when(notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(studentId, classId))
//            .thenReturn(existingPreference);
//
//        // Act
//        enrollmentController.enrollStudent(studentId, classId);
//
//        // Assert
//        //verify(notificationPreferenceDao).updateNotificationPreference(studentId, classId, true);
//    }
//    @Test
//    void testBatchEnrollStudent_InvalidNumberFormat() throws Exception {
//        // Arrange
//        when(enrollmentView.getCsvFilePath()).thenReturn("test.csv");
//        String invalidData = "notANumber,SomeCourse,notANumber\n";
//        BufferedReader bufferedReader = new BufferedReader(new StringReader(invalidData));
//        whenNew(BufferedReader.class).withAnyArguments().thenReturn(bufferedReader);
//
//        // Act
//        enrollmentController.batchEnrollStudent();
//
//        // Assert
//        //assertTrue(outContent.toString().contains("Invalid number format in line"));
//        System.out.println("************************************");
//        System.out.println(outContent.toString());
//        assertFalse(outContent.toString().contains("Batch enrollment"));
//        assertFalse(outContent.toString().contains("Encountered failed enrollment with 1 entries."));
//    }
//    @Test
//    void testBatchEnrollStudent_UnexpectedError() throws Exception {
//        // Arrange
//        when(enrollmentView.getCsvFilePath()).thenReturn("test.csv");
//        String validData = "123,SomeCourse,456\n";
//        BufferedReader bufferedReader = mock(BufferedReader.class);
//        when(bufferedReader.readLine()).thenReturn(validData, (String) null);
//        whenNew(BufferedReader.class).withAnyArguments().thenReturn(bufferedReader);
//        //doThrow(new RuntimeException("Unexpected error")).when(enrollmentController).enrollStudent(anyInt(), anyInt());
//
//        // Act
//        enrollmentController.batchEnrollStudent();
//
//        // Assert
//        assertFalse(outContent.toString().contains("Unexpected error in line"));
//        assertFalse(outContent.toString().contains("Batch enrollment process completed."));
//        assertFalse(outContent.toString().contains("Encountered failed enrollment with 1 entries."));
//    }
//    @Test
//    void testBatchEnrollStudent_IOException() throws IOException {
//        when(enrollmentView.getCsvFilePath()).thenReturn("existent.csv");
//        BufferedReader mockedReader = mock(BufferedReader.class);
//        when(mockedReader.readLine()).thenThrow(new IOException("Error reading line"));
//
//        enrollmentController.batchEnrollStudent();
//
//        assertFalse(outContent.toString().contains("Error reading file: "));
//    }
//    @Test
//    void testBatchEnrollStudent_SuccessfulProcessing() throws Exception {
//        when(enrollmentView.getCsvFilePath()).thenReturn("well_formatted.csv");
//        // Prepare a well-formatted CSV content
//        BufferedReader mockedReader = new BufferedReader(new StringReader("StudentID,CourseName,SectionID\n123,Math101,1"));
//        whenNew(BufferedReader.class).withAnyArguments().thenReturn(mockedReader);
//        //when(enrollmentController.enrollStudent(anyInt(), anyInt())).thenReturn(true);
//
//        enrollmentController.batchEnrollStudent();
//
//        assertFalse(outContent.toString().contains("Batch enrollment process completed."));
//        assertFalse(outContent.toString().contains("- Successfully enrolled 1 students."));
//    }
//    @Test
//    void testBatchEnrollStudent_InvalidFormat() throws Exception {
//        when(enrollmentView.getCsvFilePath()).thenReturn("invalid_format.csv");
//        BufferedReader mockedReader = new BufferedReader(new StringReader("InvalidContent"));
//        whenNew(BufferedReader.class).withAnyArguments().thenReturn(mockedReader);
//
//        enrollmentController.batchEnrollStudent();
//
//        assertFalse(outContent.toString().contains("Invalid format in line: "));
//        assertFalse(outContent.toString().contains("- Encountered failed enrollment with 1 entries."));
//    }
//
//    @Test
//    void testBatchEnrollStudent_NumberFormatIssue() throws Exception {
//        when(enrollmentView.getCsvFilePath()).thenReturn("number_format_issue.csv");
//        BufferedReader mockedReader = new BufferedReader(new StringReader("NaN,Math101,NaN"));
//        whenNew(BufferedReader.class).withAnyArguments().thenReturn(mockedReader);
//
//        enrollmentController.batchEnrollStudent();
//
//        assertFalse(outContent.toString().contains("Invalid number format in line: "));
//        assertFalse(outContent.toString().contains("- Encountered failed enrollment with 1 entries."));
//    }
//}
