package edu.duke.ece651.team1.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import edu.duke.ece651.team1.server.model.CustomUserDetails;
import edu.duke.ece651.team1.data_access.*;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.*;;

/*
 * This class provides services for user-related operations, such as creating user accounts.
 */
@Service
// Repository for managing user details
public class UserService implements UserDetailsService {
    // @Autowired
    // private InMemoryUserRepository inMemoryUserRepository;
    // @Autowired
    private PasswordEncoder passwordEncoder;
    private UserDao userDao = new UserDaoImp();
    private ProfessorDao professorDao = new ProfessorDaoImp();
    private StudentDao studentDao = new StudentDaoImp();
    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * Creates a new user account with the specified username and password.
     *
     * @param username the username of the new user
     * 
     * @param password the password of the new user
     */
    public void createUserProfessor(String username, String password,String role) {
        User user = new User(username,passwordEncoder.encode(password),role);
        userDao.addUser(user);
        Professor professor = new Professor(userDao.findUserByUsername(username).getUserId());
        professorDao.addProfessor(professor);
    }

    public void createUserStudent(String username, String password,String role,String legalName, String displayName,String email) {
        User user = new User(username,passwordEncoder.encode(password),role);
        userDao.addUser(user);
        long userId = userDao.findUserByUsername(username).getUserId();
        Student student = new Student(legalName,displayName,email);
        studentDao.addStudent(student,userId);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if(user!=null){
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
