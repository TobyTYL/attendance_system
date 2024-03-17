package edu.duke.ece651.team1.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student{
    private String legalName;
    private String displayName;
    private String studentId;
    private String email;
    protected Map<Course, List<AttendanceEntry>> attendanceRecordMap = new HashMap<>();

    public Student(String legalName, String displayName, String studentId, String email){
        this.legalName = legalName;
        this.displayName = displayName;
        this.studentId = studentId;
        this.email = email;
    }
    public String getLegalName(){
        return legalName;
    }
    
    public String getDisplayName(){
        return displayName;
    }
    
    public String getStudnetId(){
        return studentId;
    }
    
    public String getEmail(){
        return email;
    }

    public void markAttendance(Course course, AttendanceEntry entry){
        attendanceRecordMap.putIfAbsent(course, new ArrayList<>());
        attendanceRecordMap.get(course).add(entry);
    }

}
