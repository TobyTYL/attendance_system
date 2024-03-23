package edu.duke.ece651.team1.server.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import edu.duke.ece651.team1.server.service.AttendanceService;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import java.util.Collections;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/record")
    public ResponseEntity<String> SubmitAttendanceRecord(@RequestBody String record) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            attendanceService.saveAttendanceRecord(record,auth.getName());
        }catch(Exception e){
            return new ResponseEntity<>("Failed to save attendance record because"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Attendance record saved successfully", HttpStatus.OK);
       
    }
    @GetMapping("/record-dates")
    public ResponseEntity<List<String>> getMethodName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<String> dates=attendanceService.getRecordDates(auth.getName());
            //get empty list or list of date string
            //empty is allowed here, because user might not have a attendance record now.
            return new ResponseEntity<>(dates, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/record/{sessionDate}")
    public ResponseEntity<String> getMethodName(@PathVariable String sessionDate) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       try{
            String record =attendanceService.getRecord(auth.getName(), sessionDate);
            return new ResponseEntity<>(record, HttpStatus.OK);
       }catch(IOException e){
            return new ResponseEntity<>("Failed to fetch record because "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    
    

   
}
