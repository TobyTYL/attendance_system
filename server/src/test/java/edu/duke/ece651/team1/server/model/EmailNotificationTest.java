package edu.duke.ece651.team1.server.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import javax.mail.Session;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;

public class EmailNotificationTest {
    @Mock
    private Gmail mockedGmail;

    @Mock
    private Gmail.Users mockedUsers;

    @Mock
    private Gmail.Users.Messages mockedMessages;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    private EmailNotification emailNotification;

    private void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        emailNotification = new EmailNotification(mockedGmail);
        when(mockedGmail.users()).thenReturn(mockedUsers);
        when(mockedUsers.messages()).thenReturn(mockedMessages);
        Message mockMessageResponse = new Message();
        mockMessageResponse.setId("mockMessageId");
        Gmail.Users.Messages.Send mockSend = mock(Gmail.Users.Messages.Send.class);
        when(mockSend.execute()).thenReturn(mockMessageResponse);
        when(mockedMessages.send(anyString(), any(Message.class))).thenReturn(mockSend);
    }

    @Test
    public void testCreateEmail() throws Exception {
        String to = "to@example.com";
        String from = "from@example.com";
        String subject = "Test Subject";
        String bodyText = "This is a test email body.";
        MimeMessage email = EmailNotification.createEmail(to, from, subject, bodyText);
        assertEquals(from, ((InternetAddress) email.getFrom()[0]).getAddress());
        assertEquals(to, ((InternetAddress) email.getRecipients(javax.mail.Message.RecipientType.TO)[0]).getAddress());
        assertEquals(subject, email.getSubject());
        assertEquals(bodyText, email.getContent().toString().trim());
    }

    @Test
    public void testSendMessage() throws Exception {
        setUp();
        String to = "to@example.com";
        String from = "from@example.com";
        String subject = "Test Subject";
        String bodyText = "This is a test email body.";
        String userId = "me";
        MimeMessage mimeMessage = EmailNotification.createEmail(to, from, subject, bodyText);
        EmailNotification.sendMessage(mockedGmail, userId, mimeMessage);
        verify(mockedMessages).send(any(String.class), messageCaptor.capture());
        Message sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage.getRaw());
        byte[] emailBytes = Base64.decodeBase64(sentMessage.getRaw());
        ByteArrayInputStream bais = new ByteArrayInputStream(emailBytes);
        Session session = Session.getDefaultInstance(System.getProperties(), null);
        MimeMessage sentMimeMessage = new MimeMessage(session, bais);
        assertEquals(from, InternetAddress.toString(sentMimeMessage.getFrom()));
        assertEquals(to, InternetAddress.toString(sentMimeMessage.getRecipients(javax.mail.Message.RecipientType.TO)));
        assertEquals(subject, sentMimeMessage.getSubject());
        assertTrue(String.valueOf(sentMimeMessage.getContent()).contains(bodyText));
    }
  
    @Test
    public void testNotify() throws Exception {
        setUp();
        String testRecipient = "test@example.com";
        String testMessage = "This is a test email.";
        emailNotification.notify(testMessage, testRecipient);
        verify(mockedMessages).send(eq("me"), messageCaptor.capture());
        Message sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertNotNull(sentMessage.getRaw());
    }

   

}
