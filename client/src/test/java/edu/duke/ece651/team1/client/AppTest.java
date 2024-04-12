// package edu.duke.ece651.team1.client;

// import org.junit.jupiter.api.Test;
// import edu.duke.ece651.team1.client.view.*;
// import org.junit.jupiter.api.*;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.web.client.RestTemplate;
// import static org.mockito.Mockito.*;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintStream;
// import java.util.*;
// import org.springframework.core.ParameterizedTypeReference;
// import org.mockito.MockedStatic;
// import org.mockito.Mockito;
// import edu.duke.ece651.team1.client.model.*;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import edu.duke.ece651.team1.client.controller.*;
// import java.net.InetAddress;

// @ExtendWith(MockitoExtension.class)
// class AppTest {
//   @Mock
//   private BufferedReader inputReader;
//   @Mock
//   private PrintStream out;
//   @Mock
//   private ApplicationController mockedController;
//   @Mock
//   private UserSession userSession;

//   @BeforeEach

//   @Test
//   void test_main() throws IOException {
//     when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(String.class)))
//         .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
//     assertThrows(RuntimeException.class, () -> controller.handleLogin("testUser", "wrongPass"));

//   }
// }
