package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;

import edu.duke.ece651.team1.client.model.AttendanceSummary;
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

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import edu.duke.ece651.team1.shared.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

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
        model.addAttribute("uid", UserSession.getInstance().getUid());
        return "takeAttendance";
    }

    // student legal name : attendance status
    @PostMapping("/new/{sectionId}")
    public String sendAttendanceRecord(@RequestParam Map<String, String> allParams, @PathVariable int sectionId,
            RedirectAttributes redirectAttributes) {
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
        return "redirect:/attendance/record/" + sectionId + "/" + record.getSessionDate().toString();
    }

    @GetMapping("/record/{sectionId}/{sessionDate}")
    public String showRecord(@PathVariable int sectionId, @PathVariable String sessionDate, Model model) {
        AttendanceRecord record = attendanceService.getAttendanceRecord(sessionDate, sectionId);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("record", record);
        return "attendanceRecord";
    }

    @GetMapping("/records/{sectionId}")
    public String getrecordList(@PathVariable int sectionId, Model model) {
        List<String> sessionDates = attendanceService.getRecordDates(sectionId);
        model.addAttribute("sessionDates", sessionDates);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("sectionId", sectionId);
        return "recordtable";
    }

    @PostMapping("/record/{sectionId}/{sessionDate}")
    public String postMethodName(@PathVariable int sectionId, @PathVariable String sessionDate,
            @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        AttendanceRecord record = attendanceService.getAttendanceRecord(sessionDate, sectionId);
        for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
            Student s = entry.getKey();
            int sid = s.getStudentId();
            String key = "attendanceStatus[" + sid + "]";
            if (allParams.containsKey(key)) {
                String attendanceStatus = allParams.get(key);
                if (!attendanceStatus.isEmpty()) {
                    record.updateStudentStatus(s, AttendanceStatus.valueOf(attendanceStatus.toUpperCase()));
                }
            }
        }
        attendanceService.updateAttendanceRecord(record, sectionId);
        redirectAttributes.addFlashAttribute("successMessage",
                "You successfully modify " + sessionDate + "'s attendance record.");
        return "redirect:/attendance/record/" + sectionId + "/" + record.getSessionDate().toString();
    }

    @GetMapping("/record/download/{sectionId}/{sessionDate}")
    public String dowonload(@PathVariable int sectionId, @PathVariable String sessionDate,
            @RequestParam(value = "format") String format, RedirectAttributes redirectAttributes) {
        attendanceService.exportRecord(sessionDate, sectionId, format);
        redirectAttributes.addFlashAttribute("successMessage",
                "You successfully download file please check client/../src/data");
        return "redirect:/attendance/records/" + sectionId;

    }

    @GetMapping("/record/{sectionId}/{sessionDate}/edit")
    @ResponseBody
    public ResponseEntity<Map<Integer, Object>> getAttendanceRecordForEdit(@PathVariable int sectionId,
            @PathVariable String sessionDate) {
        AttendanceRecord record = attendanceService.getAttendanceRecord(sessionDate, sectionId);
        Map<Integer, Object> responseData = new HashMap<>();
        for (Map.Entry<Student, AttendanceStatus> entry : record.getSortedEntries()) {
            Student s = entry.getKey();
            Map<String, String> studentData = new HashMap<>();
            studentData.put("displayName", s.getDisPlayName());
            studentData.put("attendanceStatus", entry.getValue().toString());
            responseData.put(s.getStudentId(), studentData);
        }
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/record/report/class/{sectionId}")
    public String getClassReport(@PathVariable int sectionId, Model model){
        List<AttendanceSummary> summaries = attendanceService.getAttendancestatistic(sectionId);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("summaries", summaries);
        return "classReport";
    }
    @GetMapping("/test")
    public String getMethodName() {
        return "test";
    }
    


}
