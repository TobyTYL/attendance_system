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
// @Component
public class AttendanceManager {
    private  String userName;
    private final Map<Student,Map<String,AttendanceStatus>> studentAttendanceRecords;
    private Set<Student> roster;
    private List<AttendanceRecord> records;
   
   
    public AttendanceManager(String userName, Set<Student> roster, List<AttendanceRecord> records) {
        this.userName = userName;
        this.roster = roster;
        this.records = records;
        this.studentAttendanceRecords = new HashMap<>();
        initializeStudentAttendanceRecords();
    }





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

    public String generateReport(Student student) throws IOException {
        
        Map<String, AttendanceStatus> records=studentAttendanceRecords.get(student);
        // StringBuilder 
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Attendance Report for ").append(student.getDisPlayName()).append(":\n");
        for(Map.Entry<String, AttendanceStatus> entry :records.entrySet()){
            String date = entry.getKey();
            AttendanceStatus status = entry.getValue();
            reportBuilder.append(date).append(": ").append(status.getStatus()).append("\n");
        }
        int totalCount = records.size();
        double[] data = calculateAttendance(records, totalCount);
        double attenceRate = data[0];
        int attendedCount = (int)data[1];
        reportBuilder.append("Total Attendance: ").append(attendedCount).append("/").append(totalCount)
                  .append(" (").append(String.format("%.2f", attenceRate)).append("% attendance rate)");
        return reportBuilder.toString();
    }
    //return attendance rate and attendance count
    public double[] calculateAttendance(Map<String, AttendanceStatus> records,int totalCount){
        int attendCount = 0;
        //tardy or present all could be count into present
        for(Map.Entry<String, AttendanceStatus> entry :records.entrySet()){
            AttendanceStatus status = entry.getValue();
            if(status==AttendanceStatus.PRESENT || status==AttendanceStatus.TARDY){
                attendCount++;
            }
        }
        return new double[]{((double) attendCount / totalCount) * 100,attendCount};
    }


  

    

}
