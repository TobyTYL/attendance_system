package edu.duke.ece651.team1.server.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import edu.duke.ece651.team1.server.service.CourseService;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import java.util.List;
/**
 * The CourseController class handles HTTP requests related to course management. It offers API endpoints
 * for retrieving courses taught by professors and registered by students.
 */
@RestController
@RequestMapping("/api/class")
public class CourseController {
    @Autowired
    CourseService courseService;
     /**
     * Retrieves information about all courses taught by a specific professor.
     *
     * @param professorId The identifier of the professor whose courses are being queried.
     * @return ResponseEntity containing a list of course information or an empty list if no courses are found.
     */
    @GetMapping("professor/allclasses/")
    public ResponseEntity<List<String>> getAllclassesInfoForProfessor(@RequestParam(value = "professorId") Integer professorId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<String> coursesinfo  = courseService.getTaughtCoursesInfoForProfessor(professorId);
            return new ResponseEntity<>(coursesinfo, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Retrieves information about all courses a student is registered in.
     *
     * @param studentId The identifier of the student whose course registrations are being queried.
     * @return ResponseEntity containing a list of course information or an empty list if no registrations are found.
     */
    @GetMapping("student/allclasses/")
    public ResponseEntity<List<String>> getAllclassesInfoForStudent(@RequestParam(value = "studentId") Integer studentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<String> coursesinfo  = courseService.getRegisteredCoursesInfoForStudent(studentId);
            return new ResponseEntity<>(coursesinfo, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
