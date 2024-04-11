package edu.duke.ece651.team1.user_admin_app.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainMenuViewTest {
    private BufferedReader inputReader;
    private PrintStream output;
    private MainMenuView mainMenuView;
    private ProfessorView professorView;


    @BeforeEach
    void setUp() {
        String input = "Professor Name\n"; // 模拟用户输入
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        inputReader = new BufferedReader(new InputStreamReader(inputStream));

        // 创建 ProfessorView 实例
        professorView = new ProfessorView(inputReader, System.out);
        output = System.out;
    }
    @Test
    void testShowMainMenu() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        mainMenuView = new MainMenuView(null, printStream);

        mainMenuView.showMainMenu();

        String expectedOutput = "Please select an option to begin:\n" +
                "1. Manage Professor Users\n" +
                "2. Manage Student Users\n" +
                "3. Exit\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testReadMainOption_ProfessorsOption() throws IOException {
        String input = "1\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        mainMenuView = new MainMenuView(inputReader, output);
        assertEquals("professors", mainMenuView.readMainOption());
    }

    @Test
    void testReadMainOption_StudentsOption() throws IOException {
        String input = "2\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        mainMenuView = new MainMenuView(inputReader, output);
        assertEquals("students", mainMenuView.readMainOption());
    }

    @Test
    void testReadMainOption_ExitOption() throws IOException {
        String input = "3\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        mainMenuView = new MainMenuView(inputReader, output);
        assertEquals("exit", mainMenuView.readMainOption());
    }

    @Test
    void testReadMainOption_InvalidOptionThenValidOption() throws IOException {
        String input = "invalid\n2\n";
        inputReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        mainMenuView = new MainMenuView(inputReader, output);
        assertEquals("students", mainMenuView.readMainOption());
    }

    @Test
    void testReadProfessorName() throws IOException {
        // 调用 readProfessorName 方法
        String name = professorView.readProfessorName();


        // 验证返回的教授名字是否符合预期
        assertEquals("Professor Name", name);
    }
}
