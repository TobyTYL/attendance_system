package edu.duke.ece651.team1.server.model;

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

    // Maps each student to their attendance records, sorted by date.
    private final Map<Student, Map<String, AttendanceStatus>> studentAttendanceRecords;
    private Set<Student> roster;
    private List<AttendanceRecord> records;

    /**
     * Constructs an AttendanceManager with the specified user name, roster, and
     * attendance records.
     *
     * 
     * @param roster  The set of students enrolled in the course.
     * @param records The list of attendance records for the course.
     */
    public AttendanceManager(Set<Student> roster, List<AttendanceRecord> records) {
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
        for (Student student : roster) {
            studentAttendanceRecords.put(student, new TreeMap<>());
        }
        for (AttendanceRecord record : records) {
            String sessionDate = record.getSessionDate().toString();
            for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
                Student student = entry.getKey();
                AttendanceStatus status = entry.getValue();
                if (roster.contains(student)) {
                    studentAttendanceRecords.get(student).put(sessionDate, status);
                }
            }
        }
    }
    /**
     * Get the attendance records of a student.
     * @param records
     * @return
     */
    public String getDetailAttendanceInfor(Map<String, AttendanceStatus> records) {
        StringBuilder infoBuilder = new StringBuilder();
        for (Map.Entry<String, AttendanceStatus> entry : records.entrySet()) {
            String date = entry.getKey();
            AttendanceStatus status = entry.getValue();
            infoBuilder.append(date).append(": ").append(status.getStatus()).append("\n");
        }
        return infoBuilder.toString();

    }

    /**
     * Generates an attendance report for a specific student.
     *
     * @param student The student for whom the report is to be generated.
     * @return A string representing the attendance report of the student.
     */
    public String generateReport(Student student, boolean details) {
        Map<String, AttendanceStatus> records = studentAttendanceRecords.get(student);
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Attendance Report for ").append(student.getDisPlayName()).append(":\n");
        if (details) {
            reportBuilder.append(getDetailAttendanceInfor(records));
        }
        int totalCount = records.size();
        double[] data = calculateAttendance(records);
        double attenceRate = data[0];
        int attendedCount = (int) data[1];
        int tardyCount = (int) data[2];
        reportBuilder.append("Total Attendance: ").append(attendedCount).append("/").append(totalCount)
        .append(" (").append(String.format("%.2f", attenceRate)).append("% attendance rate), ")
        .append("Tardy Count: ").append(tardyCount);
        return reportBuilder.toString();
    }

    /**
     * Calculates the attendance rate and count of attended sessions
     * Tardy / Present all counted to attended.
     * 
     * @param records The attendance records of a student.
     * @return An array where the first element is the attendance rate and the
     *         second is the count of attended sessions.
     */
    public double[] calculateAttendance(Map<String, AttendanceStatus> records) {
        double attendCount = 0;
        double tardyCount = 0;
        for (Map.Entry<String, AttendanceStatus> entry : records.entrySet()) {
            AttendanceStatus status = entry.getValue();
            if (status == AttendanceStatus.PRESENT) {
                attendCount++;
            } else if (status == AttendanceStatus.TARDY) {
                tardyCount++;
            }
        }
        return new double[] { ((double) (attendCount + tardyCount * 0.8) / records.size()) * 100, attendCount,
                tardyCount };
    }

    // showing, for each student in a class the attendance participation. Tardy
    // students will count towards 80% of participation that day.
    /**
     * Generates a class attendance report. This method calculates the attendance
     * @return
     */
    public String generateClassReport() {
        StringBuilder classReportBuilder = new StringBuilder("Class Attendance Report:\n");
        for (Map.Entry<Student, Map<String, AttendanceStatus>> studentRecord : studentAttendanceRecords.entrySet()) {
            Student student = studentRecord.getKey();
            Map<String, AttendanceStatus> records = studentRecord.getValue();
            double[] attendanceData = calculateAttendance(records);
            double attendanceRate = attendanceData[0];
            int attendedCount =(int) attendanceData[1];
            int tardyCount = (int)attendanceData[2];
            int totalCount = records.size();
            classReportBuilder.append(student.getDisPlayName())
                    .append(": Attended ")
                    .append((int) attendedCount)
                    .append("/")
                    .append(totalCount)
                    .append(" sessions (")
                    .append(String.format("%.2f", attendanceRate))
                    .append("% attendance rate), Tardy Count: ")
                    .append(tardyCount)
                    .append(".\n");
        }
        return classReportBuilder.toString();
    }

}
