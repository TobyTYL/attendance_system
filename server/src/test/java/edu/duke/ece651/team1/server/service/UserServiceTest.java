
package edu.duke.ece651.team1.server.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.data_access.User.*;
import edu.duke.ece651.team1.data_access.Professor.*;
import edu.duke.ece651.team1.data_access.Student.*;
import edu.duke.ece651.team1.server.model.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDao userDao;

    @Mock
    private ProfessorDao professorDao;

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private UserService userService;
    String username = "username";
    String password = "password";
    String encodedPassword = "encodedPassword";
   

    @Test
    void createUserProfessor_CreatesNewUser() {
       
        String role = "Professor";
        int userId = 1;
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userDao.addUser(any(User.class))).thenReturn(userId);
        when(userDao.findUserByUsername(username)).thenReturn(new User(userId, username, encodedPassword));
        userService.createUserProfessor(username, password, role);
        verify(userDao, times(1)).addUser(any(User.class));
        verify(professorDao, times(1)).addProfessor(any(Professor.class));
    }

    @Test
    void createUserStudent_CreatesNewUser() {
        String role = "Student";
        String legalName = "huidan";
        String displayName = "tan";
        String email = "huidan@example.com";
        int userId = 1;
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userDao.addUser(any(User.class))).thenReturn(userId);
        userService.createUserStudent(username, password, role, legalName, displayName, email);
        verify(userDao, times(1)).addUser(any(User.class));
        verify(studentDao, times(1)).addStudent(any(Student.class));
    }

    @Test
    void loadUserByUsername_ReturnsCustomUserDetails() {
        String username = "userUsername";
        User mockUser = new User(1, username, "password", "ROLE_USER");
        when(userDao.findUserByUsername(username)).thenReturn(mockUser);
        UserDetails userDetails = userService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_ThrowsUsernameNotFoundException() {
        String username = "nonexistentUser";
        when(userDao.findUserByUsername(username)).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }
}
