package edu.duke.ece651.team1.client.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AttendanceSummaryTest {
    @Test
    public void testAttendanceSummaryConstructor() {
        String line = "StudentName1: Attended 5/10 sessions (50.00% attendance rate), Tardy Count: 2.";
        AttendanceSummary summary = new AttendanceSummary(line);

        assertEquals("StudentName1", summary.getDisplayName());
        assertEquals(5, summary.getAttendedCount());
        assertEquals(10, summary.getTotalCount());
        assertEquals(2, summary.getTardyCount());
        assertEquals(3, summary.getAbsentCount()); // 10 - 5 - 2 = 3
        assertEquals(50.00, summary.getAttendanceRate(), 0.01); // delta is 0.01 for floating point comparison
    }

    @Test
    public void testNormalConstructor() {
        String displayName = "huidan";
        AttendanceSummary summary = new AttendanceSummary("huidan", 1, 2, 0, 1, 50.00);
        assertEquals(displayName, displayName);

    }

    @Test
    public void testException() {
        String line = "Invalid line format";
        assertThrows(IllegalArgumentException.class, () -> {
            new AttendanceSummary(line);
        });
    }

    @Test
    public void testTwoParameterConstructor(){
        String line = "Total Attendance: 5/10 (50.00% attendance rate), Tardy Count: 2";
        AttendanceSummary summary = new AttendanceSummary(line,"huidan");
        assertEquals("huidan", summary.getDisplayName());
        assertEquals(5, summary.getAttendedCount());
        assertEquals(10, summary.getTotalCount());
        assertEquals(2, summary.getTardyCount());
        assertEquals(3, summary.getAbsentCount());
        assertEquals(50.00, summary.getAttendanceRate(), 0.01); // delta is 0.01 for floating point comparison


    }

}
