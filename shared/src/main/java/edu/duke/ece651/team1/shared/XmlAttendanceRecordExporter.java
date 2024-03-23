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

public class XmlAttendanceRecordExporter implements AttendanceRecordExporter {


    public Document convertToXmlFormat(AttendanceRecord record) throws ParserConfigurationException {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.newDocument();
    Element rootElement = doc.createElement("attendanceRecord");
    doc.appendChild(rootElement);
    Element dateElement = doc.createElement("sessionDate");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    dateElement.appendChild(doc.createTextNode(record.getSessionDate().format(formatter)));
    rootElement.appendChild(dateElement);
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
public void saveDocumentToFile(Document doc, String filename, String filePath) throws TransformerException, IOException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    DOMSource source = new DOMSource(doc);
    File file = new File(filePath, filename + ".xml");
    StreamResult result = new StreamResult(file);
    transformer.transform(source, result);
}



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
