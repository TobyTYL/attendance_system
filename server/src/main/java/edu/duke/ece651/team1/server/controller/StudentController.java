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


@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

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
