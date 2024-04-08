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

 import java.util.Locale;
 import java.util.Optional;

 import org.junit.jupiter.api.AfterEach;
 import org.junit.jupiter.api.BeforeEach;
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
     private MockedStatic<DB_connect> mockedDB;
     @BeforeEach
     public void setUp() throws SQLException {
         // Create mocks before each test
         mockedDB = Mockito.mockStatic(DB_connect.class);
         mockConnection = mock(Connection.class);
         mockStatement = mock(PreparedStatement.class);

         // Common stubbing used by multiple tests
         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
         when(DB_connect.getConnection()).thenReturn(mockConnection);
     }

     @AfterEach
     public void tearDown() {
         // Close resources and mocks after each test
         mockedDB.close();
         // Reset other shared resources if necessary
     }
    


     @Test
     void testAddAttendanceEntry2() throws SQLException {
         // Assuming mockedDB, mockConnection, and mockStatement have been initialized in @BeforeEach
         when(mockStatement.executeUpdate()).thenReturn(1);

         AttendanceEntry entry = new AttendanceEntry(14,  15, AttendanceStatus.PRESENT);
         AttendanceEntryDAO.addAttendanceEntry(entry);

         ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
         verify(mockConnection).prepareStatement(sqlCaptor.capture());

         assertEquals("INSERT INTO AttendanceEntries (StudentID, AttendanceRecordID, AttendanceStatus) VALUES (?, ?, ?)", sqlCaptor.getValue());

         verify(mockStatement).setInt(1, entry.getStudentId());
         verify(mockStatement).setInt(2, entry.getAttendanceRecordId());
//         verify(mockStatement).setString(3, entry.getStatus().toString()); // Changed to match the actual method call
         verify(mockStatement).setString(3, "Present");
         verify(mockStatement).executeUpdate();
         // No need to close here since we're using @AfterEach to manage your resources.
     }



     @Test
     public void testAddAttendanceEntryFailure() throws SQLException {
         when(mockStatement.executeUpdate()).thenReturn(0); // Simulate failure by returning 0 updated rows

         AttendanceEntry entry = new AttendanceEntry(1, 2, AttendanceStatus.PRESENT);
         assertThrows(SQLException.class, () -> AttendanceEntryDAO.addAttendanceEntry(entry),
                 "Creating attendance entry failed, no rows affected.");
     }




     @Test
     void testFindAttendanceEntriesByAttendanceRecordId() throws SQLException {
         ResultSet mockResultSet = mock(ResultSet.class);
         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
         when(mockStatement.executeQuery()).thenReturn(mockResultSet);
         when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result followed by end of results
         when(mockResultSet.getInt("AttendanceEntryID")).thenReturn(1);
         when(mockResultSet.getInt("StudentID")).thenReturn(1);
         when(mockResultSet.getString("AttendanceStatus")).thenReturn("PRESENT");

         Iterable<AttendanceEntry> entries = AttendanceEntryDAO.findAttendanceEntrisByattendanceRecordId(9);

         // Check that the query is setting the correct parameter
         verify(mockStatement).setInt(1, 9);
         verify(mockStatement).executeQuery();

         // Assert that the entries are correct
         AttendanceEntry resultEntry = entries.iterator().next();
         assertNotNull(resultEntry);
         assertEquals(1, resultEntry.getEntryId());
         assertEquals(1, resultEntry.getStudentId());
         assertEquals(AttendanceStatus.PRESENT, resultEntry.getStatus());

         // No need to manually close the mocked DB as it should be managed by @AfterEach
     }


     @Test
     void testFindAttendanceEntryIdByRecordIdAndStudentId() throws SQLException {
         ResultSet mockResultSet = mock(ResultSet.class);
         when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
         when(mockStatement.executeQuery()).thenReturn(mockResultSet);
         when(mockResultSet.next()).thenReturn(true);
         when(mockResultSet.getLong("AttendanceEntryID")).thenReturn(1L); // Simulate finding an entry with ID 1

         Optional<Long> result = AttendanceEntryDAO.findAttendanceEntryIdByRecordIdAndStudentId(1, 1);

         assertTrue(result.isPresent());
         assertEquals(1L, result.get());
     }

     @Test
     void testUpdateAttendanceEntry() throws SQLException {
         when(mockStatement.executeUpdate()).thenReturn(1); // Simulate successful update

         // Since the update is successful, we expect no exception to be thrown
         assertDoesNotThrow(() -> AttendanceEntryDAO.updateAttendanceEntry(1, 1, "Present"));
     }



     @Test
     void testUpdateAttendanceEntryFailure() throws SQLException {
         when(mockStatement.executeUpdate()).thenReturn(0); // Simulate update failure

         // Expect an SQLException because no rows should be affected (update failed)
         assertThrows(SQLException.class, () -> AttendanceEntryDAO.updateAttendanceEntry(1, 1, "Present"));
     }

 }
