package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.User;
import edu.duke.ece651.team1.user_admin_app.view.StudentView;

import edu.duke.ece651.team1.shared.Student;

public class StudentController {
    private final StudentDao studentDao;
    // legal name, email, display name
    private final UserDao userDao;
    BufferedReader inputReader;
    final PrintStream out;
    StudentView studentView;
    private PasswordEncoder passwordEncoder;

    public StudentController(BufferedReader inputReader, PrintStream out) {
        this.inputReader = inputReader;
        this.out = out;
        this.studentView = new StudentView(inputReader, out);
        this.studentDao = new StudentDaoImp();
        this.userDao = new UserDaoImp();
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    public void startStudentMenu() throws IOException {
        while (true) {
            try {
                studentView.showStudentMenu();
                String option = studentView.readStudentOption();
                if (option.equals("add")) {
                    addStudent();
                } else if (option.equals("remove")) {
                    removeStudent();
                } else if (option.equals("update")) {
                    //todo: updateStudent();
                } else {
                    //back to main menu
                    return;
                }
            } catch (IllegalArgumentException e) {
                out.println("Invalid option for Student Management Menue");
            }
        }
    }

    private void addStudent() throws IOException {
        String studentName = studentView.readStudentName();
        String displayName = studentView.readStudentDisplayName();
        String email = studentView.readStudentEmail();
        // "student" 搞一个常量，默认密码搞常量
        User user = new User(studentName, passwordEncoder.encode("passw0rd"), "Student");
        int uid = userDao.addUser(user);
        Student newStudent = new Student(uid, studentName, displayName, email);
        if (studentDao.checkStudentExists(studentName)) {
            out.println("Student Already exists!");
            return;
        }
        studentDao.addStudent(newStudent);
        out.println("Student added successfully.");
    }

    private void removeStudent() throws IOException {
        String studentName = studentView.readStudentName();
        Optional<Student> optionalStudent = studentDao.findStudentByName(studentName);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Integer studentId = student.getStudentId();
            if (studentId != null) {
                studentDao.removeStudent(student);
                userDao.removeUser(student.getUserId());
                out.println("Student removed successfully.");
            } else {
                out.println("Student ID is null.");
            }
        } else {
            out.println("Student not found.");
        }
    }



}