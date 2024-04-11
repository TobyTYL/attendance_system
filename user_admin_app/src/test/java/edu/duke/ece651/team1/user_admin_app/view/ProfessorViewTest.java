package edu.duke.ece651.team1.user_admin_app.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorViewTest {
    private BufferedReader inputReader;
    private PrintStream output;
    private ProfessorView professorView;

    @BeforeEach
    void setUp() {
        String input = "Professor Name\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        inputReader = new BufferedReader(new InputStreamReader(inputStream));

//        output = System.out;
        output = new PrintStream(new ByteArrayOutputStream()); // Capture output stream

        professorView = new ProfessorView(inputReader, output);
    }



    @Test
    void testShowProfessorMenu() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        professorView = new ProfessorView(null, printStream);

        professorView.showProfessorMenu();

        String expectedOutput = "Please select an option:\n" +
                "1. Add a professor\n" +
                "2. Remove a professor\n" +
                "3. Back to main menu\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testReadProfessorOption_AddOption() throws IOException {
        String input = "1\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        professorView = new ProfessorView(inputReader, output);
        assertEquals("add", professorView.readProfessorOption());
    }

    @Test
    void testReadProfessorOption_RemoveOption() throws IOException {
        String input = "2\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        professorView = new ProfessorView(inputReader, output);
        assertEquals("remove", professorView.readProfessorOption());
    }

    @Test
    void testReadProfessorOption_BackOption() throws IOException {
        String input = "3\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        professorView = new ProfessorView(inputReader, output);
        assertEquals("back", professorView.readProfessorOption());
    }

//    @Test
//    void testReadProfessorName() throws IOException {
//        // 调用 readProfessorName 方法
//        String name = professorView.readProfessorName();
//
//        // 验证输出了提示信息
//        String expectedOutput = "Enter the name of the professor:\n";
//        assertEquals(expectedOutput, output.toString());
//
//        // 验证返回的教授名字是否符合预期
//        assertEquals("Professor Name", name);
//    }

    @Test
    void testReadProfessorId_ValidInput() throws IOException {
        String input = "123\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        professorView = new ProfessorView(inputReader, output);
        assertEquals(123, professorView.readProfessorId());
    }

    @Test
    void testReadProfessorId_InvalidInputThenValidInput() throws IOException {
        String input = "invalid\n456\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        professorView = new ProfessorView(inputReader, output);
        assertEquals(456, professorView.readProfessorId());
    }
}
