package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.time.LocalDate;

public class AttendanceRecordTest {
    AttendanceRecord record = new AttendanceRecord(LocalDate.now());
    Student huidan = new Student("huidan");
    Student zhecheng = new Student("zhecheng");

    @Test
    public void testInitializeAttendanceEntry() {
        record.initializeAttendanceEntry(zhecheng);
        record.initializeAttendanceEntry(huidan);
        assertEquals(record.getAttendanceEntry(zhecheng), AttendanceStatus.ABSENT);
        assertEquals(record.getAttendanceEntry(huidan), AttendanceStatus.ABSENT);
    }

    @Test
    public void testMarkPresent() {
        record.initializeAttendanceEntry(huidan);
        record.markPresent(huidan);
        assertEquals(record.getAttendanceEntry(huidan), AttendanceStatus.PRESENT);
    }

    @Test
    public void testMarkTardy() {
        record.initializeAttendanceEntry(zhecheng);
        record.markTardy(zhecheng);
        assertEquals(record.getAttendanceEntry(zhecheng), AttendanceStatus.TARDY);
    }

    @Test
    public void testNotinRecord() {
        assertThrows(IllegalArgumentException.class, () -> record.markPresent(huidan));
        assertThrows(IllegalArgumentException.class, () -> record.markTardy(zhecheng));

    }
    // @Test void testDisplayAttendace(){
    // Set<Student> roaster = new HashSet<>(Set.of(huidan,zhecheng));
    // record.initializeFromRoaster(roaster);
    // huidan.updateDisplayName("Rachel");
    // record.markPresent(huidan);
    // String expected = "Attendance Record for "+LocalDate.now()+"\n"+
    // "----------------------------\n"+
    // "Rachel: Present\n"+
    // "zhecheng: Absent\n"+
    // "----------------------------";
    // assertEquals(expected, record.displayAttendance());

    // }
    @Test
    public void testGetEntries() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student1 = new Student("123", "John Doe");
        Student student2 = new Student("456", "Jane Doe");

        record.initializeAttendanceEntry(student1);
        record.markPresent(student1);
        record.initializeAttendanceEntry(student2);
        record.markAbsent(student2);

        Map<Student, AttendanceStatus> entries = record.getEntries();

        // Verify that the entries map has the correct statuses
        assertEquals(AttendanceStatus.PRESENT, entries.get(student1));
        assertEquals(AttendanceStatus.ABSENT, entries.get(student2));

        // Verify that the map is unmodifiable
        assertThrows(UnsupportedOperationException.class,
                () -> entries.put(new Student("789", "New Student"), AttendanceStatus.TARDY));

        // Verify the map size to ensure no unintended entries are present
        assertEquals(2, entries.size());
    }

    @Test
    public void testSetRecordId() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.of(2024, 4, 10));
        int expectedId = 123;
        record.setRecordId(expectedId);
        assertEquals(expectedId, record.getRecordId(), "The recordId should match what was set");
    }

    @Test
    public void testAddAttendanceEntry() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.of(2024, 4, 10));
        Student student = new Student("John", "Doe", "john.doe@example.com");
        AttendanceStatus status = AttendanceStatus.PRESENT;
        record.addAttendanceEntry(student, status);
        assertTrue(record.getEntries().containsKey(student), "The student should be in the entries map");
        assertEquals(status, record.getEntries().get(student), "The student's status should be PRESENT");
    }

    @Test
    public void testToString() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.of(2024, 4, 10));
        record.setRecordId(123);
        Student student = new Student(1,"John", "Doe", "john.doe@example.com",null);
        record.addAttendanceEntry(student, AttendanceStatus.PRESENT);
        String expectedString = "AttendanceRecord [recordId=123, sessionDate=2024-04-10, entries={Student [studentId=1, legalName=John, disPlayName=Doe, email=john.doe@example.com]=PRESENT}]";
        String actualString = record.toString();
        assertEquals(expectedString, actualString);
    }

}
