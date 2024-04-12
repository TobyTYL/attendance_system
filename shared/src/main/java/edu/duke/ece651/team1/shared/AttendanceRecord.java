package edu.duke.ece651.team1.shared;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Iterables;
/**
 * Represents an attendance record for a single session on a specific date.
 * Allows initialization, updating, and querying of attendance status for students.
 */

public class AttendanceRecord {
    //id builder pattern 
    private Integer recordId;
    
    private final LocalDate sessionDate;
    private final HashMap<Student, AttendanceStatus> entries;
 
   
   /**
    * for each session, record the session date and the student and the corresponding status.
    * @param sessionDate
    */
    public AttendanceRecord(LocalDate sessionDate) {
        // not insert to database yet, use null to represent.
         this.recordId = null;
         this.sessionDate = sessionDate;
        this.entries = new HashMap<>();
    }
    /**
     * default constructor
     */
    public AttendanceRecord(){
        this(LocalDate.now());
    }
    
    /**
     * Initializes an attendance entry for a student as ABSENT by default.
     * @param student The student to initialize in the attendance record.
     */
    public void initializeAttendanceEntry(Student student){
        // checkStudentRepetition(student);
        entries.put(student, AttendanceStatus.ABSENT);
        
    }
    /**
     * Initializes attendance entries for multiple students from a roster.
     * @param students An Iterable collection of students.
     */
    public void initializeFromRoaster(Iterable<Student> students){
        for(Student s:students){
            initializeAttendanceEntry(s);
        }
    }
    /**
     * Adds an attendance entry for a student with a specific status.
     * @param student
     * @param status
     */
    public void addAttendanceEntry(Student student, AttendanceStatus status){
        entries.put(student, status);
    }
    /**
     * Checks if a student is in the attendance record.
     * @param student The student to check.
     * @throws IllegalArgumentException if the student does not exist in the attendance record.
     */
    public void checkStudentInRecord(Student student){
        if(entries.get(student)==null){
            throw new IllegalArgumentException("student does not exist in the attendance record");
        }
    }
    /**
     * Retrieves the attendance status of a specific student.
     * @param student The student whose attendance status is to be queried.
     * @return The attendance status of the student.
     */
    public AttendanceStatus getAttendanceEntry(Student student){
        checkStudentInRecord(student);
        return entries.get(student);
    }
     // Methods to mark a student's attendance status as PRESENT, TARDY, or ABSENT.
     /**
      * Marks a student as present for the session.
      * @param student
      */
    public void markPresent(Student student){
        checkStudentInRecord(student);
        entries.put(student, AttendanceStatus.PRESENT);
    }
    /**
     * Marks a student as tardy for the session.
     * @param student
     */
    public void markTardy(Student student){
        checkStudentInRecord(student);
        entries.put(student, AttendanceStatus.TARDY);
    }
    /**
     * Marks a student as absent for the session.
     * @param student
     */
    public void markAbsent(Student student){
        checkStudentInRecord(student);
        entries.put(student, AttendanceStatus.ABSENT);
    }
    /**
     * Updates the attendance status of a specific student.
     * @param student The student whose status is to be updated.
     * @param status The new attendance status for the student.
     */
    public void updateStudentStatus(Student student,AttendanceStatus status){
        checkStudentInRecord(student);
        entries.put(student, status);
    }
    /**
     * Returns a sorted iterable of the attendance entries, sorted by student display names.
     * @return A sorted Iterable of Map entries representing students and their attendance statuses.
     */
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
    /**
     * Returns the session date of the attendance record.
     * @return The session date.
     */
    public LocalDate getSessionDate() {
        return sessionDate;
    }
    /**
     * Returns the attendance entries in the record.
     * @return
     */
    public Map<Student, AttendanceStatus> getEntries() {
        return Collections.unmodifiableMap(entries);
    }
    /**
     * Set the record id.
     * @param recordId
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
    /**
     * Get the record id.
     * @return
     */
    public Integer getRecordId() {
        return recordId;
    }
    /**
     * Get the attendance status of a student.
     */
    @Override
    public String toString() {
        return "AttendanceRecord [recordId=" + recordId + ", sessionDate=" + sessionDate + ", entries=" + entries + "]";
    }
    
}
