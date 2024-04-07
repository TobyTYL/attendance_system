 package edu.duke.ece651.team1.data_access.Attendance;

 import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertNotNull;
 import static org.junit.jupiter.api.Assertions.assertThrows;
 import static org.junit.jupiter.api.Assertions.assertTrue;
 import static org.mockito.ArgumentMatchers.anyString;
 import static org.mockito.Mockito.mock;
 import static org.mockito.Mockito.verify;
 import static org.mockito.Mockito.when;
 import java.util.Optional;
 import org.mockito.MockedStatic;
 import org.mockito.Mockito;

 import java.sql.Connection;

 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;

 import org.junit.jupiter.api.Test;
 import org.mockito.ArgumentCaptor;

 import edu.duke.ece651.team1.data_access.DB_connect;
 import edu.duke.ece651.team1.shared.AttendanceEntry;
 import edu.duke.ece651.team1.shared.AttendanceStatus;

 public class AttendanceEntryDAOTest {
     Connection mockConnection = mock(Connection.class);
     PreparedStatement mockStatement = mock(PreparedStatement.class);
    
     @Test
     void testAddAttendanceEntry() throws SQLException {
         MockedStatic<DB_connect> db = Mockito.mockStatic(DB_connect.class);
         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
         db.when(DB_connect::getConnection).thenReturn(mockConnection);
         when(mockStatement.executeUpdate()).thenReturn(1);
         AttendanceEntry entry = new AttendanceEntry(14, 15, AttendanceStatus.PRESENT);
         AttendanceEntryDAO.addAttendanceEntry(entry);
         ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
         verify(mockConnection).prepareStatement(sqlCaptor.capture());
         assertEquals("INSERT INTO AttendanceEntries (studentId, attendanceRecordId, status) VALUES (?, ?, ?)", sqlCaptor.getValue());
         verify(mockStatement).setLong(1, entry.getStudentId());
         verify(mockStatement).setLong(2, entry.getAttendanceRecordId());
         verify(mockStatement).setString(3, entry.getStatus().toString());
         verify(mockStatement).executeUpdate();
         db.close();
     }

     @Test
     public void testAddAttendanceEntryFailure() throws SQLException {
         MockedStatic<DB_connect> db = Mockito.mockStatic(DB_connect.class);
         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
         db.when(DB_connect::getConnection).thenReturn(mockConnection);
         when(mockStatement.executeUpdate()).thenReturn(0);
         AttendanceEntry entry = new AttendanceEntry(1, 2, AttendanceStatus.PRESENT);
         assertThrows(SQLException.class, () -> AttendanceEntryDAO.addAttendanceEntry(entry),
                     "Creating attendance entry failed, no rows affected.");
         db.close();
     }

     @Test
     void testFindAttendanceEntriesByAttendanceRecordId() throws SQLException {
         MockedStatic<DB_connect> db = Mockito.mockStatic(DB_connect.class);
         db.when(DB_connect::getConnection).thenReturn(mockConnection);
         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
         ResultSet mockResultSet = mock(ResultSet.class);
         when(mockStatement.executeQuery()).thenReturn(mockResultSet);
         when(mockResultSet.next()).thenReturn(true, false); // Simulate one result
//         when(mockResultSet.getInt("entryId")).thenReturn(1);
         when(mockResultSet.getInt("AttendanceEntryID")).thenReturn(1);
//         when(mockResultSet.getInt("studentId")).thenReturn(1);
         when(mockResultSet.getInt("StudentID")).thenReturn(1);
//         when(mockResultSet.getString("status")).thenReturn("PRESENT");
         when(mockResultSet.getString("AttendanceStatus")).thenReturn("PRESENT");
         Iterable<AttendanceEntry> entries = AttendanceEntryDAO.findAttendanceEntrisByattendanceRecordId(9);
//         verify(mockStatement).setLong(1, 9L);
         verify(mockStatement).setInt(1, 9);
         verify(mockStatement).executeQuery();
         AttendanceEntry resultEntry = entries.iterator().next();
         assertNotNull(resultEntry);
         assertEquals(1, resultEntry.getEntryId());
         assertEquals(1, resultEntry.getStudentId());
         assertEquals(AttendanceStatus.PRESENT, resultEntry.getStatus());
         db.close();
     }
     @Test
     void testFindAttendanceEntryIdByRecordIdAndStudentId() throws SQLException {
         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
             Connection mockConnection = mock(Connection.class);
             PreparedStatement mockStatement = mock(PreparedStatement.class);
             ResultSet mockResultSet = mock(ResultSet.class);
             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
             when(mockStatement.executeQuery()).thenReturn(mockResultSet);
             when(mockResultSet.next()).thenReturn(true);
//             when(mockResultSet.getInt("AttendanceEntryID")).thenReturn(1);
             // above line still seems to arise from a cast issue
             when(mockResultSet.getLong("AttendanceEntryID")).thenReturn(1L);
             Optional<Long> result = AttendanceEntryDAO.findAttendanceEntryIdByRecordIdAndStudentId(1,  1);
             assertTrue(result.isPresent());
             assertEquals(1L, result.get());
         }
     }

     @Test
     void testUpdateAttendanceEntry() throws SQLException {
         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
             Connection mockConnection = mock(Connection.class);
             PreparedStatement mockStatement = mock(PreparedStatement.class);
             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
             when(mockStatement.executeUpdate()).thenReturn(1);
             assertDoesNotThrow(() -> AttendanceEntryDAO.updateAttendanceEntry(1, 1, "Present"));
         }
     }

     @Test
     void testUpdateAttendanceEntryFailure() throws SQLException {
         try (MockedStatic<DB_connect> mockedDB = Mockito.mockStatic(DB_connect.class)) {
             Connection mockConnection = mock(Connection.class);
             PreparedStatement mockStatement = mock(PreparedStatement.class);
             mockedDB.when(DB_connect::getConnection).thenReturn(mockConnection);
             when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
             when(mockStatement.executeUpdate()).thenReturn(0);
             assertThrows(SQLException.class, () -> AttendanceEntryDAO.updateAttendanceEntry(1, 1, "Present"));
         }
     }


 }
