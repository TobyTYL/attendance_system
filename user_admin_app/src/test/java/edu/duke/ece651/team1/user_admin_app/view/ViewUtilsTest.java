package edu.duke.ece651.team1.user_admin_app.view;

import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ViewUtilsTest {

    @Test
    void testGetUserOption_ValidInput() throws IOException {
        String input = "3\n";
        BufferedReader inputReader = new BufferedReader(new StringReader(input));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        int option = ViewUtils.getUserOption(inputReader, printStream, 5);
        assertEquals(3, option);
    }

    @Test
    void testGetUserOption_InvalidInput() {
        String input = "invalid\n";
        BufferedReader inputReader = new BufferedReader(new StringReader(input));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        assertThrows(NumberFormatException.class, () -> {
            ViewUtils.getUserOption(inputReader, printStream, 5);
        });
    }
    @Test
    void testGetUserOption_InvalidInput_Null() {
        String input = "\n";
        BufferedReader inputReader = new BufferedReader(new StringReader(input));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        assertThrows(EOFException.class, () -> {
            ViewUtils.getUserOption(inputReader, printStream, 5);
        });
    }
    @Test
    void testGetUserOption_OutOfRangeInput() {
        String input = "6\n";
        BufferedReader inputReader = new BufferedReader(new StringReader(input));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        assertThrows(IllegalArgumentException.class, () -> {
            ViewUtils.getUserOption(inputReader, printStream, 5);
        });
    }


}
