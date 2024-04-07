//package edu.duke.ece651.team1.server.service;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.User;
//
//import edu.duke.ece651.team1.server.repository.InMemoryUserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class UserServiceTest {
//
//    @Mock
//    private InMemoryUserRepository inMemoryUserRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    public void testCreateUser() {
//        String username = "testuser";
//        String password = "testpassword";
//        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
//        userService.createUser(username, password);
//        verify(inMemoryUserRepository, times(1)).createUser(any(User.class));
//        verify(passwordEncoder, times(1)).encode(password);
//    }
//
//    @Test
//    public void testCreateUserWhenValidUsernameAndPasswordThenUserCreated() {
//        String username = "testuser";
//        String password = "testpassword";
//        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
//
//        userService.createUser(username, password);
//
//        verify(inMemoryUserRepository, times(1)).createUser(any(UserDetails.class));
//        verify(passwordEncoder, times(1)).encode(password);
//    }
//
//    @Test
//    public void testCreateUserWhenNullUsernameThenUserNotCreated() {
//        String password = "testpassword";
//        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null, password));
//
//        verify(inMemoryUserRepository, never()).createUser(any(UserDetails.class));
//        verify(passwordEncoder, never()).encode(password);
//    }
//
//    @Test
//    public void testCreateUserWhenNullPasswordThenUserNotCreated() {
//        String username = "testuser";
//        assertThrows(IllegalArgumentException.class, () -> userService.createUser(username, null));
//
//        verify(inMemoryUserRepository, never()).createUser(any(UserDetails.class));
//        verify(passwordEncoder, never()).encode(anyString());
//    }
//}
