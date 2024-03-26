package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttendanceViewTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  
  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }
  
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }
  BufferedReader readerMock = Mockito.mock(BufferedReader.class);
  when(readerMock.readLine()).thenReturn("Your input here");
  
  @Test
  public void testShowTakeAttendanceMenu() {
    AttendanceView view = new AttendanceView(readerMock, System.out);
    view.showTakeAttendanceMenu("2022-03-15");
    assertTrue(outContent.toString().contains("Take Attendance for session: 2022-03-15"));
  }
}
