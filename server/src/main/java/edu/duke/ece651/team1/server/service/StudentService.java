package edu.duke.ece651.team1.server.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLDataException;
import java.sql.SQLException;

import edu.duke.ece651.team1.data_access.Notification.*;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Notification.NotificationPreferenceDaoImp;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
// import edu.duke.ece651.team1.server.controller.StudentController;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import javax.management.Notification;

import edu.duke.ece651.team1.shared.*;
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
/*
 * This class provides services for managing student-related operations, such as saving, retrieving, adding, removing,
 * and editing student information.
 */

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private NotificationPreferenceDao notificationDao = new NotificationPreferenceDaoImp();

    public void updateNotificationPreference(int studentId, int coursId, boolean preference){
        notificationDao.updateNotificationPreference(studentId, coursId, preference);
    }

    public String getNotificationPreference(int studentId, int courseId){
        NotificationPreference preference=notificationDao.findNotificationPreferenceByStudentIdAndClassId(studentId, courseId);
        Gson gson = new Gson();
        Map<String, Object> notificationInfo = new HashMap<>();
        notificationInfo.put("ReceiveNotifications", preference.isReceiveNotifications());
        return gson.toJson(notificationInfo);
    }
    /*
     * Saves the roster of students for the specified user.
     *
     * @param students the list of students to be saved
     * 
     * @param username the username of the user
     * 
     * @throws IOException if an I/O error occurs while saving the roster
     */
    // public void saveRoster(List<Student> students,String username) throws
    // IOException{
    // rosterRepository.saveStudents(students, username);
    // }
    /*
     * Checks if a student with the given name exists in the roster of the specified
     * user.
     *
     * @param username the username of the user
     * 
     * @param studentName the legal name of the student
     * 
     * @return true if the student exists in the roster, false otherwise
     */
    // public boolean checkStudentExists(String username,String studentName) {
    // try{
    // List<Student> students = rosterRepository.getStudents(username);
    // for (Student student : students) {
    // if (student.getLegalName().equals(studentName)) {
    // return true;
    // }
    // }
    // }catch(Exception e){
    // logger.error("error happen in check student exists");
    // }
    // return false;
    // }
    /*
     * Adds a new student to the roster of the specified user.
     *
     * @param student the student to be added
     * 
     * @param username the username of the user
     * 
     * @throws IOException if an I/O error occurs while adding the student
     */
    // public void addStudent(Student student, String username) throws IOException {
    // List<Student> students = rosterRepository.getStudents(username);
    // students.add(student);
    // rosterRepository.saveStudents(students, username);

    // }
    /*
     * Retrieves all students from the roster of the specified user.
     *
     * @param username the username of the user
     * 
     * @return the list of all students in the roster
     * 
     * @throws IOException if an I/O error occurs while retrieving the students
     */
    // public List<Student> getAllStudent(int sectionId)  {
    //     List<Enrollment> enrollments = enrollmentDao.getEnrollmentsBySectionId(sectionId);
    //     List<Student> students = enrollments.stream()
    //             .map(enrollment -> studentDao.findStudentByStudentID(enrollment.getStudentId()).get())
    //             .collect(Collectors.toList());
    //     return students;

    // }
    /*
     * Removes the student with the specified name from the roster of the specified
     * user.
     *
     * @param studentName the legal name of the student to be removed
     * 
     * @param username the username of the user
     * 
     * @throws IOException if an I/O error occurs while removing the student
     */
    // public void removeStudent(String studentName, String username) throws
    // IOException {
    // List<Student> students = rosterRepository.getStudents(username);
    // students.removeIf(student -> student.getLegalName().equals(studentName));
    // rosterRepository.saveStudents(students, username);
    // }
    // /*
    // * Edits the display name of the student with the specified legal name in the
    // roster of the specified user.
    // *
    // * @param legalName the legal name of the student to be edited
    // * @param newDisplayName the new display name for the student
    // * @param username the username of the user
    // * @throws IOException if an I/O error occurs while editing the student
    // display name
    // * @throws IllegalArgumentException if the student with the specified legal
    // name is not found
    // */

    // public void editStudentDisplayName(String legalName, String newDisplayName,
    // String username) throws IOException {
    // List<Student> students = rosterRepository.getStudents(username);
    // boolean found = false;
    // for (Student student : students) {
    // if (student.getLegalName().equals(legalName)) {
    // student.updateDisplayName(newDisplayName);
    // found = true;
    // break;
    // }
    // }
    // if (!found) {
    // throw new IllegalArgumentException("Student with legal name " + legalName + "
    // not found");
    // }
    // rosterRepository.saveStudents(students, username);
    // }

}
