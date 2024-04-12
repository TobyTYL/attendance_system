package edu.duke.ece651.team1.data_access.Attendance;

import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import edu.duke.ece651.team1.shared.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.shared.AttendanceEntry;
/**
 * Provides data access object functionalities for handling attendance records in the database.
 */
public class AttendanceRecordDAO {
    /**
    * Adds an attendance record for a particular section to the database and iterates over
    * a map of attendance entries to add them to the database.
    * 
    * @param record The AttendanceRecord object to add to the database.
    * @param sectionId The ID of the section for which the attendance is being recorded.
    * @throws SQLException if there is a problem communicating with the database.

    */
    static StudentDao studentDao = new StudentDaoImp();

    public static void addAttendanceRecord(AttendanceRecord record, int sectionId) throws SQLException {
        String sql = "INSERT INTO AttendanceRecords (SectionId, SessionDate) VALUES (?, ?)";
        LocalDate date = record.getSessionDate();
        try (PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, sectionId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            int affectedRowsRecord = statement.executeUpdate();
            if (affectedRowsRecord == 0) {
                throw new SQLException("Creating attendance record failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                int recordId;
                if (generatedKeys.next()) {
                    recordId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating attendance record failed, no ID obtained.");
                }
                for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
                    Student student = entry.getKey();
                    AttendanceStatus status = entry.getValue();
                    AttendanceEntry attendanceEntry = new AttendanceEntry(student.getStudentId(), recordId, status);
                    AttendanceEntryDAO.addAttendanceEntry(attendanceEntry);
                }
            }
        }
    }
    /**
     * Fills an AttendanceRecord object with attendance entries from the database.
     * 
     * @param entries An Iterable of AttendanceEntry objects to be added to the AttendanceRecord.
     * @param record The AttendanceRecord object to be filled with entries.
     * @throws SQLException if a student corresponding to an entry cannot be found.
     */

    public static void fillAttendanceRecordEntriesMap(Iterable<AttendanceEntry> entries, AttendanceRecord record)
            throws SQLException {
        for (AttendanceEntry entry : entries) {
            int studentId = entry.getStudentId();
            AttendanceStatus status = entry.getStatus();
            if (studentDao.findStudentByStudentID(studentId).isPresent()) {
                Student student = studentDao.findStudentByStudentID(studentId).get();
                record.addAttendanceEntry(student, status);
            } else {
                throw new SQLException("get Attendance Record Error : cannot find student record");
            }

        }
    }
    /**
     * Finds an AttendanceRecord by section ID and session date.
     * 
     * @param sectionId The ID of the section.
     * @param sessionDate The date of the session.
     * @return An AttendanceRecord object if found, otherwise throws an exception.
     * @throws SQLException if the record cannot be found or there is a problem communicating with the database.
     */
    public static AttendanceRecord findAttendanceRecordBySectionIDAndSessionDate(int sectionId, LocalDate sessionDate)
            throws SQLException {
        String sql = "SELECT * From AttendanceRecords WHERE SectionID = ? AND SessionDate = ?";
        try (PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)) {
            statement.setInt(1, sectionId);
            statement.setDate(2, java.sql.Date.valueOf(sessionDate));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int recordId = resultSet.getInt("AttendanceRecordID");
                LocalDate date = resultSet.getDate("SessionDate").toLocalDate();
                AttendanceRecord record = new AttendanceRecord(date);
                Iterable<AttendanceEntry> entries = AttendanceEntryDAO
                        .findAttendanceEntrisByattendanceRecordId(recordId);
                fillAttendanceRecordEntriesMap(entries, record);
                record.setRecordId(recordId);
                return record;
            } else {
                throw new SQLException("get Attendance Record Error : cannot find record by id");
            }

        }
    }
    /**
     * Retrieves all AttendanceRecords for a given section ID.
     * 
     * @param sectionId The ID of the section.
     * @return An Iterable of AttendanceRecord objects.
     * @throws SQLException if there is a problem communicating with the database.
     */
    public static List<AttendanceRecord> findAttendanceRecordsBysectionID(int sectionId) throws SQLException {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT * From AttendanceRecords WHERE SectionID = ?";
        try (PreparedStatement statement = DB_connect.getConnection().prepareStatement(sql)) {
            statement.setInt(1, sectionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int recordId = resultSet.getInt("AttendanceRecordID");
                LocalDate sessionDate = resultSet.getDate("SessionDate").toLocalDate();
                AttendanceRecord record = new AttendanceRecord(sessionDate);
                List<AttendanceEntry> entries = AttendanceEntryDAO
                        .findAttendanceEntrisByattendanceRecordId(recordId);
                fillAttendanceRecordEntriesMap(entries, record);
                record.setRecordId(recordId);
                records.add(record);
            }
        }
        return records;

    }

     /**
     * Updates attendance entries for a given AttendanceRecord in the database.
     * 
     * @param record The AttendanceRecord object containing the entries to be updated.
     * @throws SQLException if there is a problem updating the entries in the database.
     */
    public static void updateAttendanceRecord(AttendanceRecord record) throws SQLException{
        int recordId = record.getRecordId();
        for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()){
            Student student = entry.getKey();
            AttendanceStatus status = entry.getValue();
            int studentId = student.getStudentId(); 
            AttendanceEntryDAO.updateAttendanceEntry(recordId, studentId, status.getStatus());
        }
    }
    //test whatever you want
    public static void main(String[] args) throws SQLException {
        
        Optional<Student> student1 = studentDao.findStudentByStudentID(2);
        Optional<Student> student2 = studentDao.findStudentByStudentID(4);
        AttendanceRecord record = new AttendanceRecord();
        record.addAttendanceEntry(student1.get(),AttendanceStatus.PRESENT);
        record.addAttendanceEntry(student2.get(),AttendanceStatus.TARDY);
        addAttendanceRecord(record, 1);
        System.out.println(findAttendanceRecordBySectionIDAndSessionDate(1, LocalDate.now()).toString());
        System.out.println("------------------------");
        for(AttendanceRecord attendanceRecord: findAttendanceRecordsBysectionID(1)){
            System.out.println(attendanceRecord.toString());
        }
        AttendanceRecord record2 = findAttendanceRecordBySectionIDAndSessionDate(1, LocalDate.now());
        record.markAbsent(student1.get());
        updateAttendanceRecord(record2);
        System.out.println(record2.toString());


    }



    
}
