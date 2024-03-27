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
import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
import edu.duke.ece651.team1.server.repository.InMemoryRosterRepository;
import edu.duke.ece651.team1.server.repository.InMemoryUserRepository;
import edu.duke.ece651.team1.shared.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
import org.apache.tomcat.util.digester.ArrayStack;
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

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class ReportSchedulerTest {
    @MockBean
    private InMemoryUserRepository userRepository;

    @MockBean
    private InMemoryAttendanceRepository attendanceRepository;

    @MockBean
    private InMemoryRosterRepository rosterRepository;
    @Mock
    private NotificationService notificationService;

    public void setup_normal() throws IOException {

        String userName1 = "huidan";
        String userName2 = "rachel";

        Student student1 = new Student("huidan", "rachel", "huidan@duke.com");
        Student student2 = new Student("yitiao", "yitiao@duke.com");
        Student student3 = new Student("meng", "meng@duke.com");
        List<Student> roster_huidan = new ArrayList<>(Arrays.asList(student1, student2));
        List<Student> roster_rahcel = new ArrayList<>(Arrays.asList(student1, student2, student3));
        AttendanceRecord record1 = new AttendanceRecord(LocalDate.of(2024, 03, 24));
        record1.initializeFromRoaster(roster_huidan);
        AttendanceRecord record2 = new AttendanceRecord(LocalDate.of(2024, 03, 27));
        record2.initializeFromRoaster(roster_huidan);
        AttendanceRecord record3 = new AttendanceRecord(LocalDate.of(2024, 03, 22));
        record2.initializeFromRoaster(roster_rahcel);
        List<AttendanceRecord> records_huidan = new ArrayList<>(Arrays.asList(record1, record2));
        List<AttendanceRecord> records_rachel = new ArrayList<>(Arrays.asList(record3));
        when(userRepository.getUserNames()).thenReturn(Arrays.asList(userName1, userName2));
        when(rosterRepository.getStudents(userName1)).thenReturn(roster_huidan);
        when(attendanceRepository.getRecords(userName1)).thenReturn(records_huidan);
        when(rosterRepository.getStudents(userName2)).thenReturn(roster_rahcel);
        when(attendanceRepository.getRecords(userName2)).thenReturn(records_rachel);
    }

    public void setup_withempty() throws IOException {
        String userNameWithRecords = "userWithRecords";
        String userNameWithoutRecords = "userWithoutRecords";
        Student student1 = new Student("Student1", "student1@domain.com");
        Student student2 = new Student("Student2", "student2@domain.com");
        List<Student> rosterWithRecords = Arrays.asList(student1, student2);
        List<Student> rosterWithoutRecords = Arrays.asList(student1);
        AttendanceRecord record1 = new AttendanceRecord(LocalDate.of(2024, 3, 24));
        record1.initializeFromRoaster(rosterWithRecords);
        List<AttendanceRecord> recordsWithRecords = Arrays.asList(record1);
        when(userRepository.getUserNames()).thenReturn(Arrays.asList(userNameWithRecords, userNameWithoutRecords));
        when(rosterRepository.getStudents(userNameWithRecords)).thenReturn(new ArrayList<>(rosterWithRecords));
        when(rosterRepository.getStudents(userNameWithoutRecords)).thenReturn(new ArrayList<>(rosterWithoutRecords));
        when(attendanceRepository.getRecords(userNameWithRecords)).thenReturn(new ArrayList<>(recordsWithRecords));
        when(attendanceRepository.getRecords(userNameWithoutRecords)).thenReturn(new ArrayList<>());
    }

    private void setField(Object targetObject, String fieldName, Object value) {
        Field field = ReflectionUtils.findField(targetObject.getClass(), fieldName);
        if (field != null) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, targetObject, value);
        } else {
            throw new RuntimeException("Field '" + fieldName + "' not found on object of class "
                    + targetObject.getClass().getSimpleName());
        }
    }

    @Test
    public void testSendWeeklyReport() throws IOException {
        setup_normal();
        ReportScheduler reportScheduler = new ReportScheduler(); // 假设你能够这样实例化ReportScheduler
        setField(reportScheduler, "userRepository", userRepository);
        setField(reportScheduler, "attendanceRepository", attendanceRepository);
        setField(reportScheduler, "rosterRepository", rosterRepository);
        setField(reportScheduler, "notification", notificationService);
        reportScheduler.sendWeeklyReport();
        assertNotNull(userRepository.getUserNames());
        assertNotNull(rosterRepository.getStudents("huidan"));
        verify(notificationService, times(5)).notifyObserver(anyString(), anyString());
    }

    public void renewReportScheduler(ReportScheduler reportScheduler) {
        setField(reportScheduler, "userRepository", userRepository);
        setField(reportScheduler, "attendanceRepository", attendanceRepository);
        setField(reportScheduler, "rosterRepository", rosterRepository);
        setField(reportScheduler, "notification", notificationService);
    }

    @Test
    public void testSendWeeklyReportWithEmptyRecords() throws IOException {
        setup_withempty();
        ReportScheduler reportScheduler = new ReportScheduler();
        renewReportScheduler(reportScheduler);
        reportScheduler.sendWeeklyReport();
        verify(notificationService, times(2)).notifyObserver(anyString(), anyString());
    }

    @Test
    public void testSendWeeklyReportWithException() throws IOException {
        doThrow(new RuntimeException("Test exception")).when(notificationService).notifyObserver(anyString(),
                anyString());

        ReportScheduler reportScheduler = new ReportScheduler();
        setup_normal();
        renewReportScheduler(reportScheduler);

        reportScheduler.sendWeeklyReport();

        // Verify that notifyObserver was attempted but threw an exception
        verify(notificationService, atLeastOnce()).notifyObserver(anyString(), anyString());

    }

    @Test
    public void testInitializeNotificationWithException() throws IOException{
        doThrow(new RuntimeException("Test exception")).when(notificationService).addNotification(any());;
        ReportScheduler reportScheduler = new ReportScheduler();
        setup_normal();
        renewReportScheduler(reportScheduler);
        reportScheduler.initializeNotification();
    }
}
