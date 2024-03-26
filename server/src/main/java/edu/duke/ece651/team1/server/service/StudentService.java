package edu.duke.ece651.team1.server.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import edu.duke.ece651.team1.server.controller.StudentController;
import edu.duke.ece651.team1.server.repository.InMemoryRosterRepository;
import edu.duke.ece651.team1.shared.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StudentService {
    // private final String studentInfoPath;
    @Autowired
    private  InMemoryRosterRepository rosterRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public void saveRoster(List<Student> students,String username) throws IOException{
        rosterRepository.saveStudents(students, username);
    }

    public boolean checkStudentExists(String username,String studentName) {
        try{
            List<Student> students = rosterRepository.getStudents(username);
            for (Student student : students) {
                if (student.getLegalName().equals(studentName)) {
                    return true; 
                }
            }
        }catch(Exception e){
            logger.error("error happen in check student exists");
        }
        return false;
    }

    public void addStudent(Student student, String username) throws IOException {
        List<Student> students = rosterRepository.getStudents(username);
        students.add(student);
        rosterRepository.saveStudents(students, username);

    }

    public List<Student> getAllStudent(String username) throws IOException{
        return rosterRepository.getStudents(username);
    }

    public void removeStudent(String studentName, String username) throws IOException {
        List<Student> students = rosterRepository.getStudents(username);
        students.removeIf(student -> student.getLegalName().equals(studentName));
        rosterRepository.saveStudents(students, username);
    }

    public void editStudentDisplayName(String legalName, String newDisplayName, String username) throws IOException {
        List<Student> students = rosterRepository.getStudents(username);
        boolean found = false;
        for (Student student : students) {
            if (student.getLegalName().equals(legalName)) {
                student.updateDisplayName(newDisplayName);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Student with legal name " + legalName + " not found");
        }
        rosterRepository.saveStudents(students, username);
    }

}

