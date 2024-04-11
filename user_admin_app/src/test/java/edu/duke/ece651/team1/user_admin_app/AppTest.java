//package edu.duke.ece651.team1.user_admin_app;
//
//import edu.duke.ece651.team1.user_admin_app.controller.ApplicationController;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//
//public class AppTest {
//    @Test
//    public void testRun_NoUncaughtExceptions() throws IOException {
//        BufferedReader inputReader = mock(BufferedReader.class);
//        PrintStream out = mock(PrintStream.class);
//        ApplicationController applicationController = mock(ApplicationController.class);
//        App app = new App();
//        app.main(new String[]{});
//        when(inputReader.readLine()).thenReturn("3");
//
//    }
//}
