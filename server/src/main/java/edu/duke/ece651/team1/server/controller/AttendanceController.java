package edu.duke.ece651.team1.server.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.duke.ece651.team1.server.service.AttendanceService;
import edu.duke.ece651.team1.shared.AttendanceRecord;
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/")
    public ResponseEntity<String> SubmitAttendanceRecord(@RequestBody String record) {
        try{
            
            attendanceService.saveAttendanceRecord(record);
          
        }catch(Exception e){
            return new ResponseEntity<>("Failed to save attendance record because"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Attendance record saved successfully", HttpStatus.OK);
       
    }

   
}
