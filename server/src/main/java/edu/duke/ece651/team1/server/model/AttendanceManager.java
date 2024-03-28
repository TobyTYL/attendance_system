package edu.duke.ece651.team1.server.model;
import edu.duke.ece651.team1.server.repository.InMemoryAttendanceRepository;
import edu.duke.ece651.team1.server.repository.InMemoryRosterRepository;
import edu.duke.ece651.team1.shared.*;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Collection;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
/**
 * Manages attendance records for a set of students. This class is capable of
 * initializing attendance records, generating attendance reports 
 * and calculating attendance statistics for individual students.
 */
public class AttendanceManager {
    private  String userName;
    // Maps each student to their attendance records, sorted by date.
    private final Map<Student,Map<String,AttendanceStatus>> studentAttendanceRecords;
    private Set<Student> roster;
    private List<AttendanceRecord> records;
   
     /**
     * Constructs an AttendanceManager with the specified user name, roster, and attendance records.
     *
     * @param userName The user name of the professor
     * @param roster   The set of students enrolled in the course.
     * @param records  The list of attendance records for the course.
     */
    public AttendanceManager(String userName, Set<Student> roster, List<AttendanceRecord> records) {
        this.userName = userName;
        this.roster = roster;
        this.records = records;
        this.studentAttendanceRecords = new HashMap<>();
        initializeStudentAttendanceRecords();
    }




    /**
     * Initializes the student attendance records. This method fill the
     * studentAttendanceRecords map with attendance data from the records list.
     * sample:
     */
    @PostConstruct
    public void initializeStudentAttendanceRecords() {
        for(Student student:roster){
            studentAttendanceRecords.put(student, new TreeMap<>());
        }
        for(AttendanceRecord record : records) {
            String sessionDate = record.getSessionDate().toString();
            for(Map.Entry<Student,AttendanceStatus> entry:record.getSortedEntries()){
                Student student = entry.getKey();
                AttendanceStatus status = entry.getValue();
                if(roster.contains(student)){
                    studentAttendanceRecords.get(student).put(sessionDate, status);
                }
            }
        }
    }
     /**
     * Generates an attendance report for a specific student.
     *
     * @param student The student for whom the report is to be generated.
     * @return A string representing the attendance report of the student.
     */
    public String generateReport(Student student)  {
        Map<String, AttendanceStatus> records=studentAttendanceRecords.get(student);
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Attendance Report for ").append(student.getDisPlayName()).append(":\n");
        for(Map.Entry<String, AttendanceStatus> entry :records.entrySet()){
            String date = entry.getKey();
            AttendanceStatus status = entry.getValue();
            reportBuilder.append(date).append(": ").append(status.getStatus()).append("\n");
        }
        int totalCount = records.size();
        double[] data = calculateAttendance(records);
        double attenceRate = data[0];
        int attendedCount = (int)data[1];
        reportBuilder.append("Total Attendance: ").append(attendedCount).append("/").append(totalCount)
                  .append(" (").append(String.format("%.2f", attenceRate)).append("% attendance rate)");
        return reportBuilder.toString();
    }
    /**
     * Calculates the attendance rate and count of attended sessions
     * Tardy / Present all counted to attended.
     * @param records The attendance records of a student.
     * @return An array where the first element is the attendance rate and the second is the count of attended sessions.
     */
    public double[] calculateAttendance(Map<String, AttendanceStatus> records){
        int attendCount = 0;
        for(Map.Entry<String, AttendanceStatus> entry :records.entrySet()){
            AttendanceStatus status = entry.getValue();
            if(status==AttendanceStatus.PRESENT || status==AttendanceStatus.TARDY){
                attendCount++;
            }
        }
        return new double[]{((double) attendCount / records.size()) * 100,attendCount};
    }


  

    

}
