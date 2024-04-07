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
@RestController
@RequestMapping("/api/class")
public class CourseController {
    @Autowired
    CourseService courseService;
    @GetMapping("professor/allclasses/")
    public ResponseEntity<List<String>> getAllclassesInfoForProfessor(@RequestParam(value = "professorId") Integer professorId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<String> coursesinfo  = courseService.getTaughtCoursesInfoForProfessor(professorId);
            if(coursesinfo.isEmpty()){
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(coursesinfo, HttpStatus.OK);
        
        }catch(Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("student/allclasses/")
    public ResponseEntity<List<String>> getAllclassesInfoForStudent(@RequestParam(value = "studentId") Integer studentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<String> coursesinfo  = courseService.getRegisteredCoursesInfoForStudent(studentId);
            if(coursesinfo.isEmpty()){
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(coursesinfo, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
