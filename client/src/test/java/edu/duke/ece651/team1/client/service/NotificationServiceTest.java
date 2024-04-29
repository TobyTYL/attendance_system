package edu.duke.ece651.team1.client.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import org.springframework.core.ParameterizedTypeReference;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import edu.duke.ece651.team1.client.model.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private UserSession userSession;

    private MockedStatic<ApiService> mockedApiService;
    int studentId = 1;
    int classID = 123;
    String notificationUrl;
    String notificationUrl_update;

    @BeforeEach
    void setUp() {
        userSession = UserSession.getInstance();
        userSession.setHost("localhost");
        userSession.setPort("8080");
        userSession.setUid(studentId);
        mockedApiService = Mockito.mockStatic(ApiService.class);
        notificationUrl = String.format("http://%s:%s/api/students/notification/%d/%d",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, classID);
        notificationUrl_update = String.format("http://%s:%s/api/students/notification/%d/%d/?preference=%s",
                UserSession.getInstance().getHost(), UserSession.getInstance().getPort(), studentId, classID, true);
        when(ApiService.executeGetRequest(eq(notificationUrl), any(ParameterizedTypeReference.class)))
                .thenReturn("{\"ReceiveNotifications\": true}");
        mockedApiService.when(() -> ApiService.executePostPutRequest(
                eq(notificationUrl_update), isNull(), any(ParameterizedTypeReference.class),
                eq(true))).thenAnswer(invocation -> null);

    }

    @AfterEach
    public void tearDown() {
        mockedApiService.close();
    }

    @Test
    void testGetReceiveNotification() {
        boolean notificationPref = notificationService.getReceiveNotification(classID);
        assertTrue(notificationPref);
    }

    @Test
    void testUpdateNotification() {
        boolean preference = true;
        notificationService.updateNotification(preference, classID);
        mockedApiService.verify(() -> ApiService.executePostPutRequest(eq(notificationUrl_update), isNull(),
                any(ParameterizedTypeReference.class), eq(true)), times(1));
    }

}
