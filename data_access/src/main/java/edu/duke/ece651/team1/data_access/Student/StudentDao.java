package edu.duke.ece651.team1.data_access.Student;

import edu.duke.ece651.team1.shared.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    void addStudent(Student student, long userId);
    void removeStudent(long studentID);
    Optional<Student> findStudentByStudentID(long studentID);
    Optional<Student> findStudentByUserID(long userID);
    List<Student> getAllStudents();
}
