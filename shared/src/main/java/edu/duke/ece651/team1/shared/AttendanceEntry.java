package edu.duke.ece651.team1.shared;
import java.time.*;

public class AttendanceEntry {
        private Long entryId; // Unique identifier for the entry
        private Long studentId; // Identifier for the student this entry refers to
        private Long attendanceRecordId; // Link to the specific attendance record
        private AttendanceStatus status; // The status of the attendance (present, absent, etc.)
    
        // Constructor
        public AttendanceEntry(Long studentId, Long attendanceRecordId, AttendanceStatus status) {
            this.entryId = null;
            this.studentId = studentId;
            this.attendanceRecordId = attendanceRecordId;
            this.status = status;
        }

        public void setEntryId(Long entryId) {
            this.entryId = entryId;
        }

        public Long getEntryId() {
            return entryId;
        }

        public Long getStudentId() {
            return studentId;
        }

        public Long getAttendanceRecordId() {
            return attendanceRecordId;
        }

        public AttendanceStatus getStatus() {
            return status;
        }
    
}

   