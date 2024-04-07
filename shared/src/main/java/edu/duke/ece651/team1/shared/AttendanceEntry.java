package edu.duke.ece651.team1.shared;
import java.time.*;

public class AttendanceEntry {
        private Integer entryId; // Unique identifier for the entry
        private Integer studentId; // Identifier for the student this entry refers to
        private Integer attendanceRecordId; // Link to the specific attendance record
        private AttendanceStatus status; // The status of the attendance (present, absent, etc.)
    
        // Constructor
        public AttendanceEntry(Integer studentId, Integer attendanceRecordId, AttendanceStatus status) {
            this.entryId = null;
            this.studentId = studentId;
            this.attendanceRecordId = attendanceRecordId;
            this.status = status;
        }

        public void setEntryId(Integer entryId) {
            this.entryId = entryId;
        }

        public Integer getEntryId() {
            return entryId;
        }

        public Integer getStudentId() {
            return studentId;
        }

        public Integer getAttendanceRecordId() {
            return attendanceRecordId;
        }

        public AttendanceStatus getStatus() {
            return status;
        }
    
}

   