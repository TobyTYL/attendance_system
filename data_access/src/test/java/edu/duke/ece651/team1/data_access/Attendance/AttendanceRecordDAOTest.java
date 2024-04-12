package edu.duke.ece651.team1.data_access.Attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.*;
import org.mockito.MockitoAnnotations;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.AttendanceEntry;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;

public class AttendanceRecordDAOTest {
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockStatement = mock(PreparedStatement.class);
    @Mock
    private StudentDao studentDao;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        AttendanceRecordDAO.studentDao = studentDao;
    }

    @Test
    void testAddAttendanceRecord() throws SQLException {
        try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
            ResultSet mockResultSet = mock(ResultSet.class);
            mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(1);
            when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(1);
            MockedStatic<AttendanceEntryDAO> mockedEntryDAO = Mockito.mockStatic(AttendanceEntryDAO.class);
            LocalDate sessionDate = LocalDate.of(2024, 4, 1); // example date
            AttendanceRecord record = new AttendanceRecord(sessionDate);
            Student student1 = new Student("huidan");
            Student student2 = new Student("zhecheng");
            record.initializeAttendanceEntry(student1);
            record.markPresent(student1);
            record.initializeAttendanceEntry(student2);
            record.markPresent(student2);
            AttendanceRecordDAO.addAttendanceRecord(record, 1);
            verify(mockStatement, times(1)).executeUpdate();
            Map<Student, AttendanceStatus> entries = record.getEntries();
            mockedEntryDAO.verify(() -> AttendanceEntryDAO.addAttendanceEntry(any(AttendanceEntry.class)), times(2));
            mockedEntryDAO.close();
        }
    }

    @Test
    public void testFillAttendanceRecordEntriesMap_Success() throws SQLException {
        Student mockStudent1 = new Student(1, "John Doe", "John Doe", "john@example.com", null);
        Student mockStudent2 = new Student(2, "Jane Doe", "Jane Doe", "jane@example.com", null);
        MockedStatic<StudentDao> studentMock = Mockito.mockStatic(StudentDao.class);
        when(studentDao.findStudentByStudentID(1)).thenReturn(Optional.of(mockStudent1));
        when(studentDao.findStudentByStudentID(2)).thenReturn(Optional.of(mockStudent2));
        List<AttendanceEntry> entries = new ArrayList<>();
        entries.add(new AttendanceEntry(1, 1, AttendanceStatus.PRESENT));
        entries.add(new AttendanceEntry(2, 1, AttendanceStatus.ABSENT));
        LocalDate sessionDate = LocalDate.of(2024, 4, 1);
        AttendanceRecord record = new AttendanceRecord(sessionDate);
        AttendanceRecordDAO.fillAttendanceRecordEntriesMap(entries, record);
        assert (record.getEntries().containsKey(mockStudent1));
        assert (record.getEntries().get(mockStudent1) == AttendanceStatus.PRESENT);
        assert (record.getEntries().containsKey(mockStudent2));
        assert (record.getEntries().get(mockStudent2) == AttendanceStatus.ABSENT);
        studentMock.close();

    }

    @Test
    public void testFillAttendanceRecordEntriesMap_Failure() throws SQLException {

        when(studentDao.findStudentByStudentID(1)).thenReturn(Optional.empty());
        AttendanceRecord record = new AttendanceRecord();
        AttendanceEntry entry = new AttendanceEntry(1, record.getRecordId(), AttendanceStatus.PRESENT);
        Iterable<AttendanceEntry> entries = Arrays.asList(entry);
        assertThrows(SQLException.class, () -> AttendanceRecordDAO.fillAttendanceRecordEntriesMap(entries, record));

    }

    @Test
    public void testFindAttendanceRecordBySectionIDAndSessionDate_Success() throws SQLException {
        try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
            int sectionId = 1;
            LocalDate sessionDate = LocalDate.of(2024, 4, 1);
            int recordId = 100;
            ResultSet mockResultSet = mock(ResultSet.class);
            mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true); // Simulate finding a record
            when(mockResultSet.getInt("AttendanceRecordID")).thenReturn(recordId);
            when(mockResultSet.getDate("SessionDate")).thenReturn(java.sql.Date.valueOf(sessionDate));
            MockedStatic<AttendanceEntryDAO> attendanceMock = Mockito.mockStatic(AttendanceEntryDAO.class);
            when(AttendanceEntryDAO.findAttendanceEntrisByattendanceRecordId(recordId))
                    .thenReturn(Collections.emptyList());
            AttendanceRecord result = AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId,
                    sessionDate);
            assertNotNull(result);
            assertEquals(sessionDate, result.getSessionDate());
            attendanceMock.close();
        }
    }

    @Test
    public void testFindAttendanceRecordBySectionIDAndSessionDate_Failure() throws SQLException {
        try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
            int sectionId = 1;
            LocalDate sessionDate = LocalDate.of(2024, 4, 1);
            int recordId = 100;
            ResultSet mockResultSet = mock(ResultSet.class);
            mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // Simulate finding a record
            assertThrows(SQLException.class,
                    () -> AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId, sessionDate));
        }
    }

    @Test
    public void testFindAttendanceRecordsBysectionID_Success() throws SQLException {
        try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
            ResultSet mockResultSet = mock(ResultSet.class);
            when(DB_connect.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getInt("AttendanceRecordID")).thenReturn(1, 2);
            when(mockResultSet.getDate("SessionDate")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2024, 4, 1)),
                    java.sql.Date.valueOf(LocalDate.of(2024, 4, 2)));
            MockedStatic<AttendanceEntryDAO> attendanceMock = Mockito.mockStatic(AttendanceEntryDAO.class);
            when(AttendanceEntryDAO.findAttendanceEntrisByattendanceRecordId(anyInt()))
                    .thenReturn(Collections.emptyList());
            Iterable<AttendanceRecord> records = AttendanceRecordDAO.findAttendanceRecordsBysectionID(1);
            List<AttendanceRecord> recordList = new ArrayList<>();
            records.forEach(recordList::add);
            assertEquals(2, recordList.size());
            assertEquals(LocalDate.of(2024, 4, 1), recordList.get(0).getSessionDate());
            assertEquals(LocalDate.of(2024, 4, 2), recordList.get(1).getSessionDate());
            attendanceMock.close();
        }
    }

    @Test
    public void testFindAttendanceRecordsBysectionID_NoRecords() throws SQLException {
        try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
            ResultSet mockResultSet = mock(ResultSet.class);
            when(DB_connect.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);
            Iterable<AttendanceRecord> records = AttendanceRecordDAO.findAttendanceRecordsBysectionID(1);
            List<AttendanceRecord> recordList = new ArrayList<>();
            records.forEach(recordList::add);
            assertTrue(recordList.isEmpty());
        }
    }

    @Test
    void updateAttendanceRecord_Success() throws SQLException {
        try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
            MockedStatic<AttendanceEntryDAO> mocked = Mockito.mockStatic(AttendanceEntryDAO.class);
            AttendanceRecord record = new AttendanceRecord();
            record.setRecordId(1);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(1);
            record.addAttendanceEntry(new Student(1, "huidan", "huidan", "ht@duke",null), AttendanceStatus.PRESENT);
            record.addAttendanceEntry(new Student(2, "zc", "huidan", "ht@duke",null), AttendanceStatus.PRESENT);
            AttendanceRecordDAO.updateAttendanceRecord(record);
            mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
            mocked.verify(() -> AttendanceEntryDAO.updateAttendanceEntry(anyInt(), anyInt(), anyString()),
                    times(record.getEntries().size()));
            mocked.close();
        }
    }

}
