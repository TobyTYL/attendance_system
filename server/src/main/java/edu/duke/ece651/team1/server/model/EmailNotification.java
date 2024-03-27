package edu.duke.ece651.team1.server.model;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
// import com.google.auth.oauth2.GoogleCredentials;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.FileNotFoundException;

/**
 * Implements {@link Notification} to send email notifications via the Gmail API.
 * This class encapsulates the functionality required to authenticate with Google's OAuth 2.0,
 * create email messages, and send them to specified recipients. It leverages the Gmail service
 * to send emails.
 *
 *
 * <p>It requires a credentials.json file obtained from Google Cloud Console configured for OAuth 2.0,
 * placed in the resources directory, and a tokens directory to store the OAuth tokens.</p>
 */

public class EmailNotification implements Notification {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static Gmail service;
     /**
     * Constructs a new {@code EmailNotification} instance, initializing the Gmail service
     * for sending emails. If the Gmail service has not been previously initialized, it performs
     * OAuth 2.0 authentication and sets up the service.
     */
    public EmailNotification() {
        if (service == null) {
            try {
                final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
                service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Alternative constructor that allows for dependency injection of a Gmail service instance,
     * facilitating testing
     *
     * @param gmail an instance of {@link Gmail} to use for email operations.
     */
    public EmailNotification(Gmail gmail){
        service = gmail;
    }
    /**
     * Fetches the Google API OAuth2 credentials. T.
     * 
     * @param HTTP_TRANSPORT The network transport to use for OAuth2 flow.
     * @return Credential object for authorized access to the Gmail API.
     * @throws IOException If there's an error loading the credentials file
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = EmailNotification.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(0).build(); // Let the system pick an
                                                                                             // available port
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
     /**
     * Sends an email notification to the specified recipient.
     * 
     * @param message   The message content of the email.
     * @param recipient The email address of the recipient.
     */
    @Override
    public void notify(String message, String recipient) {
        try {
            MimeMessage emailContent = createEmail(recipient, "attendancemanagementsever@gmail.com", "AttendanceNotification",
                    message); 
            sendMessage(service, "me", emailContent);
        } catch (Exception e) {
            System.err.println("Unable to send message to " + recipient + " because: " + e.getMessage());
        }
    }
     /**
     * Creates a MIME email message.
     * 
     * @param toEmailAddress The recipient's email address.
     * @param fromEmailAddress The sender's email address.
     * @param subject The subject line of the email.
     * @param bodyText The body text of the email.
     * @return A MimeMessage object ready to be sent.
     * @throws MessagingException If there's an error creating the email message.
     */
    public static MimeMessage createEmail(String toEmailAddress,
            String fromEmailAddress,
            String subject,
            String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(fromEmailAddress));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    /**
     * Sends the email message through the Gmail service.
     * 
     * @param service The Gmail service to use for sending the message.
     * @param userId The user ID of the sender (usually "me" for the authenticated user).
     * @param email The MimeMessage to be sent.
     * @return The sent message as a Message object.
     * @throws MessagingException If there's an error building the email message.
     * @throws IOException If there's an error communicating with the Gmail API.
     */
    public static Message sendMessage(Gmail service, String userId, MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        message = service.users().messages().send(userId, message).execute();
        System.out.println("Message id: " + message.getId());
        return message;
    }


}
