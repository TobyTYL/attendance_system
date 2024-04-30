//package edu.duke.ece651.team1.enrollmentApp;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//import edu.duke.ece651.team1.enrollmentApp.controller.ApplicationController;
//
//public class AppTest {
//  @Test
//  public void testRun_NoUncaughtExceptions() throws Exception {
//      BufferedReader inputReader = mock(BufferedReader.class);
//      PrintStream out = mock(PrintStream.class);
//      ApplicationController applicationController = mock(ApplicationController.class);
//
//      App app = new App(inputReader, out, applicationController);
//      app.run();
//
//      verify(applicationController, times(1)).startApplication();
//  }
//}
