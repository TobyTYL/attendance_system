// package edu.duke.ece651.team1.shared;

// import static org.junit.jupiter.api.Assertions.*;
// import java.time.LocalDate;
// import org.junit.jupiter.api.Test;

// public class AttendanceEntryTest {
//   @Test
//   public void test_create_and_update_attendance_entry() {
//     LocalDate testDate = LocalDate.of(2024, 3, 17);
//     AttendanceEntry testEntry = new AttendanceEntry(testDate, AttendanceStatus.PRESENT);
//     assertEquals(testDate, testEntry.getDate());
//     assertEquals(AttendanceStatus.PRESENT, testEntry.getStatus());

//     testEntry.updateStatus(AttendanceStatus.ABSENT);
//     assertEquals(AttendanceStatus.ABSENT, testEntry.getStatus());
//     LocalDate newDate = LocalDate.of(2024, 3, 18);
//     testEntry.updateDate(newDate);
//     assertEquals(newDate, testEntry.getDate());                     
       
//   }
// }
