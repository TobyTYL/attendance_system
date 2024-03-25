package edu.duke.ece651.team1.server.scheduler;

import java.security.GeneralSecurityException;
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

@Component
public class ReportScheduler {
    // @Autowired
    // private JavaMailSender emailSender;
    private NotificationService notification = new NotificationService();
    @Autowired
    private InMemoryUserRepository userRepository;
    @Autowired
    private InMemoryAttendanceRepository attendanceRepository;
    @Autowired
    private InMemoryRosterRepository rosterRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @PostConstruct
    public void initializeNotification() {
        try {
            notification.addNotification(new EmailNotification());
            logger.info("add notification successful");
        } catch (Exception e) {
            logger.info("unable to add email notification because "+e.getMessage());
        }

    }

    @Scheduled(cron = "0 0 2 * * SAT")
    public void sendWeeklyReport() {
        logger.info("begain scheduler");
        notification.notifyObserver("null", "huidan_tan18@163.com");
        List<String> userNames = userRepository.getUserNames();
        for (String userName : userNames) {
            try {
                Set<Student> roster = rosterRepository.getRoster(userName);
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
