package edu.duke.ece651.team1.data_access.Attendance;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.shared.AttendanceEntry;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AttendanceRecordDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private MockedStatic<DB_connect> mockedDB;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        mockedDB = Mockito.mockStatic(DB_connect.class);
        mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);

    }

    @AfterEach
    public void tearDown() {
        mockedDB.close();
        // more clean up logic if needed
    }

    @Test
    void testAddAttendanceRecord() throws SQLException {
        LocalDate sessionDate = LocalDate.now();
        AttendanceRecord record = new AttendanceRecord(sessionDate);
        int sectionId = 1;
        int generatedRecordId = 100; // Assume an ID for the test

        // Set up the ResultSet to simulate getting the generated key
        when(mockResultSet.next()).thenReturn(true, false); // true for first call, false for second (end of ResultSet)
        when(mockResultSet.getInt(1)).thenReturn(generatedRecordId);

        // Set up the PreparedStatement for executeUpdate and getGeneratedKeys
        when(mockStatement.executeUpdate()).thenReturn(1); // Simulating one row affected
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);

        // Call the method under test
        AttendanceRecordDAO.addAttendanceRecord(record, sectionId);

        // Verify that the PreparedStatement was set up with correct values
        verify(mockStatement).setInt(1, sectionId);
        verify(mockStatement).setDate(2, Date.valueOf(sessionDate));
        verify(mockStatement).executeUpdate();

        // Verify that getGeneratedKeys was called to retrieve the generated record ID
        verify(mockStatement).getGeneratedKeys();

        // Additional verification for adding attendance entries can go here
    }

    @Disabled
    @Test
    void testAddAttendanceRecordSuccess() throws SQLException {
        // Setup
        LocalDate date = LocalDate.now();
        int sectionId = 1;
        int studentId = 1; // Assuming a non-null student ID for testing purposes
        Student student = new Student(studentId, "StudentName", "StudentName", "student@example.com");
        AttendanceRecord record = new AttendanceRecord(date);
        record.addAttendanceEntry(student, AttendanceStatus.PRESENT);
        int generatedRecordId = 100; // Mocked ID for the record

        when(mockStatement.executeUpdate()).thenReturn(1); // Simulating row affected
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // For getting generated keys
        when(mockResultSet.getInt(1)).thenReturn(generatedRecordId);

        // Perform the action
        AttendanceRecordDAO.addAttendanceRecord(record, sectionId);

        // Verify
        verify(mockStatement).setInt(1, sectionId);
        verify(mockStatement).setDate(2, java.sql.Date.valueOf(date));
        verify(mockStatement).executeUpdate();
        verify(mockStatement).getGeneratedKeys();

        // Capture and verify the attendance entry added
        ArgumentCaptor<AttendanceEntry> attendanceEntryCaptor = ArgumentCaptor.forClass(AttendanceEntry.class);
        AttendanceEntryDAO.addAttendanceEntry(attendanceEntryCaptor.capture());
        AttendanceEntry capturedEntry = attendanceEntryCaptor.getValue();

        Assertions.assertNotNull(capturedEntry, "Captured AttendanceEntry should not be null.");
        assertEquals(student.getStudentId(), capturedEntry.getStudentId(), "Student IDs should match.");
        assertEquals(generatedRecordId, capturedEntry.getAttendanceRecordId(), "Record IDs should match.");
        assertEquals(AttendanceStatus.PRESENT, capturedEntry.getStatus(), "Attendance status should be PRESENT.");
    }

    @Disabled
    @Test
    void testFillAttendanceRecordEntriesMap() throws SQLException {
        // Mock the StudentDao
        StudentDao mockStudentDao = mock(StudentDao.class);

        // Create a student and mock the DAO response
        Student mockStudent = new Student(1, "Legal Name", "Display Name", "email@example.com");
        when(mockStudentDao.findStudentByStudentID(1)).thenReturn(Optional.of(mockStudent));

        // Create the AttendanceRecord and AttendanceEntry objects
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        AttendanceEntry entry = new AttendanceEntry(1, 100, AttendanceStatus.PRESENT);

        // Use a list to hold your entries for the method call
        List<AttendanceEntry> entriesList = new ArrayList<>();
        entriesList.add(entry);

        // Assume we can set the mocked StudentDao in the DAO class
        // AttendanceRecordDAO.setStudentDao(mockStudentDao); // Hypothetical setter method

        // Call the method to test
        AttendanceRecordDAO.fillAttendanceRecordEntriesMap(entriesList, record);

        // Assertions to verify that the AttendanceRecord now contains the entry
        assertFalse(record.getEntries().isEmpty());
        assertEquals(AttendanceStatus.PRESENT, record.getEntries().get(mockStudent));
    }



