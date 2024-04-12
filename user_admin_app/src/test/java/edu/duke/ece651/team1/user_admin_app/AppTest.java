//package edu.duke.ece651.team1.user_admin_app;
//
//import edu.duke.ece651.team1.user_admin_app.App;
//import edu.duke.ece651.team1.user_admin_app.controller.ApplicationController;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//class AppTest {
//
//    @Test
//    void testAppMain() {
//        String input = "input1\ninput2\n";
//        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inputStream);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
//        PrintStream out = new PrintStream(outputStream);
//        ApplicationController controller = new ApplicationController(inputReader, out);
//        ApplicationController mockedController = mock(ApplicationController.class);
//        App.main(new String[]{});
//        String expectedOutput = "Start Application!\n";
//        assertEquals(expectedOutput, outputStream.toString());
//        verify(mockedController).startApplication();
//    }
//}
