package edu.duke.ece651.team1.client.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificarionViewTest {
    private BufferedReader mockedInputReader;
    private PrintStream mockedOut;
    private NotificarionView notificationView;

    @BeforeEach
    void setup() {
        mockedInputReader = mock(BufferedReader.class);
        mockedOut = mock(PrintStream.class);
        notificationView = new NotificarionView(mockedInputReader, mockedOut);
    }

    @Test
    public void testDisplayNotification() {
        notificationView.displayNotification(true, "ECE651");
        verify(mockedOut).println("You are currently managing notifications for: ECE651.");
        verify(mockedOut).println("Current notification setting: true");
        verify(mockedOut).println("Would you like to Disable notifications for this course? (yes/no):");
    }
    private void testDisplayNotificationAndPrompt(boolean notify, String userInput, boolean expectedResult) throws IOException {
        when(mockedInputReader.readLine()).thenReturn(userInput);
        notificationView.displayNotification(notify, "Test Class");
        verify(mockedOut).println("You are currently managing notifications for: Test Class.");
        verify(mockedOut, atLeastOnce()).println(contains(notify ? "Disable" : "Enable"));
    }

    @Test
    void testNotifyEnabledUserSaysYes() throws IOException {
        testDisplayNotificationAndPrompt(true, "yes", true);
    }

    @Test
    void testNotifyEnabledUserSaysNo() throws IOException {
        testDisplayNotificationAndPrompt(true, "no", false);
    }

    @Test
    void testNotifyDisabledUserSaysYes() throws IOException {
        testDisplayNotificationAndPrompt(false, "yes", true);
    }

    @Test
    void testNotifyDisabledUserSaysNo() throws IOException {
        testDisplayNotificationAndPrompt(false, "no", false);
    }

    @Test
    public void testReadNotificationOptionYes() throws IOException {
        when(mockedInputReader.readLine()).thenReturn("yes");
        assertTrue(notificationView.readNotificationOption());
    }

    @Test
    public void testReadNotificationOptionNo() throws IOException {
        when(mockedInputReader.readLine()).thenReturn("no");
        assertFalse(notificationView.readNotificationOption());
    }

    @Test
public void testShowSuccessUpdateMessage() {
    notificationView.showSuccessUpdateMessage("ECE651", true);
    verify(mockedOut).println("Congrat! You successfully update your ECE651's Notification preference to Enabled");
    notificationView.showSuccessUpdateMessage("ECE651", false);
    verify(mockedOut).println("Congrat! You successfully update your ECE651's Notification preference to Disabled");

   
   
}


}
