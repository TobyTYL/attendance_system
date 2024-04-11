//package edu.duke.ece651.team1.user_admin_app;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class AppTest {
//    @Test
//    public void testAppMain() {
//        // Mock System.out
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
//        System.setIn(inputStream);
//
//        App.main(new String[]{});
//
//        assertEquals("Start Application!\n", outputStream.toString());
//    }
//}
