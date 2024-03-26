package edu.duke.ece651.team1.shared;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AttendanceStatusTest {
    @Test
    public void testGetStatus(){
        // Test PRESENT status
        assertEquals("Present", AttendanceStatus.PRESENT.getStatus());

        // Test ABSENT status
        assertEquals("Absent", AttendanceStatus.ABSENT.getStatus());

        // Test TARDY status
        assertEquals("Tardy", AttendanceStatus.TARDY.getStatus());
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString("Unknown"));
    }
}
