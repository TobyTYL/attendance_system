package edu.duke.ece651.team1.server.scheduler;

import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.management.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.duke.ece651.team1.server.controller.SecurityController;
import edu.duke.ece651.team1.server.model.AttendanceManager;
import edu.duke.ece651.team1.server.model.EmailNotification;
import edu.duke.ece651.team1.server.model.NotificationService;
import edu.duke.ece651.team1.data_access.Attendance.*;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDao;
import edu.duke.ece651.team1.data_access.Enrollment.EnrollmentDaoImpl;
import edu.duke.ece651.team1.data_access.Notification.*;
import edu.duke.ece651.team1.data_access.Section.SectionDao;
import edu.duke.ece651.team1.data_access.Section.SectionDaoImpl;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.data_access.Course.*;
import java.sql.*;
import java.util.Set;
import edu.duke.ece651.team1.shared.*;
import java.util.stream.Collectors;

/**
 * Schedules and sends weekly attendance reports for students using
 * {@link NotificationService}.
 * Initializes with adding {@link EmailNotification} to the notification system
 * and schedules
 * the report distribution every Saturday at 2:00 AM. It generates reports based
 * on attendance
 * records and student rosters, sending personalized emails to each student.
 *
 */
@Component
public class ReportScheduler {
    /**
     * Service for managing notifications. Initialized with a default instance.
     */
    private NotificationService notification = new NotificationService();
    private   Logger logger = LoggerFactory.getLogger(SecurityController.class);
    private SectionDao sectionDao = new SectionDaoImpl();
    private EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
    private StudentDao studentDao = new StudentDaoImp();
    private NotificationPreferenceDao notificationPreferenceDao = new NotificationPreferenceDaoImp();

    /**
     * Initializes the {@link NotificationService} by adding an
     * {@link EmailNotification} observer.
     * Logs the outcome of the operation, reporting success or the reason for
     * failure. This method is
     * automatically called after bean instantiation
     */
    @PostConstruct
    public void initializeNotification() {
        try {
            notification.addNotification(new EmailNotification());
            logger.info("add notification successful");
        } catch (Exception e) {
            logger.info("unable to add email notification because " + e.getMessage());
        }

    }
    /**
     * Retrieves the roster of students for a given section.
     * 
     * @param sectionId the unique identifier of the section
     * @return a set of students enrolled in the specified section
     */
    private Set<Student> getRoster(int sectionId){
        List<Student> students = enrollmentDao.getEnrollmentsBySectionId(sectionId).stream()
                .map(enrollment -> studentDao.findStudentByStudentID(enrollment.getStudentId()).get())
                .collect(Collectors.toList());
        Set<Student> roster = new HashSet<>(students);
        return roster;
    }
     /**
     * Retrieves the attendance manager for a section, responsible for compiling attendance records.
     * 
     * @param section the section for which the attendance manager is required
     * @return an instance of AttendanceManager if records are available, otherwise null
     */
    private AttendanceManager getAttendanceManager(Section section) throws SQLException{
        int sectionId = section.getSectionId();
        int courseId = section.getClassId();
        List<AttendanceRecord> records = AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId);
        if (records.isEmpty()) {
           return null;
        }
        AttendanceManager manager = new AttendanceManager(getRoster(sectionId), records);
        return manager;
    }
     /**
     * Checks whether a student prefers to receive notifications for a specific course.
     * 
     * @param studentId the unique identifier of the student
     * @param courseId the unique identifier of the course
     * @return true if the student prefers to receive notifications, otherwise false
     */
    private boolean getNotificationPreference(int studentId, int courseId){
        NotificationPreference preference = notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(studentId, courseId);
        return preference.isReceiveNotifications();
    }
    /**
     * Distributes the attendance report to all students in a section based on their notification preferences.
     * 
     * @param sectionId the unique identifier of the section
     * @param courseId the unique identifier of the course
     * @param manager the AttendanceManager containing the attendance data
     */
    private void handoutReport(int sectionId,int courseId,AttendanceManager manager){
        for (Student student : getRoster(sectionId)) {
            int studentId = student.getStudentId();
            if(!getNotificationPreference(studentId, courseId)){
                continue;
            }
            String report = manager.generateReport(student,true);
            notification.notifyObserver(report, student.getEmail());

        }
    }

    /**
     * Generates and sends weekly attendance reports for all students. Scheduled to
     * run
     * automatically every Saturday at 2:00 AM. It retrieves user names from the
     * {@link InMemoryUserRepository}, then for each user, fetches the student
     * roster and
     * attendance records. If attendance records exist, it generates a report for
     * each student
     * using {@link AttendanceManager} and sends it via {@link NotificationService}.
     *
     * If no attendance records are found for a user, that user is skipped. Errors
     * encountered
     * during report generation or sending are logged.
     */
    @Scheduled(cron = "0 36 20 * * SUN")
    public void sendWeeklyReport() {
        logger.info("begain scheduler");
        List<Section> sections = sectionDao.getAllSections();
        for (Section section : sections) {
            int sectionId = section.getSectionId();
            int courseId = section.getClassId();
            try {
                AttendanceManager manager = getAttendanceManager(section);
                if(manager==null){
                    continue;
                }
                handoutReport(sectionId, courseId, manager);
                logger.info("successfully send email to all students for section: " + sectionId);
            } catch (Exception e) {
                logger.info("error happen sending email: " + e.getMessage());

            }
        }

    }

}
