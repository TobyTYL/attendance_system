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
import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
import edu.duke.ece651.team1.server.repository.InMemoryRosterRepository;
import edu.duke.ece651.team1.server.repository.InMemoryUserRepository;
import java.util.Set;
import edu.duke.ece651.team1.shared.*;
/**
 * Schedules and sends weekly attendance reports for students using {@link NotificationService}.
 * Initializes with adding {@link EmailNotification} to the notification system and schedules
 * the report distribution every Saturday at 2:00 AM. It generates reports based on attendance
 * records and student rosters, sending personalized emails to each student.
 *
 */
@Component
public class ReportScheduler {
    /**
    * Service for managing notifications. Initialized with a default instance.
    */
    private NotificationService notification = new NotificationService();
    @Autowired
    private InMemoryUserRepository userRepository;
    @Autowired
    private InMemoryAttendanceRepository attendanceRepository;
    @Autowired
    private InMemoryRosterRepository rosterRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    /**
    * Initializes the {@link NotificationService} by adding an {@link EmailNotification} observer.
    * Logs the outcome of the operation, reporting success or the reason for failure. This method is
    * automatically called after bean instantiation
    */
    @PostConstruct
    public void initializeNotification() {
        try {
            notification.addNotification(new EmailNotification());
            logger.info("add notification successful");
        } catch (Exception e) {
            logger.info("unable to add email notification because "+e.getMessage());
        }

    }
    /**
    * Generates and sends weekly attendance reports for all students. Scheduled to run
    * automatically every Saturday at 2:00 AM. It retrieves user names from the
    * {@link InMemoryUserRepository}, then for each user, fetches the student roster and
    * attendance records. If attendance records exist, it generates a report for each student
    * using {@link AttendanceManager} and sends it via {@link NotificationService}.
     *
    * If no attendance records are found for a user, that user is skipped. Errors encountered
    * during report generation or sending are logged.
    */
    @Scheduled(cron = "0 0 2 * * SAT")
    public void sendWeeklyReport() {
        logger.info("begain scheduler");
        List<String> userNames = userRepository.getUserNames();
        for (String userName : userNames) {
            try {
                Set<Student> roster = new HashSet<>(rosterRepository.getStudents(userName));
                List<AttendanceRecord> records = attendanceRepository.getRecords(userName);
                // skip
                if (records.isEmpty()) {
                    continue;
                }
                AttendanceManager manager = new AttendanceManager(userName, roster, records);
                for (Student student : roster) {
                    String report = manager.generateReport(student);
                    notification.notifyObserver(report, student.getEmail());
                    
                }
                logger.info("successfully send email to all students for user: " + userName);

            } catch (Exception e) {
                logger.info("error happen sending email: " + e.getMessage());

            }
        }
      
        
    }

}
