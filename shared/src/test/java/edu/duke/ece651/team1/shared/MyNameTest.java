package edu.duke.ece651.team1.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MyNameTest {
  @Test
  public void test_getName() {
    assertEquals("team1", MyName.getName());
  }

}
