package edu.duke.ece651.team1.client.model.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class CSVHandlerTest {

    @Test
    public void testReadFromCSVwithHeader_ValidData_CreatesCorrectRelation() throws IOException {
        String csvData = "Legal Name,Display Name,Email\nJohn Doe,John,john@example.com\nJane Smith,Jane,jane@example.com\n \n";
        BufferedReader reader = new BufferedReader(new StringReader(csvData));
        Iterable<Object> templateIterable = Arrays.asList(Tuple.STRING, Tuple.STRING, Tuple.STRING);
        Relation relation = CSVHandler.readFromCSVwithHeader(templateIterable, reader);
        assertNotNull(relation);
        assertEquals(2, relation.size());

    }

    @Test
    public void testReadFromCSVwithoutHeader_ValidData_CreatesCorrectRelation() throws IOException {
        String csvData = "John Doe,John,john@example.com\nJane Smith,Jane,jane@example.com\n\n";
        BufferedReader reader = new BufferedReader(new StringReader(csvData));
        Iterable<Object> columnNamesIterable = Arrays.asList("Legal Name", "Display Name", "Email");
        Iterable<Object> templateIterable = Arrays.asList(Tuple.STRING, Tuple.STRING, Tuple.STRING);
        Relation relation = CSVHandler.readFromCSVwithoutHeader(columnNamesIterable, templateIterable, reader);
        assertNotNull(relation);
        assertEquals(2, relation.size());
    }

    @Test
    void testReadFile() {
        InputStream inputStream = getClass().getResourceAsStream("/test.txt");
        if (inputStream == null) {
            throw new AssertionError("test.txt resource not found");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readCSVtest1() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/sample_student.csv");
        if (inputStream == null) {
            throw new AssertionError("sample_student.csv resource not found");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Relation r = CSVHandler.readFromCSVwithHeader(Tuple.of("student_name", "student_id", "student_email"),
                Tuple.of(Tuple.STRING, Tuple.LONG, Tuple.STRING), reader);
        System.out.println("read csv was successful, result: ");
        System.out.println(r);
    }

    @Test
    public void readCSVtest2() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/sample_student.csv");
        if (inputStream == null) {
            throw new AssertionError("sample_student.csv resource not found");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Relation r = CSVHandler.readFromCSVwithHeader(Tuple.of(Tuple.STRING, Tuple.LONG, Tuple.STRING), reader);
        System.out.println("read csv was successful, result: ");
        System.out.println(r);
    }

    @Test
    public void readCSVtest3() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/student1.csv");
        if (inputStream == null) {
            throw new AssertionError("sample_student.csv resource not found");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        // Read each line from the file and print it to System.out
        Relation r = CSVHandler.readFromCSVwithoutHeader(Tuple.of("col1", "col2"), Tuple.of(Tuple.STRING, Tuple.STRING),
                reader);
        System.out.println("read csv was successful, result: ");
        System.out.println(r);
        System.out.println("above: read from csv w/o header");
    }

    @Test
    public void testReadColumnNames_NoHeader_ThrowsException() {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        CSVHandler handler = CSVHandler.initFromReader(reader);
        assertThrows(IllegalArgumentException.class, handler::readColumnNames);
    }
    @Test
    void testReadColumnNames_NoValidHeader_ThrowsException() {
        String csvData = "\n\n"; // Only empty lines, no header
        StringReader reader = new StringReader(csvData);
        CSVHandler handler = CSVHandler.initFromReader(reader);

        assertThrows(NullPointerException.class, ()->CSVHandler.initFromReader(null));
      
    }
    @Test
    void readColumnNames_NoHeader_ThrowsException() throws IOException {
        // Create a Reader that simulates an empty or invalid CSV file.
        Reader reader = new StringReader("\n\n"); // Only empty lines.
        CSVHandler handler = CSVHandler.initFromReader(reader);
        // assertEquals(reader.getWriter(), handler);
        assertThrows(IllegalArgumentException.class, handler::readColumnNames);
    }

}