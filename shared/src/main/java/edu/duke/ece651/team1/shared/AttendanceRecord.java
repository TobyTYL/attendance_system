package edu.duke.ece651.team1.shared;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Iterables;;
public class AttendanceRecord {
    private final LocalDate sessionDate;
    private final HashMap<Student, AttendanceStatus> entries;
   
   
    public AttendanceRecord(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
        this.entries = new HashMap<>();
    }
    public AttendanceRecord(){
        this(LocalDate.now());
    }
    

    public void initializeAttendanceEntry(Student student){
        // checkStudentRepetition(student);
        entries.put(student, AttendanceStatus.ABSENT);
        
    }

    public void initializeFromRoaster(Iterable<Student> students){
        for(Student s:students){
            initializeAttendanceEntry(s);
        }
    }

    public void checkStudentInRecord(Student student){
        if(entries.get(student)==null){
            throw new IllegalArgumentException("student does not exist in the attendance record");
        }
    }
    
    public AttendanceStatus getAttendanceEntry(Student student){
        checkStudentInRecord(student);
        return entries.get(student);
    }

    public void markPresent(Student student){
        checkStudentInRecord(student);
        entries.put(student, AttendanceStatus.PRESENT);
    }
    public void markTardy(Student student){
        checkStudentInRecord(student);
        entries.put(student, AttendanceStatus.TARDY);
    }
    public void markAbsent(Student student){
        checkStudentInRecord(student);
        entries.put(student, AttendanceStatus.ABSENT);
    }

    public void updateStudentStatus(Student student,AttendanceStatus status){
        checkStudentInRecord(student);
        entries.put(student, status);
    }
    public Iterable<Map.Entry<Student, AttendanceStatus>> getSortedEntries(){
        List<Map.Entry<Student, AttendanceStatus>> entryList = new ArrayList<>(entries.entrySet());
        Collections.sort(entryList, (entry1, entry2) -> entry1.getKey().getDisPlayName().compareTo(entry2.getKey().getDisPlayName()));
        return entryList;
    }
    // public String displayAttendance() {
    //     StringBuilder ans = new StringBuilder();
    //     String newLine = "\n";
    //     Iterable<Map.Entry<Student, AttendanceStatus>> entryList = getSortedEntries();
    //     ans.append("Attendance Record for " +sessionDate).append(newLine);
    //     ans.append("----------------------------").append(newLine);
    //     for (Map.Entry<Student, AttendanceStatus> entry :entryList) {
    //         Student student = entry.getKey();
    //         AttendanceStatus status = entry.getValue();
    //         ans.append(student.getDisPlayName() + ": " + status.getStatus()).append(newLine);
    //     }
    //     ans.append("----------------------------");
    //     return ans.toString();
    // }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    
    
}
