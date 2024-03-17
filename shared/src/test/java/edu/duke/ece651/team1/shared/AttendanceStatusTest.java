package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
  @Test
  public void test_getStatus() {
    assertEquals(AttendanceStatus.PRESENT.getStatus(), "Present");
    assertEquals(AttendanceStatus.ABSENT.getStatus(), "Absent");
    assertEquals(AttendanceStatus.TARDY.getStatus(), "Tardy");
  }

}
