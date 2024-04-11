package edu.duke.ece651.team1.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import edu.duke.ece651.team1.server.service.UserService;
import org.springframework.web.bind.annotation.*;
/**
 * The SecurityController class manages user registration for different roles such as professors and students.
 * It provides endpoints for registering users in the system.
 */
@RestController
@RequestMapping("/api")
public class SecurityController {
     
    @Autowired
    UserService userService;
    /**
     * Registers a new professor with a username and password.
     *
     * @param userName The username for the new professor account.
     * @param password The password for the new professor account.
     * @return ResponseEntity indicating the success or failure of the registration process.
     */
    @PostMapping("/signup/professor")
    public ResponseEntity<String> ProfessorRegister(@RequestParam(value = "username") String userName,
            @RequestParam(value = "password") String password) {
        try{
            userService.createUserProfessor(userName, password,"Professor");
        }catch(Exception e){
            return new ResponseEntity<String>("username has already been registered", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Congrat! You have successfully signed up", HttpStatus.CREATED);
    }
    /**
     * Registers a new student with detailed information including username, password, legal name, display name, and email.
     *
     * @param userName The username for the new student account.
     * @param password The password for the new student account.
     * @param legalName The legal name of the student.
     * @param displayName The display name of the student.
     * @param email The email address of the student.
     * @return ResponseEntity indicating the success or failure of the registration process.
     */
    @PostMapping("/signup/student")
    public ResponseEntity<String> StudentRegister(@RequestParam(value = "username") String userName,
            @RequestParam(value = "password") String password, @RequestParam(value = "legalname") String legalName,@RequestParam(value = "displayname") String displayName,@RequestParam(value = "email") String email) {
        try{
            userService.createUserStudent(userName, password,"Student",legalName,displayName,email);
        }catch(Exception e){
            return new ResponseEntity<String>("username has already been registered"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Congrat! You have successfully signed up", HttpStatus.CREATED);
    }

}
