package edu.duke.ece651.team1.client.model.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class CSVHandlerTest {

    @Test
    void testReadFile() {
        // Obtain input stream to the test.txt file
        InputStream inputStream = getClass().getResourceAsStream("/test.txt");

        // Check if the resource file was found
        if (inputStream == null) {
            throw new AssertionError("test.txt resource not found");
        }

        // Wrap the input stream with InputStreamReader and BufferedReader to read line by line
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            // Read each line from the file and print it to System.out
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

        // Check if the resource file was found
        if (inputStream == null) {
            throw new AssertionError("sample_student.csv resource not found");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        // Read each line from the file and print it to System.out
        Relation r = CSVHandler.readFromCSVwithHeader(Tuple.of("student_name", "student_id", "student_email"), Tuple.of(Tuple.STRING, Tuple.LONG, Tuple.STRING),  reader);
        System.out.println("read csv was successful, result: ");
        System.out.println(r);
    }

    @Test
    public void readCSVtest2() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/sample_student.csv");

        // Check if the resource file was found
        if (inputStream == null) {
            throw new AssertionError("sample_student.csv resource not found");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        // Read each line from the file and print it to System.out
        Relation r = CSVHandler.readFromCSVwithHeader( Tuple.of(Tuple.STRING, Tuple.LONG, Tuple.STRING),  reader);
        System.out.println("read csv was successful, result: ");
        System.out.println(r);
    }

    @Test
    public void writeTestFile() throws IOException {
        String data = "Line 1" + System.lineSeparator() +"Line 2";

        // Get the path to the 'resources' directory in the build folder
        Path resourceDirectory = Paths.get("build", "resources", "test");
        Files.createDirectories(resourceDirectory); // Ensure the directory exists

        // Define the file path
        Path filePath = resourceDirectory.resolve("written_file.txt");

        // Create the file or overwrite it if it already exists
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(data);
        }

        // Confirm the file now exists and print the path
        if (Files.exists(filePath)) {
            System.out.println("File written successfully to: " + filePath);
        } else {
            System.out.println("Failed to write file.");
        }
    }

    //    @Disabled
    @Test
    public void writeTest1() throws IOException {
        Tuple columnNames = Tuple.of("student name", "id", "gender");
        Tuple template = Tuple.of(Tuple.STRING, Tuple.LONG, Tuple.CHARACTER);
        Tuple line1 = Tuple.of("chen", 456L, 'M');
        Tuple line2 = Tuple.of("hanx", 123L, 'M');
        Path resourceDirectory = Paths.get("build", "resources", "test");
        Files.createDirectories(resourceDirectory); // Ensure the directory exists
        Relation r = new Relation(columnNames, template);
        r.add(line1);
        r.add(line2);

        BufferedWriter writer;

        // Define the file path
        Path filePath = resourceDirectory.resolve("written_file1.txt");
        writer = Files.newBufferedWriter(filePath);

        // void writeToCSVwithHeader(Writer writer, Relation r, Iterable<Object> columnNamesIterable)
        CSVHandler.writeToCSVwithHeader(writer, r, List.of("custom name", "custom id", "custom gender"));
        System.out.println("csv export was successful");

        CSVHandler.writeToCSVwithHeader(writer, r, null);

        CSVHandler.writeToCSVwithoutHeader(writer, r);

    }
}