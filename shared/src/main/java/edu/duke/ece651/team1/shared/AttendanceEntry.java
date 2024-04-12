package edu.duke.ece651.team1.shared;
import java.time.*;
/**
 * Represents a single entry in an attendance record.
 */
public class AttendanceEntry {
        private Integer entryId; // Unique identifier for the entry
        private Integer studentId; // Identifier for the student this entry refers to
        private Integer attendanceRecordId; // Link to the specific attendance record
        private AttendanceStatus status; // The status of the attendance (present, absent, etc.)
    
        /**
         *  Constructor to create an attendance entry with the given parameters.
         * @param studentId
         * @param attendanceRecordId
         * @param status
         */
        public AttendanceEntry(Integer studentId, Integer attendanceRecordId, AttendanceStatus status) {
            this.entryId = null;
            this.studentId = studentId;
            this.attendanceRecordId = attendanceRecordId;
            this.status = status;
        }
        /**
         * create an attendance entry with the given parameters.
         * @param entryId
         */
        public void setEntryId(Integer entryId) {
            this.entryId = entryId;
        }
        /**
         * Get the entry id.
         * @return
         */
        public Integer getEntryId() {
            return entryId;
        }
        /**
         * Get the student id.
         * @return
         */
        public Integer getStudentId() {
            return studentId;
        }
        /**
         * Get the attendance record id.
         * @return
         */
        public Integer getAttendanceRecordId() {
            return attendanceRecordId;
        }
        /**
         * Get the status of the attendance.
         * @return
         */
        public AttendanceStatus getStatus() {
            return status;
        }
    
}

   