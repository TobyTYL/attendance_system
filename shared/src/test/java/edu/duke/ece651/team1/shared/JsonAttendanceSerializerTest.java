package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonAttendanceSerializerTest {
    JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
    AttendanceRecord record = new AttendanceRecord();
    List<Student> students = new ArrayList<>();
    
   
    @Test
    public void testSerialize() {
        Student student1 = new Student("John", "Doe","john.com");
        Student student2 = new Student("Alice", "Smith","alice.com");
        students.add(student1);
        students.add(student2);
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        record.markAbsent(student2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = LocalDate.now().format(formatter);
        String expected ="{\"sessionDate\":\""+now+"\",\"Entries\":{\"John\":{\"Display Name\":\"Doe\",\"Email\":\"john.com\",\"Attendance status\":\"Present\"},\"Alice\":{\"Display Name\":\"Smith\",\"Email\":\"alice.com\",\"Attendance status\":\"Absent\"}}}";
        assertEquals(expected, serializer.serialize(record));
    }
    @Test
    public void testSerializeWithRecordId() {
        // Create AttendanceRecord and Students
        AttendanceRecord record = new AttendanceRecord(LocalDate.parse("2024-04-10"));
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1, "John", "Doe", "john.doe@example.com",null);
       

        students.add(student1);
       
        // Initialize the record with the list of students and set attendance
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        

        // Set a non-null recordId on the AttendanceRecord object
        int recordId = 1; // Example record ID
        record.setRecordId(recordId); // Method to set record ID

        // Define formatter for date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = LocalDate.now().format(formatter);

        // Expected JSON string should include the "Record Id"
        String expected = "{\"Record Id\":1,\"sessionDate\":\"" + "2024-04-10" + "\",\"Entries\":{" +
        "\"John\":{\"student Id\":1,\"Display Name\":\"Doe\",\"Email\":\"john.doe@example.com\",\"Attendance status\":\"Present\"}" +
        "}}";

        // Serialize the record and assert that it matches the expected JSON string
        assertEquals(expected, serializer.serialize(record));
        
    }
    @Test
    public void testDeserialize(){
        Student student1 = new Student(1,"John", "Doe","john.com",null);
        Student student2 = new Student(2,"Alice", "Smith","alice.com",null);
        students.add(student1);
        students.add(student2);
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        record.markAbsent(student2);
        String serilizedString = serializer.serialize(record);
        AttendanceRecord record2 = serializer.deserialize(serilizedString);
        assertEquals(serilizedString, serializer.serialize(record2));
    }

    @Test
    public void testDeserializeWithId(){
        Student student1 = new Student(1,"John", "Doe","john.com",null);
        Student student2 = new Student(2,"Alice", "Smith","alice.com",null);
        students.add(student1);
        students.add(student2);
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        record.markAbsent(student2);
        record.setRecordId(1);
        String serilizedString = serializer.serialize(record);
        AttendanceRecord record2 = serializer.deserialize(serilizedString);
        assertEquals(serilizedString, serializer.serialize(record2));
    }

}
