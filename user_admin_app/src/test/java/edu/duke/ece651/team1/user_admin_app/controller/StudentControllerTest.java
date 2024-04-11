package edu.duke.ece651.team1.user_admin_app.controller;

import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.shared.Student;
import edu.duke.ece651.team1.shared.User;
import edu.duke.ece651.team1.user_admin_app.view.StudentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentControllerTest {
    @Mock
    private BufferedReader inputReader;
    @Mock
    private PrintStream out;
    @Mock
    private StudentView studentView;
    @Mock
    private StudentDao studentDao;
    @Mock
    private UserDao userDao;
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        studentController = new StudentController(inputReader, out);
//        studentController.studentView = studentView;
//        inputReader = mock(BufferedReader.class);
        inputReader = new BufferedReader(new StringReader(""));

        out = mock(PrintStream.class);
        studentView = mock(StudentView.class);

        userDao = mock(UserDao.class);
        studentDao = mock(StudentDao.class);
        studentController = new StudentController(inputReader, out);

        studentController.setUserDao(userDao);
        studentController.setStudentDao(studentDao);
        studentController.setStudentView(studentView);
    }
    @Test
    void testStartStudentMenu_AddOption() throws IOException {
        when(studentView.readStudentOption()).thenReturn("add", "exit");
        studentController.startStudentMenu();
    }
    @Test
    void testStartStudentMenu_RemoveOption() throws IOException {
        when(studentView.readStudentOption()).thenReturn("remove", "exit");
        studentController.startStudentMenu();
    }
    @Test
    void testStartStudentMenu_UpdateOption() throws IOException {
        when(studentView.readStudentOption()).thenReturn("update", "exit");
        studentController.startStudentMenu();
    }
    @Test
    void testStartStudentMenu_ExitOption() throws IOException {
        when(studentView.readStudentOption()).thenReturn("exit");
        studentController.startStudentMenu();
    }
    @Test
    void testAddStudent() throws IOException {
        when(studentView.readStudentName()).thenReturn("Student Name");
        when(studentView.readStudentDisplayName()).thenReturn("Display Name");
        when(studentView.readStudentEmail()).thenReturn("student@example.com");

        when(studentDao.checkStudentExists(anyString())).thenReturn(false);
        when(userDao.addUser(any())).thenReturn(1);
        studentController.addStudent();
    }

    // question here
    @Test
    void testRemoveStudent_StudentFound() throws IOException {
        when(studentView.readStudentName()).thenReturn("TT");
        User mockUser = new User(1, "TT", "passw0rd", "Student");
        mockUser.setEmail("testEmail");
        when(userDao.findUserByUsername(anyString())).thenReturn(mockUser);
        Student mockStudent = new Student(1,"TT", "TT", "john@example.com",1);
//        when(studentView.readStudentName()).thenReturn(studentName);
        when(studentDao.findStudentByName(anyString())).thenReturn(Optional.of(mockStudent));
//        when(studentDao.findStudentByStudentID(anyInt())).thenReturn(mockStudent);
        studentController.removeStudent();
        verify(out).println("Student removed successfully.");
    }



    @Test
    void testRemoveStudent_StudentFound1() throws IOException {
        String studentName = "John Doe";
        Student student = new Student(1, studentName, "John", "john@example.com");
        when(studentView.readStudentName()).thenReturn(studentName);
        when(studentDao.findStudentByName(studentName)).thenReturn(Optional.of(student));
        studentController.removeStudent();
    }
    @Test
    void testRemoveStudent_NotFound() throws IOException {
        when(studentView.readStudentName()).thenReturn("Student Name");
        when(studentDao.findStudentByName(anyString())).thenReturn(Optional.empty());
        studentController.removeStudent();
        verify(studentDao, never()).removeStudent(any());
        verify(userDao, never()).removeUser(anyInt());
        verify(out).println("Student not found.");
    }
    @Test
    void testUpdateStudent() throws IOException {
        when(studentView.readStudentName()).thenReturn("Student Name");
        when(studentDao.findStudentByName(anyString())).thenReturn(Optional.of(new Student(1, "Student Name", "Old Display Name", "old_student@example.com")));
        when(studentView.readStudentDisplayName()).thenReturn("New Display Name");
        when(studentView.readStudentEmail()).thenReturn("new_student@example.com");
        studentController.updateStudent();
    }
    @Test
    void testUpdateStudent_StudentFound() throws IOException {
        String studentName = "John Doe";
        String displayName = "Johnny";
        String email = "johnny@example.com";
        Student existingStudent = new Student(1, studentName, displayName, email,1);
        when(studentView.readStudentName()).thenReturn(studentName);
        when(studentDao.findStudentByName(studentName)).thenReturn(Optional.of(existingStudent));
        when(studentView.readStudentDisplayName()).thenReturn("New Johnny");
        when(studentView.readStudentEmail()).thenReturn("new_johnny@example.com");
        studentController.updateStudent();
    }

    @Test
    void testUpdateStudent_StudentNotFound() throws IOException {
        String studentName = "John Doe";
        when(studentView.readStudentName()).thenReturn(studentName);
        when(studentDao.findStudentByName(studentName)).thenReturn(Optional.empty());
        studentController.updateStudent();
    }
    @Test
    void testUpdateStudent_StudentIdIsNull() throws IOException {
        String studentName = "John Doe";
        Student existingStudent = new Student(1, studentName, "Johnny", "johnny@example.com");
        when(studentView.readStudentName()).thenReturn(studentName);
        when(studentDao.findStudentByName(studentName)).thenReturn(Optional.of(existingStudent));
        studentController.updateStudent();
        verify(out).println("Student ID is null.");
    }
}