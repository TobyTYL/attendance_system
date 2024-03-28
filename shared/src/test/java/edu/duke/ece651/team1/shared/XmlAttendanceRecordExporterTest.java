package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import static org.xmlunit.assertj.XmlAssert.assertThat;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.io.Files;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import static org.mockito.Mockito.*;

public class XmlAttendanceRecordExporterTest {
    XmlAttendanceRecordExporter exporter = new XmlAttendanceRecordExporter();
    AttendanceRecord record = new AttendanceRecord();
    List<Student> students = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String documentToString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void convertToXmlFormatTest() throws ParserConfigurationException{
        Student student1 = new Student("John", "Doe","john.com");
        Student student2 = new Student("Alice", "Smith","alice.com");
        students.add(student1);
        students.add(student2);
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        record.markAbsent(student2);
        String expectedXml = "<attendanceRecord>\n" +
        "    <sessionDate>"+LocalDateTime.now().format(formatter)+"</sessionDate>\n" + 
        "    <entries>\n" +
        "        <entry>\n" +
        "            <student>\n" +
        "                <legalName>John</legalName>\n" +
        "                <displayName>Doe</displayName>\n" +
        "                <email>john.com</email>\n" +
        "            </student>\n" +
        "            <AttendanceStatus>Present</AttendanceStatus>\n" +
        "        </entry>\n" +
        "        <entry>\n" +
        "            <student>\n" +
        "                <legalName>Alice</legalName>\n" +
        "                <displayName>Smith</displayName>\n" +
        "                <email>alice.com</email>\n" +
        "            </student>\n" +
        "            <AttendanceStatus>Absent</AttendanceStatus>\n" +
        "        </entry>\n" +
        "    </entries>\n" +
        "</attendanceRecord>";
        String actualXml = documentToString(exporter.convertToXmlFormat(record));
        // assertEquals(expectedXml,);
        assertThat(actualXml).and(expectedXml).ignoreWhitespace().areIdentical();
    }
    @TempDir
    Path tempDir;

    // Alternative method to read file content for older Java versions
    private static String readFileContent(File file) throws Exception {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    @Test
    public void testSaveDocumentToFile() throws Exception {
        // Setup and create a Document as before
        
        // Assuming the setup for the Document and AttendanceRecord goes here

        Document doc = exporter.convertToXmlFormat(record); // Use your method to create the Document
        String filename = "testAttendance";

        // Action
        exporter.saveDocumentToFile(doc, filename, tempDir.toString());

        // Verification
        File savedFile = tempDir.resolve(filename + ".xml").toFile();
        assertTrue(savedFile.exists(), "File should exist");

        String content = readFileContent(savedFile);
        assertNotNull(content, "File content should not be null");
        // Further content verification as necessary
    }

    @Test
    public void testExportToFile() throws Exception {
        // Setup
        // Assume the AttendanceRecord setup goes here
        
        String filename = "exportedAttendance";

        // Action
        exporter.exportToFile(record, filename, tempDir.toString());

        // Verification
        File exportedFile = tempDir.resolve(filename + ".xml").toFile();
        assertTrue(exportedFile.exists(), "Exported file should exist");

        String content = readFileContent(exportedFile);
        assertNotNull(content, "Exported file content should not be null");
        // Further content verification as necessary
    }
  @Test
  public void testExportToFileException() {
    // Setup
    AttendanceRecord record = new AttendanceRecord();
    XmlAttendanceRecordExporter exporter = new XmlAttendanceRecordExporter();
    
    // Assuming the setup for AttendanceRecord goes here...
    
    // Use an invalid path to induce IOException
    String invalidPath = "\0InvalidPath";
    
    // Action & Verification
    assertThrows(IOException.class, () -> {
        exporter.exportToFile(record, "testFile", invalidPath);
      }, "Expected exportToFile to throw IOException due to invalid file path");
  }

  //for testing file exception (return null)
}
