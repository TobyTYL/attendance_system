package edu.duke.ece651.team1.server.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.json.JSONObject;
import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.data_access.Attendance.*;
import edu.duke.ece651.team1.data_access.Notification.*;
import edu.duke.ece651.team1.data_access.Enrollment.*;
import edu.duke.ece651.team1.data_access.Student.*;
import edu.duke.ece651.team1.data_access.Section.*;
import edu.duke.ece651.team1.server.model.*;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import org.mockito.MockitoAnnotations;

public class AttendanceServiceTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private StudentDao studentDao;

    @Mock
    private SectionDao sectionDao;

    @Mock
    private EnrollmentDao enrollmentDao;

    @Mock
    private NotificationPreferenceDao notificationPreferenceDao;

    private MockedStatic<AttendanceRecordDAO> mockedAttendanceRecordDAO;

    private MockedStatic<AttendanceEntryDAO> mockedAttendanceEntryDAO;

    @Mock
    private JsonAttendanceSerializer serializer;

    @InjectMocks
    private AttendanceService attendanceService;
    String recordJson = "{" +
            "\"Record Id\": 123," +
            "\"sessionDate\": \"2024-04-10\"," +
            "\"Entries\": {" +
            "\"JohnDoe\": {" +
            "\"student Id\": 1," +
            "\"Display Name\": \"John Doe\"," +
            "\"Email\": \"john.doe@example.com\"," +
            "\"Attendance status\": \"PRESENT\"" +
            "}}}";
    int sectionId = 1;
    String sessionDate = "2024-04-01";

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
    public void testSaveAttendanceRecord() throws SQLException {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        when(serializer.deserialize(recordJson)).thenReturn(attendanceRecord);
        attendanceService.saveAttendanceRecord(recordJson, sectionId);
        mockedAttendanceRecordDAO.verify(() -> AttendanceRecordDAO.addAttendanceRecord(attendanceRecord, sectionId));

    }

    @Test
    public void testGetRecordDates() throws SQLException {
        List<AttendanceRecord> mockRecords = new ArrayList<>();
        AttendanceRecord mockRecord = new AttendanceRecord(LocalDate.now());
        mockRecords.add(mockRecord);
        when(AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId)).thenReturn(mockRecords);
        List<String> dates = attendanceService.getRecordDates(sectionId);
        assertNotNull(dates);
        assertFalse(dates.isEmpty());
        assertEquals(mockRecord.getSessionDate().toString(), dates.get(0));
    }

    @Test
    public void testUpdateAttendanceRecord() throws SQLException {
        AttendanceRecord attendanceRecord = new AttendanceRecord(); // Mocked AttendanceRecord object
        when(serializer.deserialize(recordJson)).thenReturn(attendanceRecord);
        attendanceService.updateAttendanceRecord(recordJson);
        mockedAttendanceRecordDAO.verify(() -> AttendanceRecordDAO.updateAttendanceRecord(attendanceRecord));
    }

    @Test
    public void testGetRecord() throws SQLException {
        AttendanceRecord expectedRecord = new AttendanceRecord(LocalDate.parse(sessionDate));
        String expectedSerializedRecord = "{\"sessionDate\":\"2024-04-01\",\"Entries\":{}}";
        when(AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId,
                LocalDate.parse(sessionDate)))
                .thenReturn(expectedRecord);
        when(serializer.serialize(expectedRecord)).thenReturn(expectedSerializedRecord);
        String actualSerializedRecord = attendanceService.getRecord(sectionId, sessionDate);
        assertEquals(expectedSerializedRecord, actualSerializedRecord);
    }

    @Test
    public void testFindStudentByLegalName_StudentNotFound() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student = new Student("legalName", "displayName", "email@example.com");
        record.initializeAttendanceEntry(student);
        Student result = attendanceService.findStudentByLegalName(record, "nonExistentName");
        assertNull(result);
    }

    @Test
    public void testFindStudentByLegalName_StudentFound() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        Student student = new Student("legalName", "displayName", "email@example.com");
        record.initializeAttendanceEntry(student);
        Student result = attendanceService.findStudentByLegalName(record, "legalName");
        assertNotNull(result);
    }

    @Test
    public void testGenerateAttendanceNotification() {
        AttendanceService attendanceService = new AttendanceService();
        String studentName = "John Doe";
        String attendanceStatus = "PRESENT";
        String notificationMessage = attendanceService.generateAttendanceNotification(studentName, sessionDate,
                attendanceStatus);
        String expectedMessage = "Dear John Doe\nWe would like to inform you that your attendance status for the 2024-04-01 has been updated  to PRESENT";
        assertEquals(expectedMessage, notificationMessage);
    }

    @Test
    void testSendMessage() {
        String studentName = "John Doe";
        String studentEmail = "john.doe@example.com";
        String attendanceStatus = "PRESENT";
        attendanceService.sendMessage(studentName, studentEmail, sessionDate, attendanceStatus);
        String expectedMessage = "Dear John Doe\nWe would like to inform you that your attendance status for the 2024-04-01 has been updated  to PRESENT";
        verify(notificationService).notifyObserver(expectedMessage, studentEmail);
    }

    @Test
    public void testGetStudentRecordEntry_Found() throws SQLException {
        String studentName = "huidan";
        AttendanceStatus expectedStatus = AttendanceStatus.PRESENT;
        AttendanceRecord record = new AttendanceRecord();
        Map<Student, AttendanceStatus> entries = new HashMap<>();
        record.addAttendanceEntry(new Student(1, studentName, "John", "johndoe@example.com", 1), expectedStatus);
        when(AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(eq(sectionId),
                eq(LocalDate.parse(sessionDate))))
                .thenReturn(record);
        String result = attendanceService.getStudentRecordEntry(sectionId, sessionDate, studentName);
        assertEquals("Student Name: huidan, Attendance Status: PRESENT", result);
    }

    @Test
    public void testGetStudentRecordEntry_NotFound() throws SQLException {
        int sectionId = 1;
        String studentName = "huidan";
        AttendanceRecord record = new AttendanceRecord();
        when(AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(eq(sectionId),
                eq(LocalDate.parse(sessionDate))))
                .thenReturn(record);
        String result = attendanceService.getStudentRecordEntry(sectionId, sessionDate, studentName);
        assertEquals("No attendance record found for student: huidan", result);
    }

    @Test
    public void modifyStudentEntryAndSendUpdates_StudentFound() throws SQLException {
        String attendanceEntryJson = "{\"Legal Name\":\"yitiao\",\"Attendance Status\":\"PRESENT\"}";
        Student student = new Student(1, "yitiao", "Yitiao", "email@example.com", null);
        AttendanceRecord record = new AttendanceRecord();
        record.setRecordId(1);
        record.addAttendanceEntry(student, AttendanceStatus.PRESENT);
        NotificationPreference preference = new NotificationPreference(sectionId, sectionId, sectionId, false);
        preference.setReceiveNotifications(true);
        when(AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId,
                LocalDate.parse(sessionDate)))
                .thenReturn(record);
        when(sectionDao.getSectionById(sectionId)).thenReturn(new Section(sectionId, 101, 1));
        when(notificationPreferenceDao.findNotificationPreferenceByStudentIdAndClassId(student.getStudentId(), 101))
                .thenReturn(preference);
        doNothing().when(notificationService).notifyObserver(anyString(), anyString());
        String result = attendanceService.modifyStudentEntryAndSendUpdates(sectionId, sessionDate,
                attendanceEntryJson);
        assertEquals("Successfully updated attendance status for yitiao", result);
        mockedAttendanceEntryDAO
                .verify(() -> AttendanceEntryDAO.updateAttendanceEntry(anyInt(), anyInt(), anyString()), times(1));
        verify(notificationService, times(1)).notifyObserver(anyString(), eq("email@example.com"));
    }

    @Test
    public void modifyStudentEntryAndSendUpdates_StudentNotFound() throws SQLException {
        String attendanceEntryJson = "{\"Legal Name\":\"nonexistent\",\"Attendance Status\":\"PRESENT\"}";
        AttendanceRecord record = new AttendanceRecord(); // Assuming an empty record for simplicity
        when(AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(eq(sectionId),
                eq(LocalDate.parse(sessionDate))))
                .thenReturn(record);
        String result = attendanceService.modifyStudentEntryAndSendUpdates(sectionId, sessionDate,
                attendanceEntryJson);
        assertEquals("Student not found in the attendance record for " + sessionDate, result);
    }

    @Test
    public void getAllStudent_ReturnsStudentsList() throws SQLException {
        List<Enrollment> mockEnrollments = Arrays.asList(
                new Enrollment(1, 1, sectionId),
                new Enrollment(2, 2, sectionId));
        Student student1 = new Student(1, "John Doe", "John", "john@example.com", 1);
        Student student2 = new Student(2, "Jane Doe", "Jane", "jane@example.com", 1);
        when(enrollmentDao.getEnrollmentsBySectionId(sectionId)).thenReturn(mockEnrollments);
        when(studentDao.findStudentByStudentID(1)).thenReturn(Optional.of(student1));
        when(studentDao.findStudentByStudentID(2)).thenReturn(Optional.of(student2));
        List<Student> students = attendanceService.getAllStudent(sectionId);
        assertNotNull(students);
        assertEquals(2, students.size());
        assertTrue(students.containsAll(Arrays.asList(student1, student2)));
    }

    private void mocksetupForAttendanceManager() throws SQLException {
        List<Enrollment> mockEnrollments = Arrays.asList(
                new Enrollment(1, 1, sectionId),
                new Enrollment(2, 2, sectionId));
        Student student1 = new Student(1, "John Doe", "John", "john@example.com", 1);
        Student student2 = new Student(2, "Jane Doe", "Jane", "jane@example.com", 1);
        when(enrollmentDao.getEnrollmentsBySectionId(sectionId)).thenReturn(mockEnrollments);
        when(studentDao.findStudentByStudentID(1)).thenReturn(Optional.of(student1));
        when(studentDao.findStudentByStudentID(2)).thenReturn(Optional.of(student2));
        AttendanceRecord record = new AttendanceRecord(LocalDate.parse("2024-04-11"));
        record.addAttendanceEntry(student1, AttendanceStatus.PRESENT);
        record.addAttendanceEntry(student2, AttendanceStatus.ABSENT);
        List<AttendanceRecord> records = new ArrayList<>();
        records.add(record);
        when(AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId)).thenReturn(records);
    }

    @Test
    public void getAttendanceReportForStudent_ReturnsDetailedReport() throws SQLException {
        mocksetupForAttendanceManager();
        String report = attendanceService.getAttendanceReportForStudent(1, sectionId, true);
        assertTrue(report.contains("2024-04-11: Present"));
        String report_summary = attendanceService.getAttendanceReportForStudent(sectionId, sectionId, false);
        assertTrue(!report_summary.contains("2024-04-11: Absent"));
    }

    @Test
    public void getAttendanceReportForProfessor_ReturnsClassReport() throws SQLException {
        mocksetupForAttendanceManager();
        String classReport = attendanceService.getAttendanceReportForProfessor(sectionId);
        assertTrue(classReport.contains("Class Attendance Report:"));
    }

}
