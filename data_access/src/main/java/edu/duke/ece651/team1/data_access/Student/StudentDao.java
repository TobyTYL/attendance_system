package edu.duke.ece651.team1.data_access.Student;

import edu.duke.ece651.team1.shared.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    void addStudent(Student student);
    void removeStudent(Student student);
    Optional<Student> findStudentByStudentID(int studentID);
    Optional<Student> findStudentByUserID(int userID);
    List<Student> getAllStudents();
    boolean checkStudentExists(String name);
    Optional<Student> findStudentByName(String studentName);
    void updateStudent(Student student);
}
