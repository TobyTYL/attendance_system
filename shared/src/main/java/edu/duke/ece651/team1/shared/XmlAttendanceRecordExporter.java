package edu.duke.ece651.team1.shared;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * Implements the AttendanceRecordExporter for exporting attendance records to XML files.
 * This class generates XML documents representing attendance records and writes them to specified files.
 */
public class XmlAttendanceRecordExporter implements AttendanceRecordExporter {

    /**
     * Converts an AttendanceRecord into an XML Document.
     * @param record The AttendanceRecord to be converted.
     * @return A Document object representing the attendance record in XML format.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     */
    public Document convertToXmlFormat(AttendanceRecord record) throws ParserConfigurationException {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.newDocument();

    // Root element of the XML document.
    Element rootElement = doc.createElement("attendanceRecord");
    doc.appendChild(rootElement);

    // Session date element.
    Element dateElement = doc.createElement("sessionDate");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    dateElement.appendChild(doc.createTextNode(record.getSessionDate().format(formatter)));
    rootElement.appendChild(dateElement);

    // Entries element containing individual attendance entries.
    Element entriesElement = doc.createElement("entries");
    rootElement.appendChild(entriesElement);
    for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
        Element entryElement = doc.createElement("entry");
        Element studentElement = doc.createElement("student");
        createStudentElement(doc,entry.getKey(),studentElement);
        // studentElement.appendChild(createStudentElement(doc, entry.getKey(),studentElement));  
        Element statusElement = doc.createElement("AttendanceStatus");
        statusElement.appendChild(doc.createTextNode(entry.getValue().getStatus()));
        entryElement.appendChild(studentElement);
        entryElement.appendChild(statusElement);
        entriesElement.appendChild(entryElement);
    }
    return doc;
}
 /**
     * Appends student information to a provided parent XML element.
     * This includes the student's legal name, display name, and email as child elements.
     *
     * @param doc The XML document to which the student element will be added.
     * @param student The student whose information is to be serialized into XML.
     * @param studentElement The parent element to which student information elements are appended.
     */  
private void createStudentElement(Document doc, Student student,Element studentElement) {
    // Element studentElement = doc.createElement("student");
    Element legalNameElement = doc.createElement("legalName");
    legalNameElement.appendChild(doc.createTextNode(student.getLegalName()));
    studentElement.appendChild(legalNameElement);
    Element displayNameElement = doc.createElement("displayName");
    displayNameElement.appendChild(doc.createTextNode(student.getDisPlayName()));
    studentElement.appendChild(displayNameElement);
    Element emailElement = doc.createElement("email");
    emailElement.appendChild(doc.createTextNode(student.getEmail()));
    studentElement.appendChild(emailElement);
    
    // return studentElement;
}
/**
     * Writes the XML document to a file at the specified location.
     * This method creates a file with the given filename and writes the XML structure,
     * representing an attendance record, to it.
     *
     * @param doc The XML document to be written to file.
     * @param filename The name of the file (without extension) to write the XML data.
     * @param filePath The path where the file will be saved.
     * @throws TransformerException If there is an issue during the transformation process to write the XML.
     * @throws IOException If there is an I/O error creating or writing to the file.
     */
public void saveDocumentToFile(Document doc, String filename, String filePath) throws TransformerException, IOException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    DOMSource source = new DOMSource(doc);
    File file = new File(filePath, filename + ".xml");
    StreamResult result = new StreamResult(file);
    transformer.transform(source, result);
}


    /**
     * Implements the export functionality to serialize an AttendanceRecord to XML format
     * and save it as a file. This method utilizes convertToXmlFormat to serialize the record,
     * and saveDocumentToFile to write the XML to disk.
     *
     * @param record The AttendanceRecord to be exported.
     * @param filename The filename under which the XML document should be saved.
     * @param filePath The directory path where the file should be saved.
     * @throws IOException If there's an error during file creation, writing, or if a
     * serialization or transformation issue occurs.
     */
    @Override
    public void exportToFile(AttendanceRecord record, String filename, String filePath) throws IOException {
      try {
        Document doc = convertToXmlFormat(record);
        saveDocumentToFile(doc, filename, filePath);
      } catch (ParserConfigurationException | TransformerException e) {
        throw new IOException("Error exporting attendance record to XML because: " + e.getMessage(), e);
      }
    }
  
}
