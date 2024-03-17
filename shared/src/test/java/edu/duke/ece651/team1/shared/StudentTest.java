package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
public class StudentTest {
  @Test
  public void test_Student_Constructor() {
    Student student = new Student("Jimmy", "Jim", "001", "jimmy@duke.edu");
    assertEquals("Jimmy", student.getLegalName());
    assertEquals("Jim", student.getDisplayName());
    assertEquals("001", student.getStudnetId());
    assertEquals("jimmy@duke.edu", student.getEmail());
  }
  @Test
  public void test_mark_attendance(){
    Student student = new Student("Jimmy", "Jim", "001", "jimmy@duke.edu");
    Course course = new Course(1L);
    LocalDate testDate = LocalDate.of(2024, 3, 17);
    AttendanceEntry entry = new AttendanceEntry(testDate, AttendanceStatus.PRESENT);

    student.markAttendance(course, entry);

    assertTrue(student.attendanceRecordMap.containsKey(course));
    assertTrue(student.attendanceRecordMap.get(course).contains(entry));
}       
}
