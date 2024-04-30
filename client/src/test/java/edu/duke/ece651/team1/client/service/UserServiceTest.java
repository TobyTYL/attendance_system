package edu.duke.ece651.team1.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.*;

import edu.duke.ece651.team1.client.model.*;
import org.springframework.web.client.*;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserSession userSession;
    @BeforeEach
    void setUp() {
        userSession = UserSession.getInstance();
        userSession.setHost("localhost");
        userSession.setPort("8080");
    }

    @Test
    public void testAuthenticate_Success() {
        String username = "user";
        String password = "pass";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "sessionToken=abcd1234");
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Success", headers, HttpStatus.OK);
        String url = "http://localhost:8080/api/login";
        when(restTemplate.exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);
        String result = userService.authenticate(username, password);
        assertEquals("Success", result);
    }

    @Test
    public void testAuthenticate_Failure() {
        String username = "user";
        String password = "wrongpass";
        String url = "http://localhost:8080/api/login";
        when(restTemplate.exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        String result = userService.authenticate(username, password);
        assertEquals("loginFailed", result);
    }
    @Test
    public void testAuthenticate_UnexpectedError_client() {
        String username = "user";
        String password = "wrongpass";
        String url = "http://localhost:8080/api/login";
        when(restTemplate.exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        assertThrows(RuntimeException.class, () -> userService.authenticate(username, password));
    }

    @Test
    public void testAuthenticate_UnexpectedError() {
        String username = "user";
        String password = "pass";
        String url = "http://localhost:8080/api/login";
        when(restTemplate.exchange(eq(url), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RestClientException("Service unavailable"));
        assertThrows(RuntimeException.class, () -> userService.authenticate(username, password));
    }
}
