package edu.duke.ece651.team1.user_admin_app.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import edu.duke.ece651.team1.data_access.User.UserDao;
import edu.duke.ece651.team1.data_access.User.UserDaoImp;
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
                    //removeStudent();
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
        User user = new User(studentName, passwordEncoder.encode("passw0rd"), "student");
        int uid = userDao.addUser(user);
        Student newStudent = new Student(uid, studentName, displayName, email);
        if (studentDao.checkStudentExists(studentName)) {
            out.println("Student Already exists!");
            return;
        }
        studentDao.addStudent(newStudent);
        out.println("Student added successfully.");

    }


//
//    public boolean checkStudentExists(String studentName) {
//        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                + "/api/students/student/exists/" + studentName;
//        HttpEntity<Student> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
//        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                requestEntity,
//                Boolean.class);
//        return responseEntity.getBody();
//
//    }
//
//
//    private void removeStudent() throws IOException {
//        String studentName = studentView.readStudentName();
//        if(!checkStudentExists(studentName)){
//            out.println("The Student you want to remove does not exists");
//            return;
//        }
//        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                + "/api/students/student/"+studentName;
//        HttpEntity<Void> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.DELETE,
//                requestEntity,
//                String.class);
//        if(responseEntity.getStatusCode()==HttpStatus.OK){
//            studentView.showSuccessRemoveMessage(studentName);
//        }else{
//            out.println("Remove Student: "+studentName+" failed");
//        }
//
//
//    }
//
//    private void editStudentDisplayName() throws IOException {
//        String studentName = studentView.readStudentName();
//        String newDisplayName = studentView.readStudentDisplayName();
//        // HttpHeaders headers = new HttpHeaders();
//        // headers.add("Cookie", UserSession.getInstance().getSessionToken());
//        HttpEntity<String> requestEntity = new HttpEntity<>(getSessionTokenHeaders());
//        String url = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
//                + "/api/students/student/displayname/" + studentName + "/" + newDisplayName;
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.PUT,
//                requestEntity,
//                String.class);
//        if (responseEntity.getStatusCode()==HttpStatus.OK) {
//            studentView.showSuccessEditNameMessage(studentName, newDisplayName);
//        }else if(responseEntity.getStatusCode()==HttpStatus.NOT_FOUND){
//            studentView.showStudentNotFoundMessage("edit dispaly name");
//        }else{
//            out.println("Error happen in server when try to edit display Name ");
//        }
//    }
//


}