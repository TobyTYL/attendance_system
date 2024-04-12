package edu.duke.ece651.team1.server.controller;

import org.springframework.web.bind.annotation.*;

import edu.duke.ece651.team1.server.service.StudentService;
import edu.duke.ece651.team1.shared.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The StudentController class manages student-specific functionalities such as updating and retrieving notification preferences.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
     /**
     * Updates a student's notification preference for a specific class.
     *
     * @param studentId The ID of the student whose notification preference is to be updated.
     * @param classId The ID of the class for which the notification setting is updated.
     * @param preference The new notification preference (true for enabled, false for disabled).
     * @return ResponseEntity indicating the success or failure of the update operation.
     */
    @PostMapping("/notification/{studentId}/{classId}")
    public ResponseEntity<String> getAllStudent(@PathVariable int studentId, @PathVariable int classId, @RequestParam(value = "preference") boolean preference) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            studentService.updateNotificationPreference(studentId, classId, preference);
            return new ResponseEntity<>("Sucessfully update your notification preference", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error update notification preference: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Retrieves the notification preference for a student regarding a specific class.
     *
     * @param studentId The ID of the student whose notification preference is being queried.
     * @param classId The ID of the class for which the notification preference is queried.
     * @return ResponseEntity containing the notification preference status or an error message.
     */
    @GetMapping("/notification/{studentId}/{classId}")
    public ResponseEntity<String> getMethodName(@PathVariable int studentId, @PathVariable int classId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            String info=studentService.getNotificationPreference(studentId, classId);
            return new ResponseEntity<>(info, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error get notification preference: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
}
