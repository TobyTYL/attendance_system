//package edu.duke.ece651.team1.enrollmentApp.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//
//import edu.duke.ece651.team1.enrollmentApp.view.CourseView;
//import edu.duke.ece651.team1.shared.Course;
//import edu.duke.ece651.team1.data_access.Course.CourseDao;
//
//import static org.mockito.Mockito.*;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
//public class CourseControllerTest {
//  @Mock
//    private CourseDao courseDao;
//    @Mock
//    private BufferedReader inputReader;
//    @Mock
//    private PrintStream out;
//    @Mock
//    private CourseView courseView;
//    @Mock
//    private SectionController sectionController;
//
//    private CourseController courseController;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        courseController = new CourseController(inputReader, out, courseView, courseDao, sectionController);
//        // Inject the mock sectionController as there's no direct way to pass it through constructor
//
//    }
//    @Test
//    void testStartCourseManagement_ExitImmediately() throws IOException, SQLException {
//        when(courseView.getStudentManageChoice()).thenReturn(4); // Exit option
//        courseController.startCourseManagement();
//        verify(courseView, times(1)).showClassManageOption();
//        verifyNoMoreInteractions(courseDao, sectionController);
//    }
//    @Test
//    void testCreateNewClass_AlreadyExists() throws IOException {
//        when(courseView.getClassNameToCreate()).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//
//        courseController.createNewClass();
//
//        verify(out).println("Class already exists!");
//        //verifyNoMoreInteractions(courseDao);
//    }
//
//    @Test
//    void testCreateNewClass_Success() throws Exception {
//        when(courseView.getClassNameToCreate()).thenReturn("NewClass");
//        when(courseDao.checkCourseExists("NewClass")).thenReturn(false);
//
//        courseController.createNewClass();
//
//        verify(courseDao).addCourse(any(Course.class));
//        //verify(courseView).showCreateNewClassSuccessMessage("NewClass");
//    }
//    @Test
//    void testUpdateClass_ClassDoesNotExist() throws IOException, SQLException {
//        when(courseView.getClassNameToUpdateOrRemove("update")).thenReturn("NonexistentClass");
//        when(courseDao.checkCourseExists("NonexistentClass")).thenReturn(false);
//
//        courseController.updateClass();
//
//        verify(out).println("Class does not exist!");
//    }
//    @Test
//    void testRemoveClass_ClassExistsAndRemoved() throws IOException {
//        when(courseView.getClassNameToUpdateOrRemove("remove")).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//        when(courseView.confirmAction("remove", "ExistingClass")).thenReturn(true);
//
//        courseController.removeClass();
//
//        verify(courseDao).deleteCourse("ExistingClass");
//        verify(courseView).showActionConfirmation("removed", "ExistingClass");
//    }
//    @Test
//    void testUpdateClassName_NewNameExists() throws IOException {
//        String oldName = "OldClass";
//        String newName = "ExistingClass";
//
//        when(courseView.getNewClassName()).thenReturn(newName);
//        when(courseDao.checkCourseExists(newName)).thenReturn(true);
//
//        courseController.updateClassName(oldName);
//
//        verify(out).println("Class with the new name already exists!");
//        //verifyNoMoreInteractions(courseDao);
//    }
//    @Test
//    void testShowAllCourses_NoCoursesAvailable() throws IOException {
//        when(courseDao.getAllCourses()).thenReturn(List.of());
//
//        courseController.showAllCourses();
//
//        verify(out).println("No courses available.");
//    }
//    @Test
//    void testStartCourseManagement_InvalidOption() throws IOException, SQLException {
//        // First return an invalid option, then return the option to exit
//        when(courseView.getStudentManageChoice()).thenReturn(99, 4); // 4 is the exit option
//
//        courseController.startCourseManagement();
//
//        // Verify that the invalid option message is printed
//        verify(out).println("Invalid option for Class Management Menu.");
//        // Verify that the menu is shown at least twice: once initially, and once after the invalid option
//        verify(courseView, atLeast(2)).showClassManageOption();
//        // Verifying that no controller actions are invoked since only invalid and exit options are chosen
//        //verify(courseController, never()).startCourseManagement();
//        //verify(enrollmentController, never()).startEnrollment();
//    }
//
//    @Test
//    void testCreateNewClass_Exception() throws IOException {
//        when(courseView.getClassNameToCreate()).thenThrow(new IOException("Test Exception"));
//
//        courseController.createNewClass();
//
//        verify(out).println("An error occurred: Test Exception");
//    }
//    @Test
//    void testUpdateClass_AddSection() throws IOException, SQLException {
//        when(courseView.getClassNameToUpdateOrRemove("update")).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//        when(courseView.getStudentUpdateCourseChoice()).thenReturn(1, 5); // Choose to add section, then exit
//
//        courseController.updateClass();
//
//        verify(sectionController).addSection("ExistingClass");
//    }
//    @Test
//    void testUpdateClass_RemoveSection() throws IOException, SQLException {
//        when(courseView.getClassNameToUpdateOrRemove("update")).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//        // Chain return values to simulate the selection and then the exit.
//        when(courseView.getStudentUpdateCourseChoice()).thenReturn(2, 5);
//
//        courseController.updateClass();
//
//        verify(sectionController).removeSection("ExistingClass");
//    }
//
//    @Test
//    void testUpdateClass_UpdateSection() throws IOException, SQLException {
//        when(courseView.getClassNameToUpdateOrRemove("update")).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//        when(courseView.getStudentUpdateCourseChoice()).thenReturn(3, 5); // Choose to update section, then exit
//
//        courseController.updateClass();
//
//        verify(sectionController).updateSection("ExistingClass");
//    }
//    @Test
//    void testUpdateClass_UpdateClassName() throws IOException, SQLException {
//        CourseController spyController = spy(courseController);
//        when(courseView.getClassNameToUpdateOrRemove("update")).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//        when(courseView.getStudentUpdateCourseChoice()).thenReturn(4, 5);
//
//        spyController.updateClass();
//
//        // Assuming updateClassName is a method that's called within updateClass.
//        verify(spyController).updateClassName("ExistingClass");
//    }
//
//    @Test
//    void testUpdateClass_InvalidOption() throws IOException, SQLException {
//        when(courseView.getClassNameToUpdateOrRemove("update")).thenReturn("ExistingClass");
//        when(courseDao.checkCourseExists("ExistingClass")).thenReturn(true);
//        // First return an invalid option, then the option to exit.
//        when(courseView.getStudentUpdateCourseChoice()).thenReturn(99, 5);
//
//        courseController.updateClass();
//
//        verify(out).println("Invalid option for Class Management Menu.");
//    }
//    @Test
//    void testCreateNewClass_SuccessMessageDisplayed() throws Exception {
//        String newClassName = "NewClass";
//        when(courseView.getClassNameToCreate()).thenReturn(newClassName);
//        when(courseDao.checkCourseExists(newClassName)).thenReturn(false, true); // First call returns false, second call returns true.
//
//        courseController.createNewClass();
//
//        verify(courseDao).addCourse(any(Course.class));
//        verify(courseView).showCreateNewClassSuccessMessage(newClassName);
//    }
//    @Test
//    void testRemoveClass_ClassDoesNotExist() throws IOException {
//        String classNameToRemove = "NonExistentClass";
//        when(courseView.getClassNameToUpdateOrRemove("remove")).thenReturn(classNameToRemove);
//        when(courseDao.checkCourseExists(classNameToRemove)).thenReturn(false);
//
//        courseController.removeClass();
//
//        verify(out).println("Class does not exist!");
//        verify(courseDao, never()).deleteCourse(anyString());
//        verify(courseView, never()).showActionConfirmation(anyString(), anyString());
//    }
//    @Test
//    void testShowAllCourses_CoursesAreAvailable() throws IOException {
//        List<Course> courses = Arrays.asList(new Course(1, "Course1"), new Course(2, "Course2"));
//        when(courseDao.getAllCourses()).thenReturn(courses);
//
//        courseController.showAllCourses();
//
//        verify(out).println("Available Courses:");
//        verify(out).println("ID: 1, Name: Course1");
//        verify(out).println("ID: 2, Name: Course2");
//    }
//
//}
