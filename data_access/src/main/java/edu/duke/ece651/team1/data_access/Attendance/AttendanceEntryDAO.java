package edu.duke.ece651.team1.data_access.Attendance;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Iterables;

import java.util.ArrayList;;

public class AttendanceEntryDAO {
    /**
     * Adds an attendance entry to the database.
     * 
     * @param entry The AttendanceEntry object containing the student ID, attendance
     *              record ID, and status.
     * @throws SQLException If the insertion into the database fails or no rows are
     *                      affected.
     */
    public static void addAttendanceEntry(AttendanceEntry entry) throws SQLException {
        String sql = "INSERT INTO AttendanceEntries (StudentID, AttendanceRecordID, AttendanceStatus) VALUES (?, ?, ?)";
        try (PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)) {
            // statement.setInt(1, entry.getEntryId());
            statement.setInt(1, entry.getStudentId());
            statement.setInt(2, entry.getAttendanceRecordId());
            statement.setString(3, entry.getStatus().getStatus());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating attendance entry failed, no rows affected.");
            }
        }
    }

    /**
     * Retrieves all attendance entries for a given attendance record ID.
     * 
     * @param attendanceRecordId The ID of the attendance record to search for.
     * @return An Iterable of AttendanceEntry objects matching the given attendance
     *         record ID.
     * @throws SQLException If there is an error performing the query.
     */
    public static List<AttendanceEntry> findAttendanceEntrisByattendanceRecordId(int attendanceRecordId)
            throws SQLException {
        String sql = "SELECT * FROM AttendanceEntries WHERE AttendanceRecordID = ?";
        List<AttendanceEntry> entries = new ArrayList<>();
        try (PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)) {
            statement.setInt(1, attendanceRecordId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int entryId = resultSet.getInt("AttendanceEntryID");
                int studentId = resultSet.getInt("StudentID");
                String status = resultSet.getString("AttendanceStatus");
                AttendanceEntry entry = new AttendanceEntry(studentId, attendanceRecordId,
                       AttendanceStatus.fromString(status));
                entry.setEntryId(entryId);
                entries.add(entry);
            }
        }
        return entries;
    }

    /**
     * Find an attendance entry ID by attendance record ID and student ID.
     * 
     * @param attendanceRecordId The attendance record ID to match.
     * @param studentId          The student ID to match.
     * @return An Optional containing the ID of the found attendance entry if it
     *         exists, or an empty Optional if not found.
     */
    public static Optional<Long> findAttendanceEntryIdByRecordIdAndStudentId(int attendanceRecordId, int studentId)
            throws SQLException {
        String sql = "SELECT AttendanceEntryID FROM AttendanceEntries WHERE AttendanceRecordID = ? AND StudentID = ?";
        try (PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)) {
            statement.setInt(1, attendanceRecordId);
            statement.setInt(2, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // this seems to be a type error
//                return Optional.of(resultSet.getInt("AttendanceEntryID"));
                return Optional.of(resultSet.getLong("AttendanceEntryID"));
            }
        }
        return Optional.empty();
    }

    /**
     * Updates the attendance status for a specific attendance entry identified by
     * record ID and student ID.
     * 
     * @param recordId  The ID of the attendance record associated with the entry.
     * @param studentId The ID of the student associated with the entry.
     * @param newStatus The new status to set for the attendance entry.
     * @throws SQLException If the update operation fails or no rows are affected.
     */

    public static void updateAttendanceEntry(int recordId, int studentId, String newStatus) throws SQLException {
        String sql = "UPDATE AttendanceEntries SET attendanceStatus = ? WHERE attendanceRecordId = ? AND studentId = ?";
        try (Connection connection = DB_connect.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setInt(2, recordId);
            statement.setInt(3, studentId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating attendance entry failed, no rows affected.");
            }
        }
    }
    
}
