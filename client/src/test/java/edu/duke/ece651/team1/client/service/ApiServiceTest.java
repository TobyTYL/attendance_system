package edu.duke.ece651.team1.client.service;

import static org.mockito.Mockito.*;
import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.client.model.UserSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserSession userSession;

    private ApiService apiService;

    @BeforeEach
    void setUp() {
        apiService.restTemplate = restTemplate;
    }

    @Test
    void testExecuteGetRequest() {
        String testUrl = "http://example.com";
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Success", HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);
        String result = apiService.executeGetRequest(testUrl, responseType);
        assertEquals("Success", result);
    }

    @Test
    void testExecutePostPutRequest() {
        String testUrl = "http://example.com/resource";
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Created", HttpStatus.CREATED);
        when(restTemplate.exchange(eq(testUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(responseType)))
                .thenReturn(responseEntity);
                apiService.executePostPutRequest(testUrl, "requestBody", responseType, true);
        verify(restTemplate).exchange(eq(testUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(responseType));
    }

    @Test
    void testExecuteGetRequest_NotFound() {
        String testUrl = "http://example.com/notfound";
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> apiService.executeGetRequest(testUrl, responseType));
        assertTrue(exception.getMessage().contains("Resource not found"));
    }

    @Test
    void testExecuteGetRequest_ServerError() {
        String testUrl = "http://example.com/error";
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Exception exception = assertThrows(RuntimeException.class,
                () -> apiService.executeGetRequest(testUrl, responseType));

        assertTrue(exception.getMessage().contains("Server error occurred"));
    }

    @Test
    void testExecutePostPutRequest_PUT() {
        String testUrl = "http://example.com/resource/update";
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Updated", HttpStatus.OK);
        when(restTemplate.exchange(eq(testUrl), eq(HttpMethod.PUT), any(HttpEntity.class), eq(responseType)))
                .thenReturn(responseEntity);
                apiService.executePostPutRequest(testUrl, "requestBodyToUpdate", responseType, false);
        verify(restTemplate).exchange(eq(testUrl), eq(HttpMethod.PUT), any(HttpEntity.class), eq(responseType));
    }

    @Test
    void testExecutePostPutRequest_PutOperation_Exception() {
        String testUrl = "http://example.com/resource";
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        String requestBody = "{\"key\": \"updatedValue\"}";
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(responseType))).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        Exception exception = assertThrows(RuntimeException.class,
                () -> apiService.executePostPutRequest(testUrl, requestBody, responseType, false));
        assertTrue(exception.getMessage().contains("Server error occurred"));
    }

}
