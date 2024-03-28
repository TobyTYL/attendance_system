package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Predicate;
import java.io.EOFException;

public class ViewUtilsTest {
  @Test
    void testGetUserOptionValid() throws IOException {
        BufferedReader readerMock = mock(BufferedReader.class);
        PrintStream outMock = mock(PrintStream.class);
        when(readerMock.readLine()).thenReturn("1");

        int option = ViewUtils.getUserOption(readerMock, outMock, 3);
        assertEquals(1, option);
    }

    @Test
    void testGetUserOptionInvalid() throws IOException {
        BufferedReader readerMock = mock(BufferedReader.class);
        PrintStream outMock = mock(PrintStream.class);
        when(readerMock.readLine()).thenReturn("4", "1"); // First input invalid, second valid

        assertThrows(IllegalArgumentException.class, () -> ViewUtils.getUserOption(readerMock, outMock, 3));
    }

    @Test
    void testGetUserOptionEOFException() throws IOException {
        BufferedReader readerMock = mock(BufferedReader.class);
        PrintStream outMock = mock(PrintStream.class);
        when(readerMock.readLine()).thenReturn(null);

        assertThrows(EOFException.class, () -> ViewUtils.getUserOption(readerMock, outMock, 3));
    }

    @Test
    void testGetUserInputValid() throws IOException {
        BufferedReader readerMock = mock(BufferedReader.class);
        PrintStream outMock = new PrintStream(new ByteArrayOutputStream());
        when(readerMock.readLine()).thenReturn("valid input");
        Predicate<String> validationPredicate = input -> input.equals("valid input");

        String result = ViewUtils.getUserInput("Enter something: ", "Try again: ", readerMock, outMock, validationPredicate);
        assertEquals("valid input", result);
    }

    @Test
    void testGetUserInputInvalidThenValid() throws IOException {
        BufferedReader readerMock = mock(BufferedReader.class);
        when(readerMock.readLine()).thenReturn("invalid", "valid input"); // First input invalid, second valid
        PrintStream outMock = new PrintStream(new ByteArrayOutputStream());
        Predicate<String> validationPredicate = input -> input.equals("valid input");

        String result = ViewUtils.getUserInput("Enter something: ", "Try again: ", readerMock, outMock, validationPredicate);
        assertEquals("valid input", result);
    }

    @Test
    void testGetUserInputEOFException() throws IOException {
        BufferedReader readerMock = mock(BufferedReader.class);
        when(readerMock.readLine()).thenReturn(null); // Simulate end of stream
        PrintStream outMock = new PrintStream(new ByteArrayOutputStream());
        Predicate<String> validationPredicate = input -> true; // Any input is considered valid

        assertThrows(EOFException.class, () -> ViewUtils.getUserInput("Enter something: ", "Try again: ", readerMock, outMock, validationPredicate));
    }

}
