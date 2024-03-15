package edu.duke.ece651.team1.server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @Test
    void test_GetMessage() {
      App a = new App();
      assertEquals("Hello from the server for team1", a.getMessage());
    }
}

