package edu.duke.ece651.team1.server.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailNotificationTest {
    @Autowired
    JavaMailSender emailSender;
    @Test
    public void testSendEmail() throws GeneralSecurityException, IOException,MessagingException{
        EmailNotification email = new EmailNotification();
        // EmailNotification.sendEmail("attendancemanagementsever@gmail.com", "huidan_tan18@163.com");
        email.notify("hi", "huidan_tan18@163.com");
        assertTrue(true);
    }
 

}
