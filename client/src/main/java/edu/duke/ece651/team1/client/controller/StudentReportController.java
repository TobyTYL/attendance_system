package edu.duke.ece651.team1.client.controller;
import edu.duke.ece651.team1.client.model.AttendanceSummary;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.NotificationService;
import edu.duke.ece651.team1.client.service.StudentReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
/**
 * The StudentReportController class manages the generation and display of attendance reports for a student in a specific class.
 * It allows the user to view both summary and detailed reports for their attendance.
 * This controller interacts with the StudentReportView for user input and utilizes a RestTemplate
 * for HTTP requests to the backend service.
 */
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentReportController {
   @Autowired
   StudentReportService studentReportService;
   @GetMapping("/student/report/detail/{classId}/{sectionId}")
   public String getDetailReport(@PathVariable int sectionId, @PathVariable int classId, Model model) {
       Map<String, String> record = studentReportService.getDetailReport(sectionId);
       model.addAttribute("record", record);
       model.addAttribute("classId", classId);
       model.addAttribute("sectionId", sectionId);
       model.addAttribute("uid", UserSession.getInstance().getUid());
       return "detailReport";
   }

   @GetMapping("/student/report/summary/{classId}/{sectionId}")
   public String getSummaryReport(@PathVariable int sectionId, @PathVariable int classId, Model model) {
        AttendanceSummary summary = studentReportService.getSummaryStatistic(sectionId);
       model.addAttribute("summary", summary);
       model.addAttribute("classId", classId);
       model.addAttribute("sectionId", sectionId);
       model.addAttribute("uid", UserSession.getInstance().getUid());
       return "summaryReport";
   }

   
}
