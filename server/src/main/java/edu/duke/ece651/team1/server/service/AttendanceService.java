package edu.duke.ece651.team1.server.service;

import edu.duke.ece651.team1.data_access.Attendance.AttendanceEntryDAO;
import edu.duke.ece651.team1.data_access.Attendance.AttendanceRecordDAO;
import edu.duke.ece651.team1.server.controller.SecurityController;
import edu.duke.ece651.team1.server.model.EmailNotification;
import edu.duke.ece651.team1.server.model.NotificationService;
import edu.duke.ece651.team1.shared.*;
// import java.io.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import org.json.JSONObject;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This class provides services related to attendance management.
 */
@Service
public class AttendanceService {
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;
    private NotificationService nService = new NotificationService();
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    // public void setAttendanceRecordsPath(String attendanceRecordsPath) {
    // this.attendanceRecordsPath = attendanceRecordsPath;
    // }

    /**
     * Initializes the notification service.
     */
    @PostConstruct
    public void initializeNotification() {
        try {
            nService.addNotification(new EmailNotification());
            // logger.info("add notification successful");
        } catch (Exception e) {
            // logger.info("unable to add email notification because "+e.getMessage());
        }
    }

    /**
     * Saves the attendance record submitted by a user.
     *
     * @param record   The attendance record JSON string.
     * @param userName The username of the user submitting the record.
     * @throws IOException If an I/O error occurs while saving the record.
     */
    public void saveAttendanceRecord(String record, int sectionId) throws SQLException {
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        AttendanceRecord attendanceRecord = serializer.deserialize(record);
        AttendanceRecordDAO.addAttendanceRecord(attendanceRecord, sectionId);
    }

    /**
     * Retrieves the dates for which attendance records are available for a user.
     *
     * @param userName The username of the user.
     * @return A list of dates for which attendance records are available.
     * @throws IOException If an I/O error occurs while retrieving the dates.
     */
    public List<String> getRecordDates(int sectionId) throws SQLException {
        Iterable<AttendanceRecord> record = AttendanceRecordDAO.findAttendanceRecordsBysectionID(sectionId);
        Stream<AttendanceRecord> stream = StreamSupport.stream(record.spliterator(), false);
        List<String> dates = stream
                .map(attendanceRecord -> attendanceRecord.getSessionDate().toString()) // Replace getDate() with the actual method name
                .collect(Collectors.toList());
        return dates;

    }

    /**
     * Retrieves the attendance record for a user on a specific session date.
     *
     * @param userName    The username of the user.
     * @param sessionDate The session date for which to retrieve the record.
     * @return The attendance record JSON string.
     * @throws IOException If an I/O error occurs while retrieving the record.
     */
    public String getRecord(int sectionId, String sessionDate) throws SQLException {
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        AttendanceRecord record = AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId, LocalDate.parse(sessionDate));
        return serializer.serialize(record);
    }

    /**
     * find if the student is the record by searching by lagal name
     * 
     * @param record
     * @param studentName
     * @return
     */
    public Student findStudentByLegalName(AttendanceRecord record, String studentName) {
        for (Student student : record.getEntries().keySet()) {
            if (student.getLegalName().equals(studentName)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Retrieves the attendance record entry for a student.
     *
     * @param userName    The username of the user.
     * @param sessionDate The session date.
     * @param studentName The legal name of the student.
     * @return The attendance record entry.
     * @throws IOException If an I/O error occurs while retrieving the record.
     */
    public String getStudentRecordEntry(int sectionId, String sessionDate, String studentName) throws SQLException {
        AttendanceRecord record = AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId,  LocalDate.parse(sessionDate));
        Student foundStudent = findStudentByLegalName(record, studentName);
        if (foundStudent != null) {
            AttendanceStatus status = record.getEntries().get(foundStudent);
            return "Student Name: " + studentName + ", Attendance Status: " + status;
        }
        return "No attendance record found for student: " + studentName;
    }

    /**
     * This method generates an attendance notification message for a student.
     *
     * @param studentName      The name of the student.
     * @param sessionDate      The session date for which the notification is
     *                         generated.
     * @param attendanceStatus The updated attendance status of the student.
     * @return The attendance notification message.
     */
    public String generateAttendanceNotification(String studentName, String sessionDate, String attendanceStatus) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(studentName).append("\n")
                .append("We would like to inform you that your attendance status for the ").append(sessionDate)
                .append(" has been updated  to ").append(attendanceStatus);
        return stringBuilder.toString();
    }

    /**
     * This method sends an attendance notification message to a student.
     *
     * @param studentName      The name of the student.
     * @param studentEmail     The email address of the student.
     * @param sessionDate      The session date for which the notification is sent.
     * @param attendanceStatus The updated attendance status of the student.
     */
    public void sendMessage(String studentName, String studentEmail, String sessionDate, String attendanceStatus) {
        String message = generateAttendanceNotification(studentName, sessionDate, attendanceStatus);
        nService.notifyObserver(message, studentEmail);
    }

    // string attendanceEntry {legal name:yitiao, Atttendance Status: Present}
    /**
     * This method modifies a student's attendance entry and sends updates.
     *
     * @param userName            The username of the user modifying the attendance.
     * @param sessionDate         The session date for which the attendance is
     *                            modified.
     * @param attendanceEntryJson The JSON string containing the attendance entry.
     * @return A message indicating the success or failure of the operation.
     */
    public String modifyStudentEntryAndSendUpdates(int sectionId, String sessionDate, String attendanceEntryJson)  {

        try {
            JSONObject json = new JSONObject(attendanceEntryJson);
            String studentName = json.getString("Legal Name");
            String statusString = json.getString("Attendance Status");
            AttendanceStatus newStatus = AttendanceStatus.valueOf(statusString.toUpperCase());
            AttendanceRecord record = AttendanceRecordDAO.findAttendanceRecordBySectionIDAndSessionDate(sectionId,  LocalDate.parse(sessionDate));

            // Find and update the student's status
            Student foundStudent = findStudentByLegalName(record, studentName);
            if (foundStudent != null) {
                // record.updateStudentStatus(foundStudent, newStatus);
                int studentID = foundStudent.getStudentId();
                int recordID = record.getRecordId();
                AttendanceEntryDAO.updateAttendanceEntry(recordID, studentID, statusString);
                sendMessage(studentName, foundStudent.getEmail(), sessionDate, newStatus.getStatus());
                return "Successfully updated attendance status for " + studentName;
            } else {

                return "Student not found in the attendance record for " + sessionDate;

            }
        } catch (SQLException e) {
            // Handle file reading/writing errors
            logger.info("io error on modify entry", e);
            return "Failed to modify attendance record: " + e.getMessage();
        } catch (JSONException e) {
            logger.info("json error on modify entry", e);
            return "Invalid JSON format for attendance entry: " + e.getMessage();
        }
    }
}
