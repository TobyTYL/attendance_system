package edu.duke.ece651.team1.server.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.duke.ece651.team1.server.model.EmailNotification;
import edu.duke.ece651.team1.server.model.NotificationService;

import edu.duke.ece651.team1.shared.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
import org.apache.tomcat.util.digester.ArrayStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import edu.duke.ece651.team1.data_access.Attendance.*;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import edu.duke.ece651.team1.data_access.Section.*;
import edu.duke.ece651.team1.data_access.Enrollment.*;
import edu.duke.ece651.team1.data_access.Student.*;
import edu.duke.ece651.team1.data_access.Notification.*;
import java.sql.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ReportSchedulerTest {

    @Mock
    private NotificationService notificationService;
    @Mock
    private Logger logger;
    private MockedStatic<AttendanceRecordDAO> mockedAttendanceRecordDAO;
    private MockedStatic<AttendanceEntryDAO> mockedAttendanceEntryDAO;
    @Mock
    private SectionDao sectionDao;
    @Mock
    private EnrollmentDao enrollmentDao;
    @Mock
    private StudentDao studentDao;
    @Mock
    private NotificationPreferenceDao notificationPreferenceDao;
    @InjectMocks
    private ReportScheduler reportScheduler;
    int sectionId = 1;
    int courseId = 1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockedAttendanceRecordDAO = mockStatic(AttendanceRecordDAO.class);
        mockedAttendanceEntryDAO = mockStatic(AttendanceEntryDAO.class);
    }

    @AfterEach
    public void teardown() {
        mockedAttendanceRecordDAO.close();
        mockedAttendanceEntryDAO.close();
    }

    @Test
    void initializeNotification_SuccessfulAddition() {
        doNothing().when(notificationService).addNotification(any(EmailNotification.class));
        reportScheduler.initializeNotification();
        verify(logger).info("add notification successful");
    }

    @Test
    void initializeNotification_FailedAddition() {
        doThrow(new RuntimeException("Test exception")).when(notificationService)
                .addNotification(any(EmailNotification.class));
        reportScheduler.initializeNotification();
        verify(logger).info(startsWith("unable to add email notification because"));
    }

    void NormalsetUp() throws SQLException {
        List<Enrollment> mockEnrollments = Arrays.asList(new Enrollment(1, 1, sectionId),
                new Enrollment(2, 2, sectionId));
        Student student1 = new Student(1, "John Doe", "John", "john@example.com", 1);
        Student student2 = new Student(2, "Jane Doe", "Jane", "jane@example.com", 1);
        when(enrollmentDao.getEnrollmentsBySectionId(sectionId)).thenReturn(mockEnrollments);
        when(studentDao.findStudentByStudentID(1)).thenReturn(Optional.of(student1));
        when(studentDao.findStudentByStudentID(2)).thenReturn(Optional.of(student2));
        new NotificationPreference(courseId, sectionId, courseId, false);
        when(notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(1, courseId))
                .thenReturn(new NotificationPreference(1, 1, sectionId, true));
        when(notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(2, courseId))
                .thenReturn(new NotificationPreference(2, 2, sectionId, false));
        List<AttendanceRecord> records = new ArrayList<>();
        AttendanceRecord record = new AttendanceRecord(); // Assume a constructor exists
        record.addAttendanceEntry(student1, AttendanceStatus.PRESENT);
        record.addAttendanceEntry(student2, AttendanceStatus.ABSENT);
        records.add(record);
        when(AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId)).thenReturn(records);
        List<Section> sections = List.of(new Section(sectionId, courseId, 1));
        when(sectionDao.getAllSections()).thenReturn(sections);
    }

    void NullSetup() throws SQLException {
        List<Enrollment> mockEnrollments = Arrays.asList(new Enrollment(1, 1, sectionId),
                new Enrollment(2, 2, sectionId));
        Student student1 = new Student(1, "John Doe", "John", "john@example.com", 1);
        Student student2 = new Student(2, "Jane Doe", "Jane", "jane@example.com", 1);
        List<AttendanceRecord> records = new ArrayList<>();
        when(AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId)).thenReturn(records);
        List<Section> sections = List.of(new Section(sectionId, courseId, 1));
        when(sectionDao.getAllSections()).thenReturn(sections);
    }

   

    @Test
    void whenNormalConditions_sendWeeklyReportSendsEmails() throws SQLException {
        NormalsetUp(); // Prepare the normal setup
        reportScheduler.sendWeeklyReport();
        verify(notificationService, times(1)).notifyObserver(anyString(), eq("john@example.com"));
        verify(notificationService, never()).notifyObserver(anyString(), eq("jane@example.com"));
        verify(logger).info(contains("successfully send email to all students for section: " + sectionId));
    }

    @Test
    void whenNoAttendanceRecords_sendWeeklyReportSkipsSending() throws SQLException {
        NullSetup();
        reportScheduler.sendWeeklyReport();
        verify(notificationService, never()).notifyObserver(anyString(), anyString());
    }

    @Test
    void whenExceptionOccurs_sendWeeklyReportLogsError() throws SQLException {
        when(AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId))
                .thenThrow(new SQLException("sql exception happend"));
        List<Section> sections = List.of(new Section(sectionId, courseId, 1));
        when(sectionDao.getAllSections()).thenReturn(sections);
        reportScheduler.sendWeeklyReport();
        verify(notificationService, never()).notifyObserver(anyString(), anyString());
        verify(logger).info(startsWith("error happen sending email: "));
    }

}
