//package edu.duke.ece651.team1.server.service;
//
//import edu.duke.ece651.team1.server.model.EmailNotification;
//import edu.duke.ece651.team1.server.model.NotificationService;
////import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
//import edu.duke.ece651.team1.shared.AttendanceRecord;
//import edu.duke.ece651.team1.shared.AttendanceStatus;
//import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
//import edu.duke.ece651.team1.shared.Student;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class AttendanceServiceTest {
//    private NotificationService notificationService;
//    @Mock
//    private InMemoryAttendanceRepository inMemoryAttendanceRepository;
//
//    @Mock
//    private JsonAttendanceSerializer jsonAttendanceSerializer;
//    @Mock
//    private InMemoryAttendanceRepository mockInMemoryAttendanceRepository;
//
//    @Mock
//    private NotificationService mockNotificationService;
//
//    @InjectMocks
//    private AttendanceService attendanceService;
//
//    @BeforeEach
//    public void setUp() throws NoSuchFieldException, IllegalAccessException {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testModifyStudentEntryAndSendUpdatesWhenStudentFoundThenStatusUpdated() throws IOException {
//        // Arrange
//        String userName = "user1";
//        String sessionDate = "2023-04-01";
//        String studentName = "legalName";
//        String attendanceEntryJson = "{\"Legal Name\":\"legalName\", \"Attendance Status\":\"PRESENT\"}";
//        AttendanceRecord record = new AttendanceRecord(LocalDate.parse(sessionDate));
//        Student student = new Student(studentName, "displayName", "email@example.com");
//        record.initializeAttendanceEntry(student);
//        record.markPresent(student);
//
//        when(inMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenReturn(record);
//        doNothing().when(mockNotificationService).notifyObserver(anyString(), anyString());
//        when(jsonAttendanceSerializer.serialize(record)).thenReturn("serializedRecord");
//        doNothing().when(inMemoryAttendanceRepository).saveAttendanceRecord(record, userName);
//
//        String result = attendanceService.modifyStudentEntryAndSendUpdates(userName, sessionDate, attendanceEntryJson);
//        assertEquals("Successfully updated attendance status for legalName", result);
//        verify(mockNotificationService).notifyObserver(anyString(), anyString());
//        verify(inMemoryAttendanceRepository).saveAttendanceRecord(record, userName);
//    }
//
//    @Test
//    public void testModifyStudentEntryAndSendUpdatesWhenStudentNotFoundThenErrorMessage() throws IOException {
//        // Arrange
//        String userName = "user1";
//        String sessionDate = "2023-04-01";
//        String studentName = "nonExistentName";
//        String attendanceEntryJson = "{\"Legal Name\":\"nonExistentName\", \"Attendance Status\":\"PRESENT\"}";
//        AttendanceRecord record = new AttendanceRecord(LocalDate.parse(sessionDate));
//        Student student = new Student("legalName", "displayName", "email@example.com");
//        record.initializeAttendanceEntry(student);
//
//        when(inMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenReturn(record);
//
//        // Act
//        String result = attendanceService.modifyStudentEntryAndSendUpdates(userName, sessionDate, attendanceEntryJson);
//
//        // Assert
//        assertEquals("Student not found in the attendance record for 2023-04-01", result);
//    }
//
//    @Test
//    public void testModifyStudentEntryAndSendUpdatesWhenInvalidJsonThenErrorMessage() {
//        // Arrange
//        String userName = "user1";
//        String sessionDate = "2023-04-01";
//        String invalidJson = "invalidJson";
//
//        // Act
//        String result = attendanceService.modifyStudentEntryAndSendUpdates(userName, sessionDate, invalidJson);
//
//        // Assert
//        assertEquals("Invalid JSON format for attendance entry: A JSONObject text must begin with '{' at 1 [character 2 line 1]", result);
//    }
//
//    private void setMock(Object targetObject, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
//        Field field = targetObject.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(targetObject, fieldValue);
//    }
//
//    @Test
//    public void testGenerateAttendanceNotificationWhenValidParametersThenNotificationGenerated() {
//        String studentName = "John Doe";
//        String sessionDate = "2023-04-01";
//        String attendanceStatus = "PRESENT";
//        String result = attendanceService.generateAttendanceNotification(studentName, sessionDate, attendanceStatus);
//        String expected = "Dear John Doe\nWe would like to inform you that your attendance status for the 2023-04-01 has been updated  to PRESENT";
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testGenerateAttendanceNotificationWhenInvalidAttendanceStatusThenNotificationNotGenerated() {
//        String studentName = "John Doe";
//        String sessionDate = "2023-04-01";
//        String attendanceStatus = "INVALID_STATUS";
//        String result = attendanceService.generateAttendanceNotification(studentName, sessionDate, attendanceStatus);
//        String expected = "Dear John Doe\nWe would like to inform you that your attendance status for the 2023-04-01 has been updated  to INVALID_STATUS";
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testInitializeNotification() throws NoSuchFieldException, IllegalAccessException {
//        AttendanceService attendanceService = Mockito.spy(new AttendanceService());
//        NotificationService mockNotificationService = Mockito.mock(NotificationService.class);
//        Field nServiceField = AttendanceService.class.getDeclaredField("nService");
//        nServiceField.setAccessible(true);
//        nServiceField.set(attendanceService, mockNotificationService);
//        attendanceService.initializeNotification();
//        Mockito.verify(mockNotificationService, Mockito.times(1)).addNotification(Mockito.any(EmailNotification.class));
//    }
//
//    @Test
//    public void testGetRecordDatesWithEmptyList() throws IOException {
//        String userName = "user1";
//        when(inMemoryAttendanceRepository.getRecordDates(userName)).thenReturn(Collections.emptyList());
//
//        List<String> result = attendanceService.getRecordDates(userName);
//
//        assertTrue(result.isEmpty());
//        verify(inMemoryAttendanceRepository).getRecordDates(userName);
//    }
//
//    @Test
//    public void testGetRecordWhenInvalidUserNameThenThrowIOException() throws IOException {
//        String userName = "invalidUser";
//        String sessionDate = "2023-04-01";
//        when(inMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenThrow(new IOException());
//        assertThrows(IOException.class, () -> attendanceService.getRecord(userName, sessionDate));
//    }
//
//    @Test
//    public void testGetRecordWhenInvalidSessionDateThenThrowIOException() throws IOException {
//        String userName = "user1";
//        String sessionDate = "invalidDate";
//
//        when(inMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenThrow(new IOException());
//
//        assertThrows(IOException.class, () -> attendanceService.getRecord(userName, sessionDate));
//    }
//
//    @Test
//    public void testFindStudentByLegalName_StudentFound() {
//        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
//        Student student = new Student("legalName", "displayName", "email@example.com");
//        record.initializeAttendanceEntry(student);
//
//        Student result = attendanceService.findStudentByLegalName(record, "legalName");
//
//        assertNotNull(result);
//        assertEquals(student, result);
//    }
//
//    @Test
//    public void testFindStudentByLegalName_StudentNotFound() {
//        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
//        Student student = new Student("legalName", "displayName", "email@example.com");
//        record.initializeAttendanceEntry(student);
//        Student result = attendanceService.findStudentByLegalName(record, "nonExistentName");
//        assertNull(result);
//    }
//    @Test
//    public void testGetStudentRecordEntry_StudentFound() throws IOException {
//        String userName = "user1";
//        String sessionDate = "2023-04-01";
//        String studentName = "legalName";
//        AttendanceRecord record = new AttendanceRecord(LocalDate.parse(sessionDate));
//        Student student = new Student(studentName, "displayName", "email@example.com");
//        record.initializeAttendanceEntry(student);
//        record.markPresent(student);
//        when(inMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenReturn(record);
//        String result = attendanceService.getStudentRecordEntry(userName, sessionDate, studentName);
//        assertEquals("Student Name: legalName, Attendance Status: PRESENT", result);
//    }
//
//    @Test
//    public void testGetStudentRecordEntry_StudentNotFound() throws IOException {
//        String userName = "user1";
//        String sessionDate = "2023-04-01";
//        String studentName = "nonExistentName";
//        AttendanceRecord record = new AttendanceRecord(LocalDate.parse(sessionDate));
//        Student student = new Student("legalName", "displayName", "email@example.com");
//        record.initializeAttendanceEntry(student);
//        when(inMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenReturn(record);
//        String result = attendanceService.getStudentRecordEntry(userName, sessionDate, studentName);
//        assertEquals("No attendance record found for student: nonExistentName", result);
//    }
//
//    @Test
//    public void testGenerateAttendanceNotificationWhenValidInputsThenReturnNotificationMessage() {
//        // Arrange
//        AttendanceService attendanceService = new AttendanceService();
//        String studentName = "John Doe";
//        String sessionDate = "2023-04-01";
//        String attendanceStatus = "PRESENT";
//
//        // Act
//        String notificationMessage = attendanceService.generateAttendanceNotification(studentName, sessionDate, attendanceStatus);
//
//        // Assert
//        String expectedMessage = "Dear John Doe\nWe would like to inform you that your attendance status for the 2023-04-01 has been updated  to PRESENT";
//        assertEquals(expectedMessage, notificationMessage);
//    }
//
//    @Test
//    void testSendMessage() {
//        // Arrange
//        String studentName = "John Doe";
//        String studentEmail = "john.doe@example.com";
//        String sessionDate = "2023-04-01";
//        String attendanceStatus = "PRESENT";
//        // Act
//        attendanceService.sendMessage(studentName, studentEmail, sessionDate, attendanceStatus);
//        // Assert
//        String expectedMessage = "Dear John Doe\nWe would like to inform you that your attendance status for the 2023-04-01 has been updated  to PRESENT";
//        verify(mockNotificationService).notifyObserver(expectedMessage, studentEmail);
//    }
//
////    @Test
////    public void testModifyStudentEntryAndSendUpdates_SuccessfulUpdate() throws IOException {
////        // Arrange
////        String userName = "user1";
////        String sessionDate = "2023-04-01";
////        String studentName = "John Doe";
////        String attendanceEntryJson = "{\"Legal Name\":\"John Doe\",\"Attendance Status\":\"PRESENT\"}";
////        AttendanceRecord record = new AttendanceRecord(LocalDate.parse(sessionDate));
////        Student student = new Student(studentName, "displayName", "john.doe@example.com");
////        record.initializeAttendanceEntry(student);
////
////        // Ensure the mock repository returns a valid AttendanceRecord
////        when(mockInMemoryAttendanceRepository.getRecord(userName, sessionDate)).thenReturn(record);
////
////        // Act
////        String result = attendanceService.modifyStudentEntryAndSendUpdates(userName, sessionDate, attendanceEntryJson);
////
////        // Assert
////        assertEquals("Successfully updated attendance status for John Doe", result);
////        verify(mockInMemoryAttendanceRepository).saveAttendanceRecord(record, userName);
////        verify(mockNotificationService).notifyObserver(anyString(), eq("john.doe@example.com"));
////    }
//
//
//}