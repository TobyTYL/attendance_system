package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

import edu.duke.ece651.team1.client.model.CourseDetail;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.AttendanceService;
import edu.duke.ece651.team1.client.service.CourseService;
import edu.duke.ece651.team1.client.view.CourseView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.checkerframework.checker.units.qual.min;
import org.json.*;
import org.springframework.ui.Model;
import edu.duke.ece651.team1.shared.*;
import org.slf4j.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.duke.ece651.team1.shared.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @GetMapping("/new/{sectionId}")
    public String getCourses(@PathVariable int sectionId, Model model) {
        List<Student> students = attendanceService.getRoaster(sectionId);
        model.addAttribute("students", students);
        return "takeAttendance";
    }

    // student legal name : attendance status
    @PostMapping("/new/{sectionId}")
    public String sendAttendanceRecord(@RequestParam Map<String, String> allParams, @PathVariable int sectionId, RedirectAttributes redirectAttributes) {
        List<Student> students = attendanceService.getRoaster(sectionId);
        AttendanceRecord record = new AttendanceRecord();
        record.initializeFromRoaster(students);
        for (Student s : students) {
            int sid = s.getStudentId();
            String key = "attendanceStatus[" + sid + "]";
            String attendanceStatus = allParams.get(key);
            record.updateStudentStatus(s, AttendanceStatus.valueOf(attendanceStatus.toUpperCase()));
        }
        
        attendanceService.sendAttendanceRecord(record, sectionId);
        redirectAttributes.addFlashAttribute("successMessage", "You successfully submitted today's attendance record.");
        return "redirect:/attendance/record/"+sectionId+"/"+record.getSessionDate().toString();
    }

    @GetMapping("/record/{sectionId}/{sessionDate}")
    public String showRecord(@PathVariable int sectionId,@PathVariable String sessionDate,Model model) {
        AttendanceRecord record = attendanceService.getAttendanceRecord(sessionDate, sectionId);
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("record", record);
        return "attendanceRecord";
    }
    

}