//    @Test
//    public void testFillAttendanceRecordEntriesMap_Success() throws SQLException {
//         Student mockStudent1 = new Student(1L, "John Doe", "John Doe", "john@example.com");
//         Student mockStudent2 = new Student(2L, "Jane Doe", "Jane Doe", "jane@example.com");
//         MockedStatic<StudentDao> studentMock = Mockito.mockStatic(StudentDao.class);
//         when(StudentDao.findStudentByStudentID(1L)).thenReturn(Optional.of(mockStudent1));
//         when(StudentDao.findStudentByStudentID(2L)).thenReturn(Optional.of(mockStudent2));
//         List<AttendanceEntry> entries = new ArrayList<>();
//         entries.add(new AttendanceEntry(1L, 1L, AttendanceStatus.PRESENT));
//         entries.add(new AttendanceEntry(2L, 1L, AttendanceStatus.ABSENT));
//         LocalDate sessionDate = LocalDate.of(2024, 4, 1);
//         AttendanceRecord record = new AttendanceRecord(sessionDate);
//         AttendanceRecordDAO.fillAttendanceRecordEntriesMap(entries, record);
//         assert (record.getEntries().containsKey(mockStudent1));
//         assert (record.getEntries().get(mockStudent1) == AttendanceStatus.PRESENT);
//         assert (record.getEntries().containsKey(mockStudent2));
//         assert (record.getEntries().get(mockStudent2) == AttendanceStatus.ABSENT);
//         studentMock.close();
//
//     }






}

// package edu.duke.ece651.team1.data_access.Attendance;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.time.LocalDate;
// import java.util.*;
// import org.junit.jupiter.api.Test;
// import org.mockito.MockedStatic;
// import org.mockito.Mockito;
// import java.sql.*;
// import edu.duke.ece651.team1.data_access.DB_connect;
// import edu.duke.ece651.team1.data_access.Student.StudentDao;
// import edu.duke.ece651.team1.shared.AttendanceEntry;
// import edu.duke.ece651.team1.shared.AttendanceRecord;
// import edu.duke.ece651.team1.shared.AttendanceStatus;
// import edu.duke.ece651.team1.shared.Student;

// public class AttendanceRecordDAOTest {
//     Connection mockConnection = mock(Connection.class);
//     PreparedStatement mockStatement = mock(PreparedStatement.class);

//     @Test
//     void testAddAttendanceRecord() throws SQLException {
//         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
//             ResultSet mockResultSet = mock(ResultSet.class);
//             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
//             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//             when(mockStatement.executeUpdate()).thenReturn(1);
//             when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
//             when(mockResultSet.next()).thenReturn(true);
//             when(mockResultSet.getInt(1)).thenReturn(1L);
//             MockedStatic<AttendanceEntryDAO> mockedEntryDAO = Mockito.mockStatic(AttendanceEntryDAO.class);
//             LocalDate sessionDate = LocalDate.of(2024, 4, 1); // example date
//             AttendanceRecord record = new AttendanceRecord(sessionDate);
//             Student student1 = new Student("huidan");
//             Student student2 = new Student("zhecheng");
//             record.initializeAttendanceEntry(student1);
//             record.markPresent(student1);
//             record.initializeAttendanceEntry(student2);
//             record.markPresent(student2);
//             AttendanceRecordDAO.addAttendanceRecord(record, 1L);
//             verify(mockStatement, times(1)).executeUpdate();
//             Map<Student, AttendanceStatus> entries = record.getEntries();
//             mockedEntryDAO.verify(() -> AttendanceEntryDAO.addAttendanceEntry(any(AttendanceEntry.class)), times(2));
//             mockedEntryDAO.close();
//         }
//     }

//     @Test
//     public void testFillAttendanceRecordEntriesMap_Success() throws SQLException {
//         Student mockStudent1 = new Student(1L, "John Doe", "John Doe", "john@example.com");
//         Student mockStudent2 = new Student(2L, "Jane Doe", "Jane Doe", "jane@example.com");
//         MockedStatic<StudentDao> studentMock = Mockito.mockStatic(StudentDao.class);
//         when(StudentDao.findStudentByStudentID(1L)).thenReturn(Optional.of(mockStudent1));
//         when(StudentDao.findStudentByStudentID(2L)).thenReturn(Optional.of(mockStudent2));
//         List<AttendanceEntry> entries = new ArrayList<>();
//         entries.add(new AttendanceEntry(1L, 1L, AttendanceStatus.PRESENT));
//         entries.add(new AttendanceEntry(2L, 1L, AttendanceStatus.ABSENT));
//         LocalDate sessionDate = LocalDate.of(2024, 4, 1);
//         AttendanceRecord record = new AttendanceRecord(sessionDate);
//         AttendanceRecordDAO.fillAttendanceRecordEntriesMap(entries, record);
//         assert (record.getEntries().containsKey(mockStudent1));
//         assert (record.getEntries().get(mockStudent1) == AttendanceStatus.PRESENT);
//         assert (record.getEntries().containsKey(mockStudent2));
//         assert (record.getEntries().get(mockStudent2) == AttendanceStatus.ABSENT);
//         studentMock.close();

//     }

