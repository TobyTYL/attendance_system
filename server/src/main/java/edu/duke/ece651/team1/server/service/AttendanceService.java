package edu.duke.ece651.team1.server.service;

import edu.duke.ece651.team1.server.controller.SecurityController;
import edu.duke.ece651.team1.server.model.EmailNotification;
import edu.duke.ece651.team1.server.model.NotificationService;
import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
import edu.duke.ece651.team1.shared.*;
// import java.io.*;

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

@Service
public class AttendanceService {
    @Value("${attendanceRecords.path}")
    private String attendanceRecordsPath;
    private NotificationService nService = new NotificationService();
    @Autowired
    private InMemoryAttendanceRepository inMemoryAttendanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);


    // public void setAttendanceRecordsPath(String attendanceRecordsPath) {
    //     this.attendanceRecordsPath = attendanceRecordsPath;
    // }
    @PostConstruct
    public void initializeNotification() {
        try {
            nService.addNotification(new EmailNotification());
            // logger.info("add notification successful");
        } catch (Exception e) {
            // logger.info("unable to add email notification because "+e.getMessage());
        }

    }
    

    public void saveAttendanceRecord(String record, String userName) throws IOException {
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        AttendanceRecord attendanceRecord = serializer.deserialize(record);
        inMemoryAttendanceRepository.saveAttendanceRecord(attendanceRecord, userName);
    }

    public List<String> getRecordDates(String userName) throws IOException {
        return inMemoryAttendanceRepository.getRecordDates(userName);
    }

    public String getRecord(String userName, String sessionDate) throws IOException {
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        AttendanceRecord record = inMemoryAttendanceRepository.getRecord(userName, sessionDate);
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
     * get student record entry by searching the username and session date and
     * student name
     * for later modify the entry (attendance status)
     * 
     * @param userName
     * @param sessionDate
     * @param studentName
     * @return
     * @throws IOException
     */
    public String getStudentRecordEntry(String userName, String sessionDate, String studentName) throws IOException {
        AttendanceRecord record = inMemoryAttendanceRepository.getRecord(userName, sessionDate);
        Student foundStudent = findStudentByLegalName(record, studentName);
        if (foundStudent != null) {
            AttendanceStatus status = record.getEntries().get(foundStudent);
            return "Student Name: " + studentName + ", Attendance Status: " + status;
        }

        return "No attendance record found for student: " + studentName;
    }

    public String generateAttendanceNotification(String studentName,  String sessionDate,String attendanceStatus){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(studentName).append("\n")
                    .append("We would like to inform you that your attendance status for the ").append(sessionDate)
                    .append(" has been updated  to ").append(attendanceStatus);
        return stringBuilder.toString();
    }

    public void sendMessage(String studentName, String studentEmail, String sessionDate,String attendanceStatus){
        String message = generateAttendanceNotification(studentName, sessionDate, attendanceStatus);
        nService.notifyObserver(message, studentEmail);
    }

    

    // string attendanceEntry {legal name:yitiao, Atttendance Status: Present}
    public String modifyStudentEntryAndSendUpdates(String userName, String sessionDate, String attendanceEntryJson) {
      
        try {
            JSONObject json = new JSONObject(attendanceEntryJson);
            String studentName = json.getString("Legal Name");
            String statusString = json.getString("Attendance Status");
            AttendanceStatus newStatus = AttendanceStatus.valueOf(statusString.toUpperCase());
            AttendanceRecord record = inMemoryAttendanceRepository.getRecord(userName, sessionDate);

            // Find and update the student's status
            Student foundStudent = findStudentByLegalName(record, studentName);
            if (foundStudent != null) {
                record.updateStudentStatus(foundStudent, newStatus);
                sendMessage(studentName, foundStudent.getEmail(), sessionDate,newStatus.getStatus() );
                inMemoryAttendanceRepository.saveAttendanceRecord(record, userName);

                return "Successfully updated attendance status for " + studentName;
            } else {
               
                return "Student not found in the attendance record for " + sessionDate;
                
            }

        } catch (IOException e) {
            // Handle file reading/writing errors
            logger.info("io error on modify entry", e);
            return "Failed to modify attendance record: " + e.getMessage();
        } catch (JSONException e) {
            logger.info("json error on modify entry", e);
            return "Invalid JSON format for attendance entry: " + e.getMessage();
           
        }
    }

}
