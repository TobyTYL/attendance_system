package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import edu.duke.ece651.team1.client.model.AttendanceSummary;
import edu.duke.ece651.team1.client.model.CourseDetail;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.AttendanceService;
import edu.duke.ece651.team1.client.service.CourseService;
import edu.duke.ece651.team1.client.service.QRCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.checkerframework.checker.units.qual.min;
import org.json.*;
import org.springframework.ui.Model;
import edu.duke.ece651.team1.shared.*;
import org.slf4j.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import edu.duke.ece651.team1.shared.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@Controller
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    QRCodeService qrCodeService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @GetMapping("/new/{sectionId}")
    public String beginTakeAttendanceMan(@PathVariable int sectionId, Model model) {
        List<Student> students = attendanceService.getRoaster(sectionId);
        model.addAttribute("students", students);
        model.addAttribute("sectionId", sectionId);
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
    public String updateAttendanceRecord(@PathVariable int sectionId, @PathVariable String sessionDate,
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
            @RequestParam(value = "format") String format, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        attendanceService.exportRecord(sessionDate, sectionId, format,response);
        redirectAttributes.addFlashAttribute("successMessage",
                "You successfully download file please check ");
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
    public String getClassReport(@PathVariable int sectionId, Model model) {
        List<AttendanceSummary> summaries = attendanceService.getAttendancestatistic(sectionId);
        model.addAttribute("uid", UserSession.getInstance().getUid());
        model.addAttribute("summaries", summaries);
        return "classReport";
    }

   

    @GetMapping("/new/auto/{sectionId}")
    public String beginTakeAttendanceAuto(@PathVariable int sectionId, Model model) {
        try{
            model.addAttribute("sectionId", sectionId);
            model.addAttribute("uid", UserSession.getInstance().getUid());
            String redirectUrl = "https://" + UserSession.getInstance().getHost() + ":" + "8081"
                    + "/attendance/submitPosition/"+sectionId;
            String qr = qrCodeService.generateQRCodeImage(redirectUrl,false);
            model.addAttribute("qr", qr);
        }catch(Exception e){
            logger.info(e.getMessage());
        }
      
        return "takeAttendanceAuto";
    }

    @PostMapping("/new/auto/{sectionId}")
    public String sendInitialAttendance(@RequestParam("threshold") double threshold,@PathVariable int sectionId, RedirectAttributes redirectAttributes) {
        
        if(UserSession.getInstance().isScaned()){
            List<Student> students = attendanceService.getRoaster(sectionId);
            AttendanceRecord attendance = new AttendanceRecord();
            attendance.initializeFromRoaster(students);
            attendanceService.sendAttendanceRecord(attendance, sectionId);
            UserSession.getInstance().setScaned(false);
            UserSession.getInstance().setThreshold(threshold);
            System.out.println("thredshod:***"+threshold);
            return "redirect:/qrcode/" + sectionId;
        }
        redirectAttributes.addFlashAttribute("notScanned", true);
        return "redirect:/attendance/new/auto/"+sectionId;
        
    }

    @GetMapping("/submitPosition/{sectionId}")
    public String showSubmitLocation() {
        return "submitLocation";
    }
    
    @PostMapping("/submitPosition/{sectionId}")
    public String submitLocation(@PathVariable int sectionId, @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude, RedirectAttributes redirectAttributes) {
        UserSession.getInstance().setProfessorLongitude(longitude);
        UserSession.getInstance().setProfesssorLatitude(latitude);
        redirectAttributes.addFlashAttribute("message", "You successfully submit your location");
        UserSession.getInstance().setScaned(true);
        return "redirect:/attendance/submitPosition/"+sectionId;
    }

    

}
