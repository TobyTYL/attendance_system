package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

import org.junit.jupiter.api.Test;

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
}
