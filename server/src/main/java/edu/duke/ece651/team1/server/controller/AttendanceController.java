package edu.duke.ece651.team1.server.controller;

import java.io.IOException;

import java.sql.*;
import edu.duke.ece651.team1.server.service.AttendanceService;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import java.util.List;
import edu.duke.ece651.team1.shared.Student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.NoSuchElementException;

/**
 * The AttendanceController class handles HTTP requests related to managing attendance records.
 * It supports operations such as recording, updating, and retrieving attendance data.
 */
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService ;

    /**
     * Handles POST requests to submit an attendance record.
     *
     * @param record The attendance record in JSON format.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping("/record/{sectionID}")
    public ResponseEntity<String> SubmitAttendanceRecord(@PathVariable int sectionID, @RequestBody String record) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            attendanceService.saveAttendanceRecord(record, sectionID);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save attendance record because" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Attendance record saved successfully", HttpStatus.OK);
    }
    /**
     * Updates an existing attendance record for a given section.
     * 
     * @param sectionID The identifier of the section.
     * @param record The updated attendance record details in JSON format.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping("/record/{sectionID}")
    public ResponseEntity<String> updateAttendanceRecord(@PathVariable int sectionID, @RequestBody String record) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            attendanceService.updateAttendanceRecord(record);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update attendance record because" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Attendance record update successfully", HttpStatus.OK);
    }


    /**
     * Retrieves a list of dates for which attendance records are available for a specific section.
     * 
     * @param sectionID The identifier of the section.
     * @return ResponseEntity containing a list of dates or an empty list if no records exist.
     */
    @GetMapping("/record-dates/{sectionID}")
    // fetch available record date
    public ResponseEntity<List<String>> getAttendanceRecordDates(@PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            List<String> dates = attendanceService.getRecordDates(sectionID);
            // get empty list or list of date string
            // empty is allowed here, because user might not have a attendance record now.
            return new ResponseEntity<>(dates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET requests to retrieve an attendance record for a specific session
     * date.
     *
     * @param sessionDate The session date for which the attendance record is
     *                    requested.
     * @return ResponseEntity containing the attendance record in JSON format or an
     *         error message if not found.
     */
    @GetMapping("/record/{sectionID}/{sessionDate}")
    // fetch attendance record for a specific date
    public ResponseEntity<String> getAttendanceRecord(@PathVariable String sessionDate, @PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String record = attendanceService.getRecord(sectionID, sessionDate);
            return new ResponseEntity<>(record, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>("Failed to fetch record because " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This method fetches the attendance entry for a specific student on a given date.
     * It provides detailed information about the student's attendance status for that session.
     *
     * @param sessionDate The date of the session for which the attendance record is requested.
     * @param studentName The name of the student whose record is being queried.
     * @param sectionID The identifier of the section.
     * @return ResponseEntity containing the attendance details or an error message if not found.
     */
    // API for getting student attendance record based on sessionDate and name
    @GetMapping("/entry/{sectionID}/{sessionDate}/{studentName}")
    public ResponseEntity<String> getStudentRecordEntry(@PathVariable String sessionDate,
            @PathVariable String studentName, @PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String responseMessage = attendanceService.getStudentRecordEntry(sectionID, sessionDate, studentName);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>("Failed to fetch student attendance entry: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Generic catch block for other exceptions
            return new ResponseEntity<>("Error fetching student attendance entry: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Modifies the attendance entry for a student. This method allows updating the attendance status
     * such as marking a student tardy or absent.
     *
     * @param sessionDate The date of the session to modify.
     * @param attendanceEntryJson JSON containing the attendance details to be updated.
     * @param sectionID The identifier of the section where the record is maintained.
     * @return ResponseEntity indicating the result of the update operation.
     */
    // API for modify student attendance status,
    // Key: Legal Name: yitiao, Attendance Status: Tardy
    @PostMapping("/modification/{sectionID}/{sessionDate}")
    public ResponseEntity<String> modifyAttendanceEntry(@PathVariable String sessionDate,
            @RequestBody String attendanceEntryJson, @PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String responseMessage = attendanceService.modifyStudentEntryAndSendUpdates(sectionID, sessionDate,
                    attendanceEntryJson);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error modifying attendance entry: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Retrieves a list of all students enrolled in a specified section. This can be used to quickly view
     * which students are part of a section.
     *
     * @param sectionID The identifier of the section.
     * @return ResponseEntity containing a list of students or an empty list if none found.
     */
    @GetMapping("/allStudents/{sectionID}")
    public ResponseEntity<List<Student>> getAllStudent(@PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            List<Student> students = attendanceService.getAllStudent(sectionID);
            if (students.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Generates a report of attendance records for a professor. This is useful for professors to get
     * an overview of attendance in their sections.
     *
     * @param sectionID The identifier of the section for which the report is requested.
     * @return ResponseEntity containing the attendance report or an error message if unable to generate.
     */
    @GetMapping("/report/class/{sectionID}")
    public ResponseEntity<String> getReportForProfessor(@PathVariable int sectionID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String report = attendanceService.getAttendanceReportForProfessor(sectionID);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Get class Report error because " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        /**
     * Generates a detailed attendance report for a student. This report can include detailed
     * information about the student's attendance, depending on the 'detail' parameter.
     *
     * @param studentID The identifier of the student for whom the report is generated.
     * @param sectionID The identifier of the section.
     * @param detail Boolean flag indicating whether detailed information should be included.
     * @return ResponseEntity containing the attendance report or an error message.
     */
    @GetMapping("/report/student/{studentID}/{sectionID}")
    public ResponseEntity<String> getReportForStudent(@PathVariable int studentID,@PathVariable int sectionID,@RequestParam(value = "detail") Boolean detail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String report = attendanceService.getAttendanceReportForStudent(studentID,sectionID,detail);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Get student Report error because " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/mark/{sectionID}/{sessionDate}/{studentId}")
    public ResponseEntity<String> markPresent(@PathVariable String sessionDate,
 @PathVariable int sectionID, @PathVariable int studentId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    try{
        attendanceService.updateStudentAttendance(sessionDate, sectionID, studentId);
        return ResponseEntity.ok("Attendance marked successfully.");
    }catch(IllegalArgumentException e){
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }catch(NoSuchElementException e){
        return ResponseEntity.notFound().build();
    }catch(Exception e){
        return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
    }
    }
    
    
}
