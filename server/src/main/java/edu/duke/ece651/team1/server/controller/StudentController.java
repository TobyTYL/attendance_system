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

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    /**
     * Retrieves a list of all students
     * @return A ResponseEntity containing the list of students, or an empty list if no students are found
     */
    @GetMapping("/allStudents/{sectionID}")
    public ResponseEntity<List<Student>> getAllStudent(@PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<Student> students  = studentService.getAllStudent(sectionID);
            if(students.isEmpty()){
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        
        }catch(Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // /**
    //  * Saves a roster of students
    //  * @param students The list of students to be saved
    //  * @return A ResponseEntity
    //  */
    // @PostMapping("/roster")
    // public ResponseEntity<String> postMethodName(@RequestBody List<Student> students) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     try{
    //         studentService.saveRoster(students, auth.getName());
    //         return new ResponseEntity<>("received", HttpStatus.OK);
    //     }catch(IOException e){
    //          return new ResponseEntity<>("error happen in save roster becaues "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    // /**
    //  * Checks if a student exists
    //  * @param studentName The name of the student to check
    //  * @return A ResponseEntity containing a boolean indicating whether the student exists or not
    //  */
    // @GetMapping("/student/exists/{studentName}")
    // public ResponseEntity<Boolean> checkStudentExists(@PathVariable String studentName) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     boolean exist= studentService.checkStudentExists(auth.getName(),studentName);
    //     return new ResponseEntity<>(exist, HttpStatus.OK);
    // }
    // /**
    //  * Adds a new student
    //  * @param student The student need to be added in the roster
    //  * @return A ResponseEntity indicating the result of the operation
    //  */
    // @PostMapping("/student/{studentName}")
    // public ResponseEntity<String> addStudent(@RequestBody Student student) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     try {
    //         studentService.addStudent(student, auth.getName());
    //     } catch (IOException e) {
    //         return new ResponseEntity<>("Failed to add student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     return new ResponseEntity<>("Student added successfully", HttpStatus.OK);
    // }
    // /**
    //  * Removes a student
    //  * @param studentName The name of the student to be removed
    //  * @return A ResponseEntity indicating the result of the operation
    //  */
    // @DeleteMapping("/student/{studentName}")
    // public ResponseEntity<String> removeStudent(@PathVariable String studentName) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     try {
    //         studentService.removeStudent(studentName, auth.getName());
    //     } catch (Exception e) {
    //         return new ResponseEntity<>("Failed to remove student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     return new ResponseEntity<>("Student removed successfully", HttpStatus.OK);
    // }
    // /**
    //  * Edits the display name of a student
    //  * @param studentName The name of the student whose display name is to be edited
    //  * @param newDisplayName The new display name for the student
    //  * @return A ResponseEntity indicating the result of the operation
    //  */
    // @PutMapping("/student/displayname/{studentName}/{newDisplayName}")
    // public ResponseEntity<String> editStudentDisplayName(@PathVariable String studentName, @PathVariable String newDisplayName) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     try {
    //         studentService.editStudentDisplayName(studentName, newDisplayName, auth.getName());
    //     } catch (IllegalArgumentException e) {
    //         return new ResponseEntity<>("Failed to edit student display name: " + e.getMessage(), HttpStatus.NOT_FOUND);
    //     }catch(IOException e){
    //         return new ResponseEntity<>("Failed to edit student display name: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     return new ResponseEntity<>("Student display name updated successfully", HttpStatus.OK);
    // }
}
