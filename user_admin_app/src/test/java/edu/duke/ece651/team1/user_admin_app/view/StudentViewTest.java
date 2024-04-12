package edu.duke.ece651.team1.user_admin_app.view;

import edu.duke.ece651.team1.shared.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentViewTest {
    private BufferedReader inputReader;
    private ByteArrayOutputStream outputStream;
    private StudentView studentView;

    @BeforeEach
    void setUp() {
        String input = "Test Student\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        inputReader = new BufferedReader(new InputStreamReader(inputStream));

        // 创建 StudentView 实例
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        studentView = new StudentView(inputReader, printStream);
    }

    @Test
    void testShowStudentMenu() {
        studentView.showStudentMenu();
        String expectedOutput = "Please select an option:\n" +
                "1. Add a student\n" +
                "2. Remove a student\n" +
                "3. Update a student\n" +
                "4. Back to main menu\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testReadStudentOption_AddOption() throws IOException {
        String input = "1\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        studentView = new StudentView(inputReader, new PrintStream(outputStream));
        assertEquals("add", studentView.readStudentOption());
    }

    @Test
    void testReadStudentOption_RemoveOption() throws IOException {
        String input = "2\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        studentView = new StudentView(inputReader, new PrintStream(outputStream));
        assertEquals("remove", studentView.readStudentOption());
    }

    @Test
    void testReadStudentOption_UpdateOption() throws IOException {
        String input = "3\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        studentView = new StudentView(inputReader, new PrintStream(outputStream));
        assertEquals("update", studentView.readStudentOption());
    }

    @Test
    void testReadStudentOption_BackOption() throws IOException {
        String input = "4\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        studentView = new StudentView(inputReader, new PrintStream(outputStream));
        assertEquals("back", studentView.readStudentOption());
    }

    @Test
    void testReadStudentName() throws IOException {
        String name = studentView.readStudentName();
        assertEquals("Test Student", name);
        String expectedOutput = "Enter the name of the student:\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testReadStudentDisplayName() throws IOException {
        String displayName = studentView.readStudentDisplayName();
        assertEquals("Test Student", displayName);
        String expectedOutput = "Enter the display name of the student:\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testReadStudentEmail() throws IOException {
        String email = studentView.readStudentEmail();
        assertEquals("Test Student", email);
        String expectedOutput = "Enter the email of the student:\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayStudentList() {
        Iterable<Student> students = List.of(
                new Student(1, "John Doe", "John", "john@example.com"),
                new Student(2, "Jane Doe", "Jane", "jane@example.com")
        );

        studentView.displayStudentList(students);

        String expectedOutput = "Legal Name   Display Name   Email\n" +
                "=====================================\n" +
                "John Doe, John, john@example.com\n" +
                "Jane Doe, Jane, jane@example.com\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testShowSuccessAddDisplayNameMessage() {
        studentView.showSuccessAddDisplayNameMessage("Test Student", "status");
        String expectedOutput = "You successfully added Test Student display name to this class status\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testShowSuccessRemoveMessage() {
        studentView.showSuccessRemoveMessage("Test Student");
        String expectedOutput = "You successfully removed Test Student\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testShowStudentNotFoundMessage() {
        studentView.showStudentNotFoundMessage("action");
        String expectedOutput = "The student you want to action was not found\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
