package edu.duke.ece651.team1.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceEntryTest {

    @Test
    void testConstructorAndGetterMethods() {
        Integer studentId = 1;
        Integer attendanceRecordId = 10;
        AttendanceStatus status = AttendanceStatus.PRESENT;
        AttendanceEntry entry = new AttendanceEntry(studentId, attendanceRecordId, status);
        assertNull(entry.getEntryId()); 
        assertEquals(studentId, entry.getStudentId());
        assertEquals(attendanceRecordId, entry.getAttendanceRecordId());
        assertEquals(status, entry.getStatus());
    }

    @Test
    void testSetAndGetEntryId() {
        AttendanceEntry entry = new AttendanceEntry(1, 10, AttendanceStatus.PRESENT);
        Integer entryId = 100;
        entry.setEntryId(entryId);
        assertEquals(entryId, entry.getEntryId());
    }
}
