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
/**
 * This class provides services for managing notification preferences of students.
 */
@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private NotificationPreferenceDao notificationDao = new NotificationPreferenceDaoImp();
    /**
     * Updates the notification preference of a student for a course.
     *
     * @param studentId The ID of the student.
     * @param courseId  The ID of the course.
     * @param preference The notification preference.
     */
    public void updateNotificationPreference(int studentId, int coursId, boolean preference){
        notificationDao.updateNotificationPreference(studentId, coursId, preference);
    }
    /**
     * Retrieves the notification preference of a student for a course.
     *
     * @param studentId The ID of the student.
     * @param courseId  The ID of the course.
     * @return A JSON string containing the notification preference.
     */
    public String getNotificationPreference(int studentId, int courseId){
        NotificationPreference preference=notificationDao.findNotificationPreferenceByStudentIdAndClassId(studentId, courseId);
        Gson gson = new Gson();
        Map<String, Object> notificationInfo = new HashMap<>();
        notificationInfo.put("ReceiveNotifications", preference.isReceiveNotifications());
        return gson.toJson(notificationInfo);
    }
   
}
