package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
public class StudentTest {
  @Test
  public void test_Student_Constructor() {
    Student student = new Student("Jimmy", "Jim", "jimmy@duke.edu");
    assertEquals("Jimmy", student.getLegalName());
    assertEquals("Jim", student.getDisPlayName());
    assertEquals("jimmy@duke.edu", student.getEmail());
  }
}
