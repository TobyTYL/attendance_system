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
    public void testExport(){

    }
    @Test
    public void testDeserialize(){
        Student student1 = new Student("John", "Doe","john.com");
        Student student2 = new Student("Alice", "Smith","alice.com");
        students.add(student1);
        students.add(student2);
        record.initializeFromRoaster(students);
        record.markPresent(student1);
        record.markAbsent(student2);
        String serilizedString = serializer.serialize(record);
        AttendanceRecord record2 = serializer.deserialize(serilizedString);
        assertEquals(serilizedString, serializer.serialize(record2));
    }
}
