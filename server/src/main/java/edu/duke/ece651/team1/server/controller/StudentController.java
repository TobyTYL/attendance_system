package edu.duke.ece651.team1.server.controller;
import org.springframework.web.bind.annotation.*;
import edu.duke.ece651.team1.shared.Student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @GetMapping("/allStudents")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> students = new ArrayList<>();
        Student huidan = new Student("yitiao","huidan_tan18@163.com");
        Student zhecheng = new Student("zhecheng","huidan_tan18@163.com");
        students.add(huidan);
        students.add(zhecheng);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
   
}