//     @Test
//     public void testFillAttendanceRecordEntriesMap_Failure() throws SQLException {
//         MockedStatic<StudentDao> studentMock = Mockito.mockStatic(StudentDao.class);
//         when(StudentDao.findStudentByStudentID(1)).thenReturn(Optional.empty());
//         AttendanceRecord record = new AttendanceRecord();
//         AttendanceEntry entry = new AttendanceEntry(1, record.getRecordId(), AttendanceStatus.PRESENT);
//         Iterable<AttendanceEntry> entries = Arrays.asList(entry);
//         assertThrows(SQLException.class, () -> AttendanceRecordDAO.fillAttendanceRecordEntriesMap(entries, record));
//         studentMock.close();
//     }

//     @Test
//     public void testFindAttendanceRecordBySectionIDAndSessionDate_Success() throws SQLException {
//         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
//             int sectionId = 1;
//             LocalDate sessionDate = LocalDate.of(2024, 4, 1);
//             int recordId = 100;
//             ResultSet mockResultSet = mock(ResultSet.class);
//             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
//             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//             when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//             when(mockResultSet.next()).thenReturn(true); // Simulate finding a record
//             when(mockResultSet.getInt("AttendanceRecordID")).thenReturn(recordId);
//             when(mockResultSet.getDate("SessionDate")).thenReturn(java.sql.Date.valueOf(sessionDate));
//             MockedStatic<AttendanceEntryDAO> attendanceMock = Mockito.mockStatic(AttendanceEntryDAO.class);
//             when(AttendanceEntryDAO.findAttendanceEntrisByattendanceRecordId(recordId))
//                     .thenReturn(Collections.emptyList());
//             AttendanceRecord result = AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId,
//                     sessionDate);
//             assertNotNull(result);
//             assertEquals(sessionDate, result.getSessionDate());
//             attendanceMock.close();
//         }
//     }

//     @Test
//     public void testFindAttendanceRecordBySectionIDAndSessionDate_Failure() throws SQLException {
//         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
//             int sectionId = 1L;
//             LocalDate sessionDate = LocalDate.of(2024, 4, 1);
//             int recordId = 100L;
//             ResultSet mockResultSet = mock(ResultSet.class);
//             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
//             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//             when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//             when(mockResultSet.next()).thenReturn(false); // Simulate finding a record
//             assertThrows(SQLException.class,
//                     () -> AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId, sessionDate));
//         }
//     }

//     @Test
//     public void testFindAttendanceRecordsBysectionID_Success() throws SQLException {
//         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
//             ResultSet mockResultSet = mock(ResultSet.class);
//             when(DB_connect.getConnection()).thenReturn(mockConnection);
//             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//             when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//             when(mockResultSet.next()).thenReturn(true, true, false);
//             when(mockResultSet.getInt("AttendanceRecordID")).thenReturn(1, 2);
//             when(mockResultSet.getDate("SessionDate")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2024, 4, 1)),
//                     java.sql.Date.valueOf(LocalDate.of(2024, 4, 2)));
//             MockedStatic<AttendanceEntryDAO> attendanceMock = Mockito.mockStatic(AttendanceEntryDAO.class);
//             when(AttendanceEntryDAO.findAttendanceEntrisByattendanceRecordId(anyInt()))
//                     .thenReturn(Collections.emptyList());
//             Iterable<AttendanceRecord> records = AttendanceRecordDAO.findAttendanceRecordsBysectionID(1);
//             List<AttendanceRecord> recordList = new ArrayList<>();
//             records.forEach(recordList::add);
//             assertEquals(2, recordList.size());
//             assertEquals(LocalDate.of(2024, 4, 1), recordList.get(0).getSessionDate());
//             assertEquals(LocalDate.of(2024, 4, 2), recordList.get(1).getSessionDate());
//             attendanceMock.close();
//         }
//     }

//     @Test
//     public void testFindAttendanceRecordsBysectionID_NoRecords() throws SQLException {
//         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
//             ResultSet mockResultSet = mock(ResultSet.class);
//             when(DB_connect.getConnection()).thenReturn(mockConnection);
//             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//             when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//             when(mockResultSet.next()).thenReturn(false);
//             Iterable<AttendanceRecord> records = AttendanceRecordDAO.findAttendanceRecordsBysectionID(1);
//             List<AttendanceRecord> recordList = new ArrayList<>();
//             records.forEach(recordList::add);
//             assertTrue(recordList.isEmpty());
//         }
//     }

//     @Test
//     void updateAttendanceRecord_Success() throws SQLException {
//         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
//             MockedStatic<AttendanceEntryDAO> mocked = Mockito.mockStatic(AttendanceEntryDAO.class);
//             AttendanceRecord record = new AttendanceRecord();
//             record.setRecordId(1);
//             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//             when(mockStatement.executeUpdate()).thenReturn(1);
//             record.addAttendanceEntry(new Student(1, "huidan", "huidan", "ht@duke"), AttendanceStatus.PRESENT);
//             record.addAttendanceEntry(new Student(2, "zc", "huidan", "ht@duke"), AttendanceStatus.PRESENT);
//             AttendanceRecordDAO.updateAttendanceRecord(record);
//             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
//             mocked.verify(() -> AttendanceEntryDAO.updateAttendanceEntry(anyInt(), anyInt(), anyString()), times(record.getEntries().size()));
//         }
//     }

// }
