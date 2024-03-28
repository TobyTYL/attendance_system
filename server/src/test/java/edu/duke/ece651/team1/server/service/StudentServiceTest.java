package edu.duke.ece651.team1.server.service;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import edu.duke.ece651.team1.server.repository.InMemoryRosterRepository;
import edu.duke.ece651.team1.shared.Student;

public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private InMemoryRosterRepository rosterRepositoryMock;

    @BeforeEach
    public void setUp() {
        rosterRepositoryMock = mock(InMemoryRosterRepository.class);
        studentService = new StudentService();
        setPrivateField(studentService, "rosterRepository", rosterRepositoryMock);

    }
    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSaveRoster() throws IOException {
        String username = "testUser";
        List<Student> students = new ArrayList<>();
        students.add(new Student("John Doe"));
        students.add(new Student("Jane Smith"));
        studentService.saveRoster(students, username);
        verify(rosterRepositoryMock).saveStudents(students, username);
    }
    @Test
    public void testCheckStudentExists() throws IOException {
        List<Student> students = new ArrayList<>();
        students.add(new Student("John Doe"));
        students.add(new Student("Jane Smith"));
        when(rosterRepositoryMock.getStudents("testUser")).thenReturn(students);
        assertTrue(studentService.checkStudentExists("testUser", "John Doe"));
        assertTrue(studentService.checkStudentExists("testUser", "Jane Smith"));
        assertFalse(studentService.checkStudentExists("testUser", "Alice Johnson"));
        verify(rosterRepositoryMock, times(3)).getStudents("testUser");
    }
    @Test
    public void testAddStudent() throws IOException {
        String username = "testUser";
        Student newStudent = new Student("John Doe");
        List<Student> existingStudents = new ArrayList<>();
        when(rosterRepositoryMock.getStudents(username)).thenReturn(existingStudents);
        studentService.addStudent(newStudent, username);
        verify(rosterRepositoryMock).getStudents(username);
        verify(rosterRepositoryMock).saveStudents(existingStudents, username);
        assertEquals(1, existingStudents.size());
        assertEquals(newStudent, existingStudents.get(0));
    }
    @Test
    public void testGetAllStudent() throws IOException {
        String username = "testUser";
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("John Doe"));
        expectedStudents.add(new Student("Jane Smith"));
        when(rosterRepositoryMock.getStudents(username)).thenReturn(expectedStudents);
        List<Student> actualStudents = studentService.getAllStudent(username);

        verify(rosterRepositoryMock).getStudents(username);
        assertEquals(expectedStudents.size(), actualStudents.size());
        assertEquals(expectedStudents, actualStudents);
    }
    @Test
    public void testRemoveStudent() throws IOException {
        // 准备测试数据
        String username = "testUser";
        String studentNameToRemove = "John Doe";
        List<Student> students = new ArrayList<>();
        students.add(new Student("John Doe"));
        students.add(new Student("Jane Smith"));

        when(rosterRepositoryMock.getStudents(username)).thenReturn(students);

        studentService.removeStudent(studentNameToRemove, username);
        verify(rosterRepositoryMock).getStudents(username);
        verify(rosterRepositoryMock).saveStudents(students, username);
        assertEquals(1, students.size());
        assertFalse(students.get(0).getLegalName().equals(studentNameToRemove));
    }
    @Test
    public void testEditStudentDisplayName() throws IOException {
        // 准备测试数据
        String username = "testUser";
        String legalName = "John Doe";
        String newDisplayName = "Johnny";

        List<Student> students = new ArrayList<>();
        students.add(new Student("John Doe"));
        students.add(new Student("Jane Smith"));

        // 设置mock对象的行为
        when(rosterRepositoryMock.getStudents(username)).thenReturn(students);

        // 执行editStudentDisplayName方法
        studentService.editStudentDisplayName(legalName, newDisplayName, username);

        // 验证getStudents方法的调用
        verify(rosterRepositoryMock).getStudents(username);
        // 验证saveStudents方法的调用
        verify(rosterRepositoryMock).saveStudents(students, username);
        // 验证学生的显示名称是否被正确修改
        assertEquals(newDisplayName, students.get(0).getDisPlayName());
    }
    @Test
    public void testEditStudentDisplayName_StudentNotFound() throws IOException {
        // 准备测试数据
        String username = "testUser";
        String legalName = "Nonexistent Student";
        String newDisplayName = "New Name";

        List<Student> students = new ArrayList<>();
        students.add(new Student("John Doe"));
        students.add(new Student("Jane Smith"));

        // 设置mock对象的行为
        when(rosterRepositoryMock.getStudents(username)).thenReturn(students);

        // 执行editStudentDisplayName方法，尝试编辑一个不存在的学生
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.editStudentDisplayName(legalName, newDisplayName, username);
        });

        // 验证getStudents方法的调用
        verify(rosterRepositoryMock).getStudents(username);
        // 验证saveStudents方法未被调用，因为学生不存在
        verify(rosterRepositoryMock, never()).saveStudents(anyList(), eq(username));
    }

//    @Test
//    public void testCheckStudentExists_Exception() throws IOException {
//        String username = "testUser";
//        String studentName = "John Doe";
//        when(rosterRepositoryMock.getStudents(username)).thenThrow(IOException.class);
//        assertDoesNotThrow(() -> {
//            boolean result = studentService.checkStudentExists(username, studentName);
//            assertFalse(result);
//        });
//        verify(rosterRepositoryMock).getStudents(username);
//    }
}
